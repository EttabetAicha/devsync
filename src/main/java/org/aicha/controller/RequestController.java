package org.aicha.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.aicha.model.*;
import org.aicha.model.enums.RequestStatus;
import org.aicha.model.enums.RequestType;
import org.aicha.model.enums.TaskStatus;
import org.aicha.model.enums.UserRole;
import org.aicha.service.RequestService;
import org.aicha.service.TaskService;
import org.aicha.service.UserService;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class RequestController {

    private final RequestService requestService;
    private final MainController mainController;
    private final TaskService taskService;
    private final UserService userService;

    public RequestController() {
        requestService = new RequestService();
        mainController = new MainController();
        taskService = new TaskService();
        userService = new UserService();
    }

    public void index(HttpServletRequest servletRequest, HttpServletResponse response) {
        // Check session and role
        HttpSession session = servletRequest.getSession();
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            List<Request> requests;
            requests = requestService.getAllRequests()
                    .stream()
                    .filter(
                            req -> {
                                if (user.getRole() == UserRole.manager)
                                    return req.getTask().getCreatedBy().equals(user);
                                else
                                    return req.getRequester().equals(user);
                            }
                    )
                    .collect(Collectors.toList());
            // order by creation date
            requests.sort(Comparator.comparing(Request::getRequestedAt).reversed());
            servletRequest.setAttribute("requests", requests);
            try {
                RequestDispatcher requestDispatcher = servletRequest.getRequestDispatcher("requests/index.jsp");
                requestDispatcher.forward(servletRequest, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                response.sendRedirect(servletRequest.getContextPath() + "/users?action=login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void save(HttpServletRequest servletRequest, HttpServletResponse response) {
        HttpSession session = servletRequest.getSession();
        if (session != null && session.getAttribute("user") != null) {
            User requester = (User) session.getAttribute("user");
            Request newRequest = new Request();
            newRequest.setRequester(requester);
            newRequest.setType(RequestType.valueOf(servletRequest.getParameter("type")));
            newRequest.setTask(taskService.getTaskById(Long.parseLong(servletRequest.getParameter("task_id"))).orElse(null));

            String error = requestService.validateRequest(newRequest);
            if (error != null) {
                try {
                    response.sendRedirect(servletRequest.getContextPath() + "/tasks?error=" + error);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }

            Optional<Request> registeredRequest = requestService.createRequest(newRequest);
            registeredRequest.ifPresent(
                    req -> {
                        try {
                            userService.updateUser(requester.useToken(req.getType()));
                            response.sendRedirect(servletRequest.getContextPath() + "/requests?success=Request sent successfully");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );
        }
    }

    public void acceptRequest(HttpServletRequest servletRequest, HttpServletResponse response) {
        Long RequestId = Long.parseLong(servletRequest.getParameter("id"));
        Optional<Request> request = requestService.getRequestById(RequestId);
        request.ifPresentOrElse(
                req -> {
                    if (req.getType() == RequestType.DELETION) {
                        Task task = req.getTask();
                        taskService.deleteTask(task);
                    } else {
                        servletRequest.setAttribute("request", req);
                        List<User> users = new UserService().getAllUsers()
                                .stream()
                                .filter(user -> user.getRole() == UserRole.user && !user.equals(req.getRequester()))
                                .collect(Collectors.toList());
                        servletRequest.setAttribute("users", users);
                        try {
                            // Redirect to form to assign the task to other employee
                            RequestDispatcher requestDispatcher = servletRequest.getRequestDispatcher("tasks/assign.jsp");
                            requestDispatcher.forward(servletRequest, response);
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    Optional<Request> updatedRequest = requestService.respondToRequest(req, RequestStatus.ACCEPTED);
                    updatedRequest.ifPresent(
                            r -> {
                                try {
                                    response.sendRedirect(servletRequest.getContextPath() + "/requests?success=Request accepted");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                    );
                },
                () -> mainController.notFound(servletRequest, response)
        );
    }

    public void rejectRequest(HttpServletRequest servletRequest, HttpServletResponse response) {
        Long RequestId = Long.parseLong(servletRequest.getParameter("id"));
        Optional<Request> request = requestService.getRequestById(RequestId);
        request.ifPresentOrElse(
                t -> {
                    Optional<Request> updatedRequest = requestService.respondToRequest(t, RequestStatus.REJECTED);
                    updatedRequest.ifPresent(
                            Request1 -> {
                                try {
                                    response.sendRedirect(servletRequest.getContextPath() + "/requests?info=Request rejected");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                    );
                },
                () -> mainController.notFound(servletRequest, response)
        );
    }

    public void acceptModify(HttpServletRequest servletRequest, HttpServletResponse response) {
        String requestIdParam = servletRequest.getParameter("request_id");
        String userIdParam = servletRequest.getParameter("modified_to");
        System.out.println("Request ID: " + requestIdParam);
        System.out.println("User ID: " + userIdParam);

        if (requestIdParam == null || userIdParam == null) {
            try {
                response.sendRedirect(servletRequest.getContextPath() + "/requests?error=Missing request ID or user ID");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        try {
            Long requestId = Long.parseLong(requestIdParam);
            Long userId = Long.parseLong(userIdParam);

            Optional<Request> reqOp = requestService.getRequestById(requestId);
            Optional<User> userModifiedToOp = userService.getUserById(userId);

            if (reqOp.isPresent() && userModifiedToOp.isPresent()) {
                Request request = reqOp.get();
                Task task = request.getTask();
                task.setAssignedTo(userModifiedToOp.get());
                task.setTokenModifiable(false);
                taskService.updateTask(task);
                requestService.respondToRequest(request, RequestStatus.ACCEPTED);
                userService.updateUser(request.getRequester().useToken(RequestType.MODIFICATION));
                response.sendRedirect(servletRequest.getContextPath() + "/requests?success=Request accepted");
            } else {
                response.sendRedirect(servletRequest.getContextPath() + "/requests?error=Invalid request ID or user ID");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            try {
                response.sendRedirect(servletRequest.getContextPath() + "/requests?error=Invalid request ID or user ID");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
