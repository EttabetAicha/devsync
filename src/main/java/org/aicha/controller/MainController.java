package org.aicha.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aicha.model.Task;
import org.aicha.model.User;
import org.aicha.model.enums.TaskStatus;
import org.aicha.model.enums.UserRole;
import org.aicha.service.TagService;
import org.aicha.service.TaskService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

public class MainController {
    private TagService tagService;
    private TaskService taskService;

    public MainController() {
        tagService = new TagService();
        taskService = new TaskService();
    }

    public void index(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
        try {
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notFound(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("notFound.jsp");
        try {
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void statistics(HttpServletRequest request, HttpServletResponse response, String tag, String period) {
        forwardToPage(request, response, "statistics.jsp");
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            forwardToPage(request, response, "login.jsp");
            return;
        } else if (!user.getRole().equals(UserRole.manager)) {
            forwardToPage(request, response, "/");
            return;
        }

        if (tag == null || period == null) {
            System.out.println("Tag or period is null");
            forwardToPage(request, response, "error.jsp");
            return;
        }

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now();

        switch (period) {
            case "week":
                startDate = startDate.with(TemporalAdjusters.previousOrSame(LocalDate.now().getDayOfWeek()));
                endDate = endDate.with(TemporalAdjusters.nextOrSame(LocalDate.now().getDayOfWeek()));
                break;
            case "month":
                startDate = startDate.with(TemporalAdjusters.firstDayOfMonth());
                endDate = endDate.with(TemporalAdjusters.lastDayOfMonth());
                break;
            case "year":
                startDate = startDate.with(TemporalAdjusters.firstDayOfYear());
                endDate = endDate.with(TemporalAdjusters.lastDayOfYear());
                break;
        }

        List<Task> tasks = taskService.getTasksByUserAndTag(user, tag, startDate, endDate);
        if (tasks == null || tasks.isEmpty()) {
            forwardToPage(request, response, "error.jsp");
            return;
        }

        double completionPercentage = taskService.getCompletionPercentage(tasks);
        int tokensUsed = taskService.getTokensUsedByUser(user);

        request.setAttribute("totalTasks", tasks.size());
        request.setAttribute("completedTasks", tasks.stream().filter(task -> task.getStatus() == TaskStatus.done).count());
        request.setAttribute("completionPercentage", completionPercentage);
        request.setAttribute("tokensUsed", tokensUsed);

        forwardToPage(request, response, "statistics.jsp");
    }

    private void forwardToPage(HttpServletRequest request, HttpServletResponse response, String page) {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(page);
        try {
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}