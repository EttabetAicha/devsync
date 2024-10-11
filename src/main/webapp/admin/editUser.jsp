<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Edit User</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.0/css/bootstrap.min.css">
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.0/js/bootstrap.bundle.min.js"></script>
</head>
<body>

<div class="container mt-5">
  <h2>Edit User</h2>

  <form action="User" method="post">
    <input type="hidden" name="id" value="${user.id}">
    <div class="form-group">
      <label for="username">Username:</label>
      <input type="text" class="form-control" id="username" name="username" value="${user.username}" required>
    </div>
    <div class="form-group">
      <label for="firstname">First Name:</label>
      <input type="text" class="form-control" id="firstname" name="firstname" value="${user.firstname}" required>
    </div>
    <div class="form-group">
      <label for="lastname">Last Name:</label>
      <input type="text" class="form-control" id="lastname" name="lastname" value="${user.lastname}" required>
    </div>
    <div class="form-group">
      <label for="email">Email:</label>
      <input type="email" class="form-control" id="email" name="email" value="${user.email}" required>
    </div>
    <div class="form-group">
      <label for="typeUser">User Type:</label>
      <select class="form-control" id="typeUser" name="typeUser" required>
        <option value="USER" <c:if test="${user.manager == 'USER'}">selected</c:if>>User</option>
        <option value="MANAGER" <c:if test="${user.manager == 'MANAGER'}">selected</c:if>>Manager</option>
        <!-- Add other user types as needed -->
      </select>
    </div>
    <button type="submit" class="btn btn-primary">Update User</button>
    <a href="User?action=users" class="btn btn-secondary">Cancel</a>
  </form>
</div>

</body>
</html>
