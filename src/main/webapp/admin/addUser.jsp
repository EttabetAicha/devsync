<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>Add Client</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"> <!-- Bootstrap CSS -->
</head>
<body>
<div class="container mt-5">
  <h1 class="mb-4">Add New Client</h1>
  <form action="User" method="post">
    <input type="hidden" name="action" value="addUser">

    <div class="form-group">
      <%--@declare id="firstname"--%><label for="firstname">First Name:</label>
      <input type="text" class="form-control" name="firstname" required>
    </div>

    <div class="form-group">
      <%--@declare id="lastname"--%>
        <label for="lastname">Last Name:</label>
      <input type="text" class="form-control" name="lastname" required>
    </div>

    <div class="form-group">
      <%--@declare id="email"--%>
        <label for="email">Email:</label>
      <input type="email" class="form-control" name="email" required>
    </div>

    <div class="form-group">
      <%--@declare id="username"--%>
        <label for="username">Username:</label>
      <input type="text" class="form-control" name="username" required>
    </div>

    <div class="form-group">
      <%--@declare id="typeuser"--%>
        <label for="typeUser">User Type:</label>
      <select class="form-control" name="typeUser" required>
        <option value="USER">User</option>
        <option value="MANAGER">Manager</option>
      </select>
    </div>

    <button type="submit" class="btn btn-primary">Add Client</button>
  </form>
  <a href="User?action=users" class="btn btn-secondary mt-3">Back to Clients List</a>
</div>

<!-- Optional JavaScript; choose one of the two! -->
<!-- Option 1: jQuery and Bootstrap Bundle (includes Popper) -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
