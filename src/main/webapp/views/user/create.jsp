<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>Create User</title>
</head>
<body>
<h1>Create User</h1>
<form action="?action=create" method="post">
  <label for="username">Username:</label>
  <input type="text" id="username" name="username" required><br><br>
  <label for="email">Email:</label>
  <input type="email" id="email" name="email" required><br><br>
  <label for="password">Password:</label>
  <input type="password" id="password" name="password" required><br><br>
  <input type="submit" value="Create User">
</form>
<a href="?action=list">Back to User List</a>
</body>
</html>