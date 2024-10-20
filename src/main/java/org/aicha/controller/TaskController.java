package org.aicha.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.aicha.model.Tag;
import org.aicha.model.Task;
import org.aicha.model.User;
import org.aicha.model.enums.TaskStatus;
import org.aicha.model.enums.UserRole;
import org.aicha.service.TagService;
import org.aicha.service.TaskService;
import org.aicha.service.UserService;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class TaskController {

    private final TaskService taskService;
    private final TagService tagService;
    private final UserService userService;
    private final MainController mainController;

    public TaskController() {
        taskService = new TaskService();
        tagService = new TagService();
        userService = new UserService();
        mainController = new MainController();
    }

    public void index(HttpServletRequest request, HttpServletResponse response) {
        // Check session and role
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            List<Task> tasks;
            if (user.getRole() == UserRole.manager) {
                tasks = taskService.getAllTasks()
                        .stream()
                        .filter(
                                task -> task.getCreatedBy().equals(user)
                        )
                        .collect(Collectors.toList());
            } else {
                tasks = taskService.getUserTasks(user);
            }
            tasks.sort(Comparator.comparing(Task::getDeadline).reversed());
            request.setAttribute("tasks", tasks);
            try {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("tasks/index.jsp");
                requestDispatcher.forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                response.sendRedirect(request.getContextPath() + "/users?action=login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void create(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("user") != null) {
            try {
                List<Tag> tags = tagService.getAllTags();
                request.setAttribute("tags", tags);
                List<User> users = userService.getAllUsers()
                        .stream()
                        .filter(
                                user -> user.getRole() == UserRole.user
                        )
                        .collect(Collectors.toList());
                request.setAttribute("users", users);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("tasks/create.jsp");
                requestDispatcher.forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                response.sendRedirect(request.getContextPath() + "/users?action=login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void edit(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("user") != null) {
            Long id = Long.parseLong(request.getParameter("id"));
            Optional<Task> task = taskService.getTaskById(id);
            task.ifPresentOrElse(
                    t -> {
                        List<Tag> tags = tagService.getAllTags();
                        request.setAttribute("tags", tags);
                        List<User> users = userService.getAllUsers()
                                .stream()
                                .filter(
                                        user -> user.getRole() == UserRole.user
                                )
                                .collect(Collectors.toList());
                        // Dates format
                        request.setAttribute("start_date", t.getStartDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
                        request.setAttribute("deadline", t.getDeadline().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
                        request.setAttribute("users", users);
                        request.setAttribute("task", t);
                        try {
                            RequestDispatcher requestDispatcher = request.getRequestDispatcher("tasks/edit.jsp");
                            requestDispatcher.forward(request, response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    },
                    () -> mainController.notFound(request, response)
            );
        } else {
            try {
                response.sendRedirect(request.getContextPath() + "/users?action=login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void save(HttpServletRequest request, HttpServletResponse response) {
        Task newTask = this.getTaskFromRequest(request);
        String error = taskService.validateTask(newTask);
        if (error != null) {
            try {
                response.sendRedirect(request.getContextPath() + "/tasks?action=create&error=" + error);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        Optional<Task> registeredTask = taskService.createTask(newTask);
        registeredTask.ifPresent(
                task -> {
                    try {
                        response.sendRedirect(request.getContextPath() + "/tasks");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public void markAsDone(HttpServletRequest request, HttpServletResponse response) {
        Long taskId = Long.parseLong(request.getParameter("id"));
        Optional<Task> task = taskService.getTaskById(taskId);
        task.ifPresentOrElse(
                t -> {
                    Boolean isUpdated = taskService.markTaskAsDone(t);
                    try {
                        if (isUpdated)
                            response.sendRedirect(request.getContextPath() + "/tasks");
                        else
                            response.sendRedirect(request.getContextPath() + "/tasks?error=Cannot mark task as done after deadline");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                },
                () -> mainController.notFound(request, response)
        );
    }

    public void update(HttpServletRequest request, HttpServletResponse response) { // TODO : if this worked you are more than a beast
        Long id = Long.parseLong(request.getParameter("id"));
        Optional<Task> taskOp = taskService.getTaskById(id);
        taskOp.ifPresentOrElse(
                task -> {
                    Task newData = this.getTaskFromRequest(request);
                    task.setTitle(newData.getTitle());
                    task.setDescription(newData.getDescription());
                    task.setStartDate(newData.getStartDate());
                    task.setDeadline(newData.getDeadline());
                    task.setAssignedTo(newData.getAssignedTo());
                    task.setTags(newData.getTags());
                    String error = taskService.validateTask(newData);
                    if (error != null) {
                        try {
                            response.sendRedirect(request.getContextPath() + "/tasks?action=edit&id=" + id + "&error=" + error);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    Optional<Task> updatedTaskOp = taskService.updateTask(task);
                    updatedTaskOp.ifPresent(
                            t -> {
                                try {
                                    response.sendRedirect(request.getContextPath() + "/tasks");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                    );
                },
                () -> mainController.notFound(request, response)
        );
    }

    public void delete(HttpServletRequest request, HttpServletResponse response) {
        Long taskId = Long.parseLong(request.getParameter("id"));
        Optional<Task> task = taskService.getTaskById(taskId);
        task.ifPresentOrElse(
                u -> {
                    if (taskService.deleteTask(u) != null) {
                        try {
                            response.sendRedirect(request.getContextPath() + "/tasks");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            PrintWriter out = response.getWriter();
                            out.println("<html><body><h1 style=\"color:red;\">Task deletion failed</h1></body></html>");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                () -> {
                    try {
                        PrintWriter out = response.getWriter();
                        out.println("<html><body><h1 style=\"color:red;\">Task not found</h1></body></html>");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    private Task getTaskFromRequest(HttpServletRequest request) {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        LocalDate startDate = LocalDate.parse(request.getParameter("start_date"), formatter);
        LocalDate deadline = LocalDate.parse(request.getParameter("deadline"), formatter);
        TaskStatus status = TaskStatus.todo;
        User createdBy = (User) request.getSession().getAttribute("user");

        // Get assigned user from request
        User assignedTo;
        if (createdBy.getRole().equals(UserRole.manager))
            assignedTo = userService.getUserById(Long.parseLong(request.getParameter("assigned_to"))).orElse(null);
        else
            assignedTo = createdBy;

        // Get tags ids from request
        ArrayList<Tag> tags = Arrays.stream(request.getParameterValues("tags[]"))
                .map(
                        id -> {
                            try {
                                return tagService.getTagById(Long.parseLong(id)).orElse(null);
                            } catch (NumberFormatException e) {
                                return null;
                            }
                        }
                )
                .collect(Collectors.toCollection(ArrayList::new));

        Boolean isTokenModifiable = true;
        Task task = new Task(title, description, status, startDate, deadline, isTokenModifiable, assignedTo, createdBy);
        task.setTags(tags);
        return task;
    }

    public void markAsPending(HttpServletRequest request, HttpServletResponse response) {
        Long taskId = Long.parseLong(request.getParameter("id"));
        Optional<Task> task = taskService.getTaskById(taskId);
        task.ifPresentOrElse(
                t -> {
                    t.setStatus(TaskStatus.pending);
                    Optional<Task> updatedTask = taskService.updateTask(t);
                    updatedTask.ifPresent(
                            task1 -> {
                                try {
                                    response.sendRedirect(request.getContextPath() + "/tasks");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                    );
                },
                () -> mainController.notFound(request, response)
        );
    }
}
