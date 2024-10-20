package org.aicha.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aicha.controller.MainController;
import org.aicha.controller.TagController;

@WebServlet(name = "tagServlet", value = {"/tags"})
public class TagServlet extends HttpServlet {

    private TagController tagController;
    private MainController mainController;

    public void init() {
        tagController = new TagController();
        mainController = new MainController();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");
        response.setContentType("text/html");

        // Index page
        if (action == null || action.equals("index")) {
            try {
                tagController.index(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Create page
        else if (action.equals("create")) {
            try {
                tagController.create(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Edit
        else if (action.equals("edit")) {
            try {
                tagController.edit(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Handle invalid paths
        else {
            mainController.notFound(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");
        System.out.println("\n\nAction : " + action);
        response.setContentType("text/html");

        // Create tag
        switch (action) {
            case "save":
                try {
                    tagController.save(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            // Update
            case "update":
                try {
                    tagController.update(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            // Delete
            case "delete":
                try {
                    tagController.delete(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            // Handle invalid paths
            default:
                mainController.notFound(request, response);
                break;
        }
    }


}
