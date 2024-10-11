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
import org.aicha.service.TaskService;
import org.aicha.service.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet(name = "auth", urlPatterns = {"/auth"})
public class AuthServlet extends HttpServlet {
    private UserService userService;
    private TaskService taskService;

    @Override
    public void init() throws ServletException {
        this.userService = new UserService();
        this.taskService = new TaskService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/register.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession session = request.getSession(true);
        String action = request.getParameter("action");

        switch (action) {
            case "register":
                handleRegistration(request, response, session);
                break;
            case "login":
                handleLogin(request, response, session);
                break;
            default:
                response.sendRedirect("index.jsp");
                break;
        }
    }

    private void handleRegistration(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        String username = request.getParameter("username");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String typeUser = request.getParameter("userType");
        String submitType = request.getParameter("submit");

        UserType userType;
        try {
            if (typeUser == null || typeUser.isEmpty()) {
                throw new IllegalArgumentException("User type must be specified.");
            }
            userType = UserType.valueOf(typeUser.toUpperCase());
        } catch (IllegalArgumentException e) {
            request.setAttribute("message", "Invalid user type provided.");
            forwardToPage(request, response, "register.jsp");
            return;
        }

        User user = new User(username, firstname, lastname, password, email, userType);

        try {
            Optional<User> registeredUser = userService.register(user, request);

            if (registeredUser.isPresent()) {
                request.setAttribute("message", "Registered successfully");
                if ("user".equals(submitType)) {
                    response.sendRedirect("index.jsp");
                } else {
                    request.setAttribute("userList", userService.getAll());
                    forwardToPage(request, response, "admin/listUser.jsp");
                }
            } else {
                String message = (String) session.getAttribute("existEmail");
                if (message == null) {
                    message = (String) session.getAttribute("existUsername");
                }
                request.setAttribute("message", message != null ? message : "Registration failed. Please try again.");
                forwardToPage(request, response, "register.jsp");
            }
        } catch (SQLException e) {
            // Log the SQLException for debugging
            e.printStackTrace();
            request.setAttribute("message", "Database error occurred. Please contact support.");
            forwardToPage(request, response, "register.jsp");
        } catch (Exception e) {
            // Catch all other exceptions
            e.printStackTrace();
            request.setAttribute("message", "An unexpected error occurred during registration. Please try again.");
            forwardToPage(request, response, "register.jsp");
        }
    }


    private void handleLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Optional<User> userOpt = userService.login(username, password);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                session.setAttribute("user", user);
                String redirectPage = user.getUserType().equals(UserType.USER) ? "userDashboard.jsp" : "adminDashboard.jsp";
                forwardToPage(request, response, redirectPage);
            } else {
                request.setAttribute("message", "Invalid username or password.");
                forwardToPage(request, response, "index.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred while logging in. Please try again.");
            forwardToPage(request, response, "index.jsp");
        }
    }

    private void forwardToPage(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}
