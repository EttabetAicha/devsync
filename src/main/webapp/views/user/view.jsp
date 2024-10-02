<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>View User</title>
</head>
<body>
<h1>User Details</h1>
<p><strong>ID:</strong> ${user.id}</p>
<p><strong>Username:</strong> ${user.username}</p>
<p><strong>Email:</strong> ${user.email}</p>
<a href="user?action=edit&id=${user.id}">Edit</a>
<a href="user?action=list">Back to User List</a>
</body>
</html>