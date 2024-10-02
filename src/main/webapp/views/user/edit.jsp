<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>Edit User</title>
</head>
<body>
<h1>Edit User</h1>
<form action="user?action=edit" method="post">
  <input type="hidden" name="id" value="${user.id}">
  <label for="username">Username:</label>
  <input type="text" id="username" name="username" value="${user.username}" required><br><br>
  <label for="email">Email:</label>
  <input type="email" id="email" name="email" value="${user.email}" required><br><br>
  <label for="password">New Password (leave blank to keep current):</label>
  <input type="password" id="password" name="password"><br><br>
  <input type="submit" value="Update User">
</form>
<a href="user?action=list">Back to User List</a>
</body>
</html>