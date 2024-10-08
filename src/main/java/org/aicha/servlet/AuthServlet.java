package org.aicha.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.aicha.model.User;
import org.aicha.service.AuthService;

import java.io.IOException;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

    @Inject
    private AuthService authService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "login":
                    loginUser(request, response);
                    break;
                case "logout":
                    logoutUser(request, response);
                    break;
                case "register":
                    registerUser(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
                    break;
            }
        } catch (Exception e) {
            log("Error processing POST request: " + e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action.equals("login")) {
                showLoginForm(request, response);
            }else
            if (action.equals("register")) {
                showRegisterForm(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
            }


        } catch (Exception e) {
            log("Error processing GET request: " + e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: " + e.getMessage());
        }


    }

    private void showRegisterForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/auth/register.jsp").forward(request, response);
    }
    private void showLoginForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/auth/login.jsp").forward(request, response);
    }


    private void registerUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String ismanager = request.getParameter("ismanager");

        System.out.println("Registering user: " + username + ", Email: " + email + ", ismanager: " + ismanager);

        if (username == null || email == null || password == null || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "All fields are required.");
            return;
        }

        try {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.setManager(Boolean.parseBoolean(ismanager));

            String token = authService.registerUser(newUser);
            if (token != null) {
                response.sendRedirect("auth?action=login");
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Username already exists or registration failed.");
            }
        } catch (Exception e) {
            log("Error during registration: " + e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Registration error. Please try again later.");
        }
    }


    private void loginUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Username and password are required.");
            return;
        }
        String token = authService.authenticate(username, password);
        if (token != null) {
            HttpSession session = request.getSession();
            session.setAttribute("userToken", token);
            response.sendRedirect("user?action=list");
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid username or password.");
        }
    }


    private void logoutUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect("/views/auth/login.jsp");
    }
}
