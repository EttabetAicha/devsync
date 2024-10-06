<%@ page import="org.aicha.model.User" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
  <title>User List</title>
  <!-- Include Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container mt-5">
  <h1 class="mb-4">Users List</h1>

  <%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null && !errorMessage.isEmpty()) {
  %>
  <div class="alert alert-danger"><%= errorMessage %></div>
  <%
    }
  %>

  <table class="table table-bordered table-striped">
    <thead class="thead-dark">
    <tr>
      <th>ID</th>
      <th>Username</th>
      <th>Email</th>
      <th>Role</th>
      <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <%
      List<User> users = (List<User>) request.getAttribute("users");
      if (users == null || users.isEmpty()) {
    %>
    <tr>
      <td colspan="5" class="text-center">No users found.</td>
    </tr>
    <%
    } else {
      for (User user : users) {
    %>
    <tr>
      <td><%= user.getId() %></td>
      <td><%= user.getUsername() %></td>
      <td><%= user.getEmail() %></td>
      <td>
        <%= user.isManager() ? "Manager" : "Simple User" %>
      </td>
      <td>
        <a href="?action=view&id=<%= user.getId() %>" class="btn btn-info text-white btn-sm">View</a>
        <a href="?action=edit&id=<%= user.getId() %>" class="btn btn-warning text-white btn-sm">Edit</a>
        <a href="?action=delete&id=<%= user.getId() %>" class="btn btn-danger text-white btn-sm" onclick="return confirm('Are you sure you want to delete this user?')">Delete</a>
      </td>
    </tr>
    <%
        }
      }
    %>
    </tbody>
  </table>

  <a href="?action=create" class="btn btn-success">Create New User</a>
</div>
</body>
</html>
