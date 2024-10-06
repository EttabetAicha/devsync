package org.aicha.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aicha.model.User;
import org.aicha.service.UserService;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.List;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    @Inject
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }
        try {
            switch (action) {
                case "list":
                    listUsers(request, response);
                    break;
                case "view":
                    viewUser(request, response);
                    break;
                case "create":
                    showCreateForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteUser(request, response);
                    break;
                default:
                    listUsers(request, response);
                    break;
            }
        } catch (Exception e) {
            log("Error processing GET request: " + e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "create":
                    createUser(request, response);
                    break;
                case "edit":
                    updateUser(request, response);
                    break;
                default:
                    listUsers(request, response);
                    break;
            }
        } catch (Exception e) {
            log("Error processing POST request: " + e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: " + e.getMessage());
        }
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<User> users = userService.getAllUsers();
            System.out.println("Number of users found: " + users.size());
            request.setAttribute("users", users);
            request.getRequestDispatcher("/views/user/list.jsp").forward(request, response);
        } catch (Exception e) {
            log("Error retrieving user list: " + e.getMessage(), e);
            request.setAttribute("errorMessage", "Failed to retrieve user list. Please try again later.");
            request.setAttribute("users", List.of());
            request.getRequestDispatcher("/views/user/list.jsp").forward(request, response);
        }
    }

    private void viewUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID is required.");
            return;
        }

        try {
            Long id = Long.parseLong(idParam);
            User user = userService.getUserById(id);
            if (user != null) {
                request.setAttribute("user", user);
                request.getRequestDispatcher("/views/user/view.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found.");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid User ID format.");
        }
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/user/create.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID is required.");
            return;
        }

        try {
            Long id = Long.parseLong(idParam);
            User user = userService.getUserById(id);
            if (user != null) {
                request.setAttribute("user", user);
                request.getRequestDispatcher("/views/user/edit.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found.");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid User ID format.");
        }
    }

    private void createUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String isManagerParam = request.getParameter("ismanager");

        if (username == null || username.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "All fields are required.");
            return;
        }

        boolean isManager = isManagerParam != null && isManagerParam.equals("true");

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User newUser = new User(username, hashedPassword, email, isManager);
        userService.createUser(newUser);
        response.sendRedirect("user?action=list");
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID is required.");
            return;
        }

        try {
            Long id = Long.parseLong(idParam);
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String isManagerParam = request.getParameter("ismanager");

            if (username == null || username.isEmpty() || email == null || email.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Username and email cannot be empty.");
                return;
            }
            boolean isManager = isManagerParam != null && isManagerParam.equals("true");
            User user = userService.getUserById(id);
            if (user != null) {
                user.setUsername(username);
                user.setEmail(email);
                user.setManager(isManager);
                if (password != null && !password.isEmpty()) {
                    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
                    user.setPassword(hashedPassword);
                }
                userService.updateUser(user);
                response.sendRedirect("user?action=list");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found.");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid User ID format.");
        }
    }


    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID is required.");
            return;
        }
        try {
            Long id = Long.parseLong(idParam);
            userService.deleteUser(id);
            response.sendRedirect("user?action=list");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid User ID format.");
        }
    }
}
