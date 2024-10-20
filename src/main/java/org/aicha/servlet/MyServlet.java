package org.aicha.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import org.aicha.controller.MainController;
import org.aicha.model.Tag;
import org.aicha.service.TagService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "myServlet", value = {"/"})
public class MyServlet extends HttpServlet {

    MainController mainController;
    TagService tagService;

    public void init() {
        mainController = new MainController();
        tagService = new TagService();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getServletPath().equals("/")) {
            mainController.index(request, response);
        } else if (request.getServletPath().equals("/statistics")) {
            // Set tags attribute
            List<Tag> tags = tagService.getAllTags();
            request.setAttribute("tags", tags);

            String tag = request.getParameter("tag");
            String period = request.getParameter("period");

            if (tag == null || tag.isEmpty() || period == null || period.isEmpty()) {
                request.setAttribute("error", "Tag or period is null or empty");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("statistics.jsp");
                requestDispatcher.forward(request, response);
                return;
            }

            mainController.statistics(request, response, tag, period);
        }
    }
}