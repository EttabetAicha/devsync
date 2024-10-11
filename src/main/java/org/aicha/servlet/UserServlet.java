package org.aicha.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.aicha.model.User;
import org.aicha.model.enums.UserType;
import org.aicha.service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "User", value = "/User")
public class UserServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService(); // Ensure this is properly initialized
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String idParam = request.getParameter("id");
        String typeParam = request.getParameter("type");

        session.setAttribute("typeParam", typeParam);
        switch (action) {
            case "update":
                handleUpdateUser(request, response, idParam, session);
                break;
            case "delete":
                handleDeleteUser(request, response, idParam);
                break;
            case "dashboard":
                forwardToPage(request, response, "dashboard.jsp");
                break;
            case "users":
                handleUsersList(request, response);
                break;
            case "logout":
                handleLogout(session, response);
                break;
            default:
                response.sendRedirect("Login");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");

        if ("addUser".equals(action)) {
            forwardToPage(request, response, "admin/addUser.jsp");
        } else {
            handleUserUpdate(request, response, session);
        }
    }

    private void handleUpdateUser(HttpServletRequest request, HttpServletResponse response, String idParam, HttpSession session) throws ServletException, IOException {
        if (idParam != null) {
            Long id = Long.parseLong(idParam);
            Optional<User> user = userService.getById(id);
            if (user.isPresent()) {
                request.setAttribute("user", user.get());
                request.setAttribute("userLogin", session.getAttribute("user"));
                if ("manager".equals(session.getAttribute("typeParam"))) {
                    request.setAttribute("isManager", "true");
                }
                forwardToPage(request, response, "admin/editUser.jsp");
            } else {
                response.sendRedirect("admin/listUser.jsp");
            }
        } else {
            response.sendRedirect("admin/listUser.jsp");
        }
    }

    private void handleDeleteUser(HttpServletRequest request, HttpServletResponse response, String idParam) throws IOException, ServletException {
        if (idParam != null) {
            Long id = Long.parseLong(idParam);
            Optional<User> user = userService.getById(id);
            if (user.isPresent() && userService.deleteById(user.get().getId()).isPresent()) {
                redirectToUserList(request, response, String.valueOf(user.get().getUserType()));
            }
        }
    }

    private void handleUsersList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> usersList = userService.getAll();
        request.setAttribute("userList", usersList);
        forwardToPage(request, response, "admin/listUser.jsp");
    }

    private void handleLogout(HttpSession session, HttpServletResponse response) throws IOException {
        session.invalidate();
        response.sendRedirect("Login");
    }

    private void handleUserUpdate(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id != null) {
            Long userId = Long.parseLong(id);
            Optional<User> userOptional = userService.getById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                updateUserDetails(request, user);
                userService.update(user);
                redirectToUserListAfterUpdate(request, response, session, user);
            } else {
                forwardToPage(request, response, "admin/listUser.jsp");
            }
        }
    }

    private void updateUserDetails(HttpServletRequest request, User user) {
        user.setFirstname(request.getParameter("firstname"));
        user.setLastname(request.getParameter("lastname"));
        user.setEmail(request.getParameter("email"));
        user.setPassword(request.getParameter("password"));
    }

    private void redirectToUserList(HttpServletRequest request, HttpServletResponse response, String userType) throws IOException {
        response.sendRedirect("User?action=users&type=" + userType);
    }

    private void redirectToUserListAfterUpdate(HttpServletRequest request, HttpServletResponse response, HttpSession session, User user) throws ServletException, IOException {
        request.setAttribute("userList", userService.getAll());
        if (user.getUserType() == UserType.MANAGER) {
            forwardToPage(request, response, "admin/listUser.jsp");
        } else {
            response.sendRedirect("User?action=users&type=user");
        }
    }

    private void forwardToPage(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}
