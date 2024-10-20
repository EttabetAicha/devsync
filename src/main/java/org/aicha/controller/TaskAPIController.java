package org.aicha.controller;

import jakarta.ws.rs.core.Response;
import org.json.JSONObject;
import org.aicha.model.Tag;
import org.aicha.model.Task;
import org.aicha.model.enums.TaskStatus;
import org.aicha.service.TagService;
import org.aicha.service.TaskService;

import java.util.List;
import java.util.stream.Collectors;

public class TaskAPIController {

    private final TaskService taskService;
    private final TagService tagService;

    public TaskAPIController() {
        taskService = new TaskService();
        tagService = new TagService();
    }

    public Response index(List<String> tagsIds, String year, String month) {
        // Fetch all tasks at once
        List<Task> tasks = taskService.getAllTasks();

        // Filter tasks based on tags, year, and month
        List<Task> filteredTasks = tasks.stream()
                .filter(task -> filterByTags(task, tagsIds))
                .filter(task -> filterByYear(task, year))
                .filter(task -> filterByMonth(task, month))
                .collect(Collectors.toList());

        JSONObject response = new JSONObject();
        response.put("tasks_total", filteredTasks.size());
        response.put("todo", formatPercentage(filteredTasks, TaskStatus.todo));
        response.put("pending", formatPercentage(filteredTasks, TaskStatus.pending));
        response.put("done", formatPercentage(filteredTasks, TaskStatus.done));
        response.put("overdue", formatPercentage(filteredTasks, TaskStatus.overdue));

        return Response.ok(response.toString()).build();
    }

    private boolean filterByTags(Task task, List<String> tagsIds) {
        if (tagsIds == null || tagsIds.isEmpty()) {
            return true; // No filtering by tags
        }
        List<Long> ids = tagsIds.stream().map(Long::parseLong).collect(Collectors.toList());
        List<Tag> tags = tagService.getTagsByIds(ids);
        return task.getTags().stream().anyMatch(tags::contains);
    }

    private boolean filterByYear(Task task, String year) {
        return year == null || year.equals("all") || task.getStartDate().getYear() == Integer.parseInt(year);
    }

    private boolean filterByMonth(Task task, String month) {
        return month == null || month.equals("all") || task.getStartDate().getMonthValue() == Integer.parseInt(month);
    }

    private String formatPercentage(List<Task> tasks, TaskStatus status) {
        int total = tasks.size();
        if (total == 0) {
            return "0.00";
        }

        int count = (int) tasks.stream().filter(task -> task.getStatus() == status).count();
        double percentage = (count * 100.0) / total;

        return String.format("%.2f", percentage);
    }
}
