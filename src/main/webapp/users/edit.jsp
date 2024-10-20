<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.aicha.model.User, org.aicha.model.enums.UserRole" %>
<%
    User user = (User) request.getAttribute("user");
    if (user == null) {
        user = (User) session.getAttribute("user");
    }
%>
<html class="dark">
<head>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet"/>
    <title>DevSync - users edit</title>
</head>
<body>

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
                        <h1 class="text-center">Update account</h1>
                        <form action="<%= request.getContextPath() %>/users?action=update" method="post">
                            <div class="form-group">
                                <label for="username">Your Username</label>
                                <input type="text" name="username" value="<%= user.getUsername() %>" id="username" class="form-control" placeholder="name@company.com" required>
                            </div>
                            <div class="form-group">
                                <label for="email">Your email</label>
                                <input type="email" name="email" value="<%= user.getEmail() %>" id="email" class="form-control" placeholder="name@company.com" required>
                            </div>
                            <div class="form-group">
                                <label for="password">Password</label>
                                <input type="password" name="password" id="password" placeholder="••••••••" class="form-control" required>
                            </div>
                            <div class="form-group">
                                <label for="firstName">First Name</label>
                                <input type="text" name="firstName" value="<%= user.getFirstName() %>" id="firstName" class="form-control" required>
                            </div>
                            <div class="form-group">
                                <label for="lastName">Last Name</label>
                                <input type="text" name="lastName" value="<%= user.getLastName() %>" id="lastName" class="form-control" required>
                            </div>
                            <div class="form-group">
                                <label for="role">Role</label>
                                <select name="role" id="role" class="form-control">
                                    <option value="user" <%= user.getRole().equals(UserRole.user) ? "selected" : "" %>>User</option>
                                    <option value="manager" <%= user.getRole().equals(UserRole.manager) ? "selected" : "" %>>Manager</option>
                                </select>
                            </div>
                            <input value="<%= user.getId() %>" name="id" type="hidden">
                            <button type="submit" class="btn btn-primary btn-block">Update</button>
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
</body>
</html>