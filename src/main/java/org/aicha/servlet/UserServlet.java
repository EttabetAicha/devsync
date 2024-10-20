package org.aicha.servlet;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import org.aicha.controller.UserController;

@WebServlet(name = "UserServlet", value = {"/users"})
public class UserServlet extends HttpServlet {
    private final UserController userController;

    public UserServlet() {
        userController = new UserController();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");
        System.out.println("\n\nAction : " + action);
        response.setContentType("text/html");
        // Index
        if (action == null) {
            userController.index(request, response);
        }
        // Register
        else if (action.equals("register")) {
            userController.create(request, response);
        }
        // Login
        else if (action.equals("login")) {
            userController.loginForm(request, response);
        }
        // Logout
        else if (action.equals("logout")) {
            userController.logout(request, response);
        }
        // Edit User
        else if (action.equals("edit")) {
            userController.edit(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");
        System.out.println("\n\nAction : " + action);

        // Register
        switch (action) {
            case "register":
                userController.save(request, response);
                break;
            // Login
            case "login":
                userController.login(request, response);
                break;
            // Logout
            case "logout":
                userController.logout(request, response);
                break;
            // Update User
            case "update":
                userController.update(request, response);
                break;
            // Delete User
            case "delete":
                userController.delete(request, response);
                break;
            default:
                userController.index(request, response);
                break;
        }
    }
}
