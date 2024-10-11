<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Task List</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
  <h1 class="mb-4">Task List</h1>
  <a href="Task?actionType=Add Task" class="btn btn-primary mb-3">Add Task</a>

  <table class="table table-bordered">
    <thead>
    <tr>
      <th>ID</th>
      <th>Title</th>
      <th>Description</th>
      <th>Creation Date</th>
      <th>End Date</th>
      <th>Status</th>
      <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="task" items="${taskList}">
      <tr>
        <td>${task.id}</td>
        <td>${task.title}</td>
        <td>${task.description}</td>
        <td>${task.creationDate}</td>
        <td>${task.endDate}</td>
        <td>${task.isCompleted ? 'Completed' : 'Pending'}</td>
        <td>
          <a href="Task?action=updateTask&id=${task.id}" class="btn btn-warning btn-sm">Edit</a>
          <a href="Task?action=deleteTask&id=${task.id}" class="btn btn-danger btn-sm">Delete</a>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
