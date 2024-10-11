package org.aicha.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.aicha.model.Tag;
import org.aicha.model.Task;
import org.aicha.model.User;
import org.aicha.model.enums.TaskStatus;
import org.aicha.service.TagService;
import org.aicha.service.TaskService;
import org.aicha.service.UserService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(name = "Task", value = "Task")
public class TaskServlet extends HttpServlet {
    private UserService userService;
    private TaskService taskService;
    private TagService tagService;

    @Override
    public void init() throws ServletException {
        this.userService = new UserService();
        this.taskService = new TaskService();
        this.tagService = new TagService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null) {
            handleAction(request, response, action);
        } else {
            forwardToTaskList(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("actionType");
        if (action != null) {
            switch (action) {
                case "Add Task":
                    forwardToAddTaskPage(request, response);
                    break;
                case "addTask":
                    processTask(request, response, false);
                    break;
                case "updateTask":
                    processTask(request, response, true);
                    break;
            }
        }
    }

    private void handleAction(HttpServletRequest request, HttpServletResponse response, String action) throws ServletException, IOException {
        Long taskId = Long.parseLong(request.getParameter("id"));
        switch (action) {
            case "deleteTask":
                taskService.findById(taskId)
                        .ifPresent(taskService::delete);
                forwardToTaskList(request, response);
                break;

            case "updateTask":
                Optional<Task> task = taskService.findById(taskId);
                if (task.isPresent()) {
                    request.setAttribute("task", task.get());
                    populateFormAttributes(request);
                    forwardToPage(request, response, "admin/__ My-Task__ EditTickets.jsp");
                }
                break;
        }
    }

    private void processTask(HttpServletRequest request, HttpServletResponse response, boolean isUpdate) throws ServletException, IOException {
        Optional<Task> task = getTaskFromRequest(request, isUpdate);
        if (task.isPresent()) {
            if (isUpdate) {
                taskService.update(task.get());
            } else {
                taskService.save(task.get());
            }
            forwardToTaskList(request, response);
        }
    }

    private Optional<Task> getTaskFromRequest(HttpServletRequest request, boolean isUpdate) throws ServletException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        LocalDate creationDate = LocalDate.parse(request.getParameter("creationDate"));
        LocalDate endDate = LocalDate.parse(request.getParameter("endDate"));

        // Check if selected tags are valid
        String[] selectedTags = request.getParameterValues("tages[]");
        if (selectedTags == null || selectedTags.length < 2) {
            throw new ServletException("Please select at least two tags.");
        }

        List<Tag> tags = Arrays.stream(selectedTags)
                .map(Long::parseLong)
                .map(tagService::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        Long creatorId = Long.parseLong(request.getParameter("user_id"));
        Long assigneeId = Long.parseLong(request.getParameter("assigneeTo_id"));
        Optional<User> creator = userService.getById(creatorId);
        Optional<User> assignee = userService.getById(assigneeId);

        if (creator.isEmpty() || assignee.isEmpty()) {
            throw new ServletException("Invalid users selected.");
        }

        // Date validation
        if (!validateDates(creationDate, endDate)) {
            throw new ServletException("Invalid date parameters.");
        }

        Task task;
        if (isUpdate) {
            Long taskId = Long.parseLong(request.getParameter("idTask"));
            task = taskService.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Task not found"));
            task.setTitle(title);
            task.setDescription(description);
            task.setCreationDate(creationDate);
            task.setEndDate(endDate);
            task.setUser(creator.get());
            task.setAssigneeTo(assignee.get());
            task.setTags(tags);
            task.setStatus(TaskStatus.COMPLETED);
        } else {
            task = new Task(title, description, creationDate, endDate, TaskStatus.PENDING, false, creator.get(), assignee.get(), tags, false);
        }
        return Optional.of(task);
    }

    private boolean validateDates(LocalDate creationDate, LocalDate endDate) {
        if (creationDate.isAfter(endDate)) {
            return false;
        }
        if (creationDate.isBefore(LocalDate.now())) {
            return false;
        }
        long daysBetween = ChronoUnit.DAYS.between(LocalDate.now(), endDate);
        if (daysBetween < 3) {
            return false;
        }
        return true;
    }



    private void forwardToAddTaskPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        populateFormAttributes(request);
        forwardToPage(request, response, "tasks/AddTickets.jsp");
    }

    private void populateFormAttributes(HttpServletRequest request) {
        request.setAttribute("userList", userService.getAll());
        request.setAttribute("tagesList", tagService.findAll());
    }

    private void forwardToTaskList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("taskList", taskService.findAll());
        forwardToPage(request, response, "tasks/AddTickets.jsp");
    }

    private void forwardToPage(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

    private void setErrorMessageAndForward(HttpServletRequest request, HttpServletResponse response, String message, String page) throws ServletException, IOException {
        request.setAttribute("errorDate", message);
        forwardToPage(request, response, page);
    }
}
