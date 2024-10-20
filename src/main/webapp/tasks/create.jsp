<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.aicha.model.Tag, org.aicha.model.User, java.util.List" %>
<%@ page import="org.aicha.model.enums.UserRole" %>
<!DOCTYPE html>
<html class="dark">
<head>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css" rel="stylesheet"/>
    <title>DevSync _ Tasks-Create</title>
</head>
<body>

<!-- Include header -->
<jsp:include page="../layouts/header.jsp"/>

<section class="bg-light py-3">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <a href="<%= request.getContextPath() %>" class="d-flex align-items-center mb-4 text-dark">
                    DevSync
                </a>
                <div class="card">
                    <div class="card-body">
                        <h1 class="text-center">Create Task</h1>
                        <form action="<%= request.getContextPath() %>/tasks?action=save" method="post">
                            <div class="form-group">
                                <label for="title">Title</label>
                                <input type="text" name="title" id="title" class="form-control" placeholder="Enter task title" required>
                            </div>

                            <div class="form-group">
                                <label for="description">Description</label>
                                <textarea id="description" name="description" rows="4" class="form-control" placeholder="Enter task description"></textarea>
                            </div>

                            <div class="form-group">
                                <%--@declare id="start_date"--%><label for="start_date">Start Date</label>
                                <div class="input-group">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text">
                                            <svg class="bi bi-calendar" width="1em" height="1em" fill="currentColor" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16">
                                                <path d="M3.5 0a.5.5 0 0 1 .5.5V1h8V.5a.5.5 0 0 1 1 0V1h1a2 2 0 0 1 2 2v11a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V3a2 2 0 0 1 2-2h1V.5a.5.5 0 0 1 .5-.5zM1 4v10a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V4H1zm1-1h12V3a1 1 0 0 0-1-1H2a1 1 0 0 0-1 1v1z"/>
                                            </svg>
                                        </span>
                                    </div>
                                    <input type="text"  name="start_date" class="form-control" placeholder="Select start date">
                                </div>
                            </div>

                            <div class="form-group">
                                <%--@declare id="deadline"--%><label for="deadline">Deadline</label>
                                <div class="input-group">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text">
                                            <svg class="bi bi-calendar" width="1em" height="1em" fill="currentColor" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16">
                                                <path d="M3.5 0a.5.5 0 0 1 .5.5V1h8V.5a.5.5 0 0 1 1 0V1h1a2 2 0 0 1 2 2v11a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V3a2 2 0 0 1 2-2h1V.5a.5.5 0 0 1 .5-.5zM1 4v10a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V4H1zm1-1h12V3a1 1 0 0 0-1-1H2a1 1 0 0 0-1 1v1z"/>
                                            </svg>
                                        </span>
                                    </div>
                                    <input type="text"  name="deadline" class="form-control" placeholder="Select deadline">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="tags">Tags</label>
                                <select id="tags" name="tags[]" multiple class="form-control">
                                    <%
                                        List<Tag> tags = (List<Tag>) request.getAttribute("tags");
                                        if (tags != null) {
                                            for (Tag tag : tags) {
                                    %>
                                    <option value="<%= tag.getId() %>"><%= tag.getName() %></option>
                                    <%
                                            }
                                        }
                                    %>
                                </select>
                            </div>

                            <%
                                User user = (User) request.getSession().getAttribute("user");
                                if (user != null && user.getRole() == UserRole.manager) {
                            %>
                            <div class="form-group">
                                <label for="assigned_to">Assigned To</label>
                                <select id="assigned_to" name="assigned_to" class="form-control">
                                    <%
                                        List<User> users = (List<User>) request.getAttribute("users");
                                        if (users != null) {
                                            for (User u : users) {
                                    %>
                                    <option value="<%= u.getId() %>"><%= u.getUsername() %></option>
                                    <%
                                            }
                                        }
                                    %>
                                </select>
                            </div>
                            <% } %>

                            <button type="submit" class="btn btn-primary btn-block">Create Task</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<jsp:include page="../layouts/footer.jsp"/>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>

</body>
</html>