<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Dashboard</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.0/css/bootstrap.min.css">
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.0/js/bootstrap.bundle.min.js"></script>
</head>
<body>

<div class="container mt-5">
  <h2>Dashboard</h2>
  <div class="row">
    <div class="col-md-4">
      <div class="card text-center">
        <div class="card-body">
          <h5 class="card-title">Manage Users</h5>
          <p class="card-text">Add, edit, or delete users from the system.</p>
          <a href="User?action=users" class="btn btn-primary">Go to Users</a>
        </div>
      </div>
    </div>
    <div class="col-md-4">
      <div class="card text-center">
        <div class="card-body">
          <h5 class="card-title">Manage Tags</h5>
          <p class="card-text">Create and manage tags for your tasks.</p>
          <a href="Tag?action=tags" class="btn btn-primary">Go to Tags</a>
        </div>
      </div>
    </div>
    <div class="col-md-4">
      <div class="card text-center">
        <div class="card-body">
          <h5 class="card-title">Manage Tasks</h5>
          <p class="card-text">View, add, or manage tasks.</p>
          <a href="Task?action=tasks" class="btn btn-primary">Go to Tasks</a>
        </div>
      </div>
    </div>
  </div>
</div>

</body>
</html>
