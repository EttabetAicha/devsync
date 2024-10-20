package org.aicha.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aicha.controller.MainController;
import org.aicha.controller.RequestController;

@WebServlet(name = "RequestServlet", value = {"/requests"})
public class RequestServlet extends HttpServlet {
    private final RequestController requestController;
    private final MainController mainController;

    public RequestServlet() {
        requestController = new RequestController();
        mainController = new MainController();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");
        response.setContentType("text/html");

        if (action == null) {
            requestController.index(request, response);
        }
        else
            mainController.notFound(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");

        switch (action) {
            case "save":
                requestController.save(request, response);
                break;

            case "accept":
                requestController.acceptRequest(request, response);
                break;

            case "acceptModify":
                requestController.acceptModify(request, response);
                break;

            case "reject":
                requestController.rejectRequest(request, response);
                break;
            default:
                requestController.index(request, response);
                break;
        }
    }
}
