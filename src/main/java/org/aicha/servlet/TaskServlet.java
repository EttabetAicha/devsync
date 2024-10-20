package org.aicha.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aicha.controller.MainController;
import org.aicha.controller.TaskController;

@WebServlet(name = "TaskServlet", value = {"/tasks"})
public class TaskServlet extends HttpServlet {
    private  TaskController taskController;
    private  MainController mainController;

    public void  init() {
        taskController = new TaskController();
        mainController = new MainController();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");
        response.setContentType("text/html");
        // Index page
        if (action == null || action.equals("index")) {
            try {
                taskController.index(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Create page
        else if (action.equals("create")) {
            try {
                taskController.create(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Edit
        else if (action.equals("edit")) {
            try {
                taskController.edit(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");
        System.out.println("\n\nAction : " + action);

        // Register
        switch (action) {
            case "save":
                taskController.save(request, response);
                break;
            // Update Task
            case "update":
                taskController.update(request, response);
                break;
            // Delete Task
            case "delete":
                taskController.delete(request, response);
                break;
            // Mark as done
            case "done":
                taskController.markAsDone(request, response);
                break;
            // Mark as pending
            case "pending":
                taskController.markAsPending(request, response);
                break;
            default:
                taskController.index(request, response);
                break;
        }
    }
}
