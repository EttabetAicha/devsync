<%@ page import="org.aicha.model.enums.TaskStatus" %>
<%@ page import="org.aicha.model.User" %>
<%@ page import="org.aicha.model.enums.UserRole" %>
<%@ page import="org.aicha.model.Task" %>
<%@ page import="java.util.List" %>
<%@ page import="org.aicha.model.Tag" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    User user = (User) session.getAttribute("user");
    List<Task> tasks = (List<Task>) request.getAttribute("tasks");
%>
<!DOCTYPE html>
<html>
<head>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet"/>
    <title>DevSync _ Tasks</title>
</head>
<body>

<jsp:include page="../layouts/header.jsp"/>

<div class="container bg-light py-4">
    <section class="py-3 sm:py-5">
        <div class="px-4 mx-auto">
            <div class="relative overflow-hidden bg-white shadow-md sm:rounded-lg">
                <a href="${pageContext.request.contextPath}/tasks?action=create" class="btn btn-primary position-absolute top-1 end-2 m-3">
                    Add Task
                </a>
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead class="thead-light">
                        <tr>
                            <th scope="col">Mark as</th>
                            <th scope="col">Title</th>
                            <th scope="col">Description</th>
                            <th scope="col">Tags</th>
                            <th scope="col">Status</th>
                            <th scope="col">Assigned To</th>
                            <th scope="col">Start Date</th>
                            <th scope="col">End Date</th>
                            <% if (user.getRole() == UserRole.manager) { %>
                            <th scope="col" class="text-center">Used Tokens</th>
                            <% } %>
                            <th scope="col" class="text-center">Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        <% if (tasks == null || tasks.isEmpty()) { %>
                        <tr>
                            <td colspan="9" class="text-center">No tasks found</td>
                        </tr>
                        <% } else { %>
                        <% for (Task task : tasks) { %>
                        <tr class="border-bottom">
                            <td>
<%--                                <% if (task.getStatus() == TaskStatus.done) { %>--%>
<%--                                <form action="${pageContext.request.contextPath}/tasks?action=done" method="post" class="d-flex align-items-center p-0 m-0">--%>
<%--                                    <input type="hidden" name="id" value="<%= task.getId() %>">--%>
<%--                                    <button type="submit" class="btn btn-success btn-sm">Mark as Done</button>--%>
<%--                                </form>--%>
<%--                                <% } else if (task.getStatus() == TaskStatus.todo || task.getStatus() == TaskStatus.done) { %>--%>
<%--                                <form action="${pageContext.request.contextPath}/tasks?action=pending" method="post" class="d-flex align-items-center p-0 m-0">--%>
<%--                                    <input type="hidden" name="id" value="<%= task.getId() %>">--%>
<%--                                    <button type="submit" class="btn btn-warning btn-sm">Mark as Pending</button>--%>
<%--                                </form>--%>
<%--                                <% } %>--%>
                            </td>
                            <td>
                                <% if (task.getCreatedBy().equals(task.getAssignedTo())) { %>
                                <div class="badge bg-primary position-absolute top-1 end-1">EXTRA</div>
                                <% } %>
                                <%= task.getTitle() %>
                            </td>
                            <td>
                                <span class="badge bg-info"><%= task.getDescription() %></span>
                            </td>
                            <td>
                                <% for (Tag tag : task.getTags()) { %>
                                <span class="badge bg-secondary"><%= tag.getName() %></span>
                                <% } %>
                            </td>
                            <td>
                                <div class="d-flex">
                                    <% if (task.getStatus() == TaskStatus.todo) { %>
                                    <span class="badge bg-primary">Todo</span>
                                    <% } else if (task.getStatus() == TaskStatus.pending) { %>
                                    <span class="badge bg-warning">Pending..</span>
                                    <% } else if (task.getStatus() == TaskStatus.done) { %>
                                    <span class="badge bg-success">Done</span>
                                    <% } else if (task.getStatus() == TaskStatus.overdue) { %>
                                    <span class="badge bg-danger">Overdue!</span>
                                    <% } %>
                                </div>
                            </td>
                            <td><%= task.getAssignedTo().getUsername() %></td>
                            <td><%= task.getStartDate() %></td>
                            <td><%= task.getDeadline() %></td>
                            <% if (user.getRole() == UserRole.manager) { %>
                            <td class="text-center">
                                <span class="badge bg-pink text-dark"><%= task.getRequests().size() %></span>
                            </td>
                            <% } %>
                            <td class="d-flex justify-content-center" style="min-width: 300px">
                                <% if (!task.getCreatedBy().equals(task.getAssignedTo()) && user.getRole() != UserRole.manager) { %>
                                <form action="${pageContext.request.contextPath}/requests?action=save&type=MODIFICATION" method="post" class="d-flex p-0 m-0">
                                    <input type="hidden" name="task_id" value="<%= task.getId() %>">
                                    <button type="submit" class="btn btn-info btn-sm me-2">Send Modification Request</button>
                                </form>
                                <% } %>
                                <a href="${pageContext.request.contextPath}/tasks?action=edit&id=<%= task.getId() %>" class="btn btn-primary btn-sm me-2">Edit</a>
                                <form action="${pageContext.request.contextPath}/tasks?action=delete" method="post" class="d-flex p-0 m-0">
                                    <input type="hidden" name="id" value="<%= task.getId() %>">
                                    <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                                </form>
                            </td>
                        </tr>
                        <% } %>
                        <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </section>
</div>

<jsp:include page="../layouts/footer.jsp"/>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>