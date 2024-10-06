<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>Edit User</title>

  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-5">
<h1 class="mb-4">Edit User</h1>
<form action="user?action=edit" method="post" class="needs-validation" novalidate>

  <input type="hidden" name="id" value="${user.id}">

  <div class="mb-3">
    <label for="username" class="form-label">Username</label>
    <input type="text" class="form-control" id="username" name="username" value="${user.username}" required>
    <div class="invalid-feedback">
      Please enter a username.
    </div>
  </div>

  <div class="mb-3">
    <label for="email" class="form-label">Email</label>
    <input type="email" class="form-control" id="email" name="email" value="${user.email}" required>
    <div class="invalid-feedback">
      Please enter a valid email address.
    </div>
  </div>

  <div class="mb-3">
    <label for="password" class="form-label">New Password (leave blank to keep current)</label>
    <input type="password" class="form-control" id="password" name="password">
  </div>

  <div class="mb-3">
    <label for="isManager" class="form-label">Is Manager</label>
    <select class="form-select" id="ismanager" name="ismanager" required>
      <option value="" disabled ${!user.isManager ? 'selected' : ''}>Select Manager Status</option>
      <option value="true" ${user.isManager ? 'selected' : ''}>Yes</option>
      <option value="false" ${!user.isManager ? 'selected' : ''}>No</option>
    </select>
    <div class="invalid-feedback">
      Please select a manager status.
    </div>
  </div>

  <button type="submit" class="btn btn-primary">Update User</button>
  <a href="user?action=list" class="btn btn-secondary">Back to User List</a>
</form>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>

<script>
  (function () {
    'use strict'
    var forms = document.querySelectorAll('.needs-validation')
    Array.prototype.slice.call(forms)
            .forEach(function (form) {
              form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                  event.preventDefault()
                  event.stopPropagation()
                }
                form.classList.add('was-validated')
              }, false)
            })
  })()
</script>
</body>
</html>
