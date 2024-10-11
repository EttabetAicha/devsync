<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title><c:if test="${not empty task}">Edit Task</c:if><c:if test="${empty task}">Add Task</c:if></title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
  <h1 class="mb-4"><c:if test="${not empty task}">Edit Task</c:if><c:if test="${empty task}">Add Task</c:if></h1>

  <form action="Task" method="post">
    <input type="hidden" name="actionType" value="<c:if test="${not empty task}">updateTask</c:if><c:if test="${empty task}">addTask</c:if>">
    <c:if test="${not empty task}">
      <input type="hidden" name="idTask" value="${task.id}">
    </c:if>

    <div class="form-group">
      <label for="title">Title</label>
      <input type="text" class="form-control" id="title" name="title" value="${not empty task ? task.title : ''}" required>
    </div>

    <div class="form-group">
      <label for="description">Description</label>
      <textarea class="form-control" id="description" name="description" required>${not empty task ? task.description : ''}</textarea>
    </div>

    <div class="form-group">
      <label for="creationDate">Creation Date</label>
      <input type="date" class="form-control" id="creationDate" name="creationDate" value="${not empty task ? task.creationDate : ''}" required>
    </div>

    <div class="form-group">
      <label for="endDate">End Date</label>
      <input type="date" class="form-control" id="endDate" name="endDate" value="${not empty task ? task.endDate : ''}" required>
    </div>

    <div class="form-group">
      <label for="user_id">Creator</label>
      <select class="form-control" id="user_id" name="user_id" required>
        <c:forEach var="user" items="${userList}">
          <option value="${user.id}" <c:if test="${not empty task && task.user.id == user.id}">selected</c:if>>${user.name}</option>
        </c:forEach>
      </select>
    </div>

    <div class="form-group">
      <label for="assigneeTo_id">Assignee</label>
      <select class="form-control" id="assigneeTo_id" name="assigneeTo_id" required>
        <c:forEach var="user" items="${userList}">
          <option value="${user.id}" <c:if test="${not empty task && task.assigneeTo.id == user.id}">selected</c:if>>${user.name}</option>
        </c:forEach>
      </select>
    </div>

    <div class="form-group">
      <label for="tags">Tags</label>
      <select multiple class="form-control" id="tags" name="tages[]" required>
        <c:forEach var="tag" items="${tagesList}">
          <option value="${tag.id}" <c:if test="${not empty task && task.tag.contains(tag)}">selected</c:if>>${tag.name}</option>
        </c:forEach>
      </select>
    </div>

    <div class="form-group">
      <label for="isCompleted">Status</label>
      <select class="form-control" id="isCompleted" name="isCompleted" required>
        <option value="PENDING" <c:if test="${not empty task && task.isCompleted == 'PENDING'}">selected</c:if>>Pending</option>
        <option value="COMPLETED" <c:if test="${not empty task && task.isCompleted == 'COMPLETED'}">selected</c:if>>Completed</option>
      </select>
    </div>

    <button type="submit" class="btn btn-success">Save Task</button>
    <a href="Task" class="btn btn-secondary">Cancel</a>
  </form>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
