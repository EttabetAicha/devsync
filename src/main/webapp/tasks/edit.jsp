<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.aicha.model.Tag, org.aicha.model.User, org.aicha.model.enums.UserRole" %>
<%@ page import="java.util.List" %>
<%@ page import="org.aicha.model.Task" %>

<!DOCTYPE html>
<html class="dark">
<head>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet"/>
    <title>DevSync _ Tasks-Edit</title>
</head>
<body>

<jsp:include page="../layouts/header.jsp"/>

<section class="bg-light py-3">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <h1 class="text-center">Update Task</h1>
                <form action="${pageContext.request.contextPath}/tasks?action=update" method="post">
                    <input type="hidden" name="id" value="<%= request.getAttribute("task") != null ? ((Task) request.getAttribute("task")).getId() : "" %>">

                    <div class="form-group">
                        <label for="title">Title</label>
                        <input type="text" name="title" id="title" class="form-control" value="<%= request.getAttribute("task") != null ? ((Task) request.getAttribute("task")).getTitle() : "" %>" required>
                    </div>

                    <div class="form-group">
                        <label for="description">Description</label>
                        <textarea id="description" name="description" rows="4" class="form-control" required><%= request.getAttribute("task") != null ? ((Task) request.getAttribute("task")).getDescription() : "" %></textarea>
                    </div>

                    <div class="form-group">
                        <label for="start_date">Start Date</label>
                        <input type="text" id="start_date" name="start_date" class="form-control" value="<%= request.getAttribute("start_date") != null ? request.getAttribute("start_date") : "" %>" required>
                    </div>

                    <div class="form-group">
                        <label for="deadline">Deadline</label>
                        <input type="text" id="deadline" name="deadline" class="form-control" value="<%= request.getAttribute("deadline") != null ? request.getAttribute("deadline") : "" %>" required>
                    </div>

                    <div class="form-group">
                        <label for="tags">Tags</label>
                        <select id="tags" name="tags[]" multiple class="form-control">
                            <%
                                List<Tag> tags = (List<Tag>) request.getAttribute("tags");
                                Task task = (Task) request.getAttribute("task");
                                if (tags != null) {
                                    for (Tag tag : tags) {
                                        boolean selected = task != null && task.getTags().contains(tag);
                            %>
                            <option value="<%= tag.getId() %>" <%= selected ? "selected" : "" %>><%= tag.getName() %></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>

                    <%
                        User user = (User) request.getAttribute("user");
                        if (user != null && user.getRole() == UserRole.manager) {
                    %>
                    <div class="form-group">
                        <label for="assigned_to">Assigned To</label>
                        <select id="assigned_to" name="assigned_to" class="form-control">
                            <%
                                List<User> users = (List<User>) request.getAttribute("users");
                                if (users != null) {
                                    for (User emp : users) {
                                        boolean selected = task != null && task.getAssignedTo().equals(emp);
                            %>
                            <option value="<%= emp.getId() %>" <%= selected ? "selected" : "" %>><%= emp.getUsername() %></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>
                    <%
                        }
                    %>

                    <button type="submit" class="btn btn-primary btn-block">Update Task</button>
                </form>
            </div>
        </div>
    </div>
</section>

<jsp:include page="../layouts/footer.jsp"/>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>