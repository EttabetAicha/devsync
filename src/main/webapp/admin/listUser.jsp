<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>User Management</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.0/css/bootstrap.min.css">
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.0/js/bootstrap.bundle.min.js"></script>
</head>
<body>

<div class="container mt-5">
  <h2>User Management</h2>

  <div class="mb-4">
    <a href="User?action=addUser" class="btn btn-primary">Add User</a>
  </div>

  <!-- User List Table -->
  <table class="table table-bordered">
    <thead>
    <tr>
      <th>ID</th>
      <th>Username</th>
      <th>First Name</th>
      <th>Last Name</th>
      <th>Email</th>
      <th>Type</th>
      <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${userList}">
      <tr>
        <td>${user.id}</td>
        <td>${user.username}</td>
        <td>${user.firstname}</td>
        <td>${user.lastname}</td>
        <td>${user.email}</td>
        <td>${user.manager}</td>
        <td>
          <a href="User?action=update&id=${user.id}" class="btn btn-warning btn-sm">Edit</a>
          <a href="User?action=delete&id=${user.id}" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this user?');">Delete</a>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>

</body>
</html>
