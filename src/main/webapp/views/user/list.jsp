<%@ page import="org.aicha.model.User" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
  <title>User List</title>
  <style>
    /* Your existing styles */
  </style>
</head>
<body>
<h1>User List</h1>

<!-- Display an error message if available -->
<%
  String errorMessage = (String) request.getAttribute("errorMessage");
  if (errorMessage != null && !errorMessage.isEmpty()) {
%>
<div style="color: red;"><%= errorMessage %></div>
<%
  }
%>

<table>
  <tr>
    <th>ID</th>
    <th>Username</th>
    <th>Email</th>
    <th>Actions</th>
  </tr>

  <%
    List<User> users = (List<User>) request.getAttribute("users");
    if (users == null || users.isEmpty()) {
  %>
  <tr>
    <td colspan="4">No users found.</td>
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
      <a href="?action=view&id=<%= user.getId() %>">View</a> |
      <a href="?action=edit&id=<%= user.getId() %>">Edit</a> |
      <a href="?action=delete&id=<%= user.getId() %>" onclick="return confirm('Are you sure you want to delete this user?')">Delete</a>
    </td>
  </tr>
  <%
      }
    }
  %>
</table>

<a href="?action=create">Create New User</a>

</body>
</html>
