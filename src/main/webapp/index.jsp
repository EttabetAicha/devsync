<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - DevSync</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2 class="text-center">Login to DevSync</h2>
    <form action="auth" method="post">
        <input type="hidden" name="action" value="login">
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" class="form-control" id="username" name="username" required>
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" class="form-control" id="password" name="password" required>
        </div>
        <div class="text-center">
            <button type="submit" class="btn btn-primary">Login</button>
            <p class="mt-3"><a href="register.jsp">Don't have an account? Register here</a></p>
        </div>

        <%
            // Check if the message attribute is not empty and display it
            String message = (String) request.getAttribute("message");
            if (message != null && !message.isEmpty()) {
        %>
        <div class="alert alert-danger mt-3"><%= message %></div>
        <%
            }
        %>
    </form>
</div>
</body>
</html>
