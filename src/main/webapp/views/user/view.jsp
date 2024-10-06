<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>View User</title>

  <!-- Include Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-5">
<h1 class="mb-4">User Details</h1>

<!-- Card displaying user details -->
<div class="card">
  <div class="card-header">
    <h5>User Information</h5>
  </div>
  <div class="card-body">
    <!-- Display User ID -->
    <p class="card-text"><strong>ID:</strong> ${user.id}</p>

    <!-- Display Username -->
    <p class="card-text"><strong>Username:</strong> ${user.username}</p>

    <!-- Display Email -->
    <p class="card-text"><strong>Email:</strong> ${user.email}</p>

    <!-- Edit and Back buttons -->
    <a href="user?action=edit&id=${user.id}" class="btn btn-primary">Edit</a>
    <a href="user?action=list" class="btn btn-secondary">Back to User List</a>
  </div>
</div>

<!-- Bootstrap JS and Popper.js -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>
