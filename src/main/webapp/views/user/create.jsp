<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>User Login</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
  <style>
    body {
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
      background-color: #f8f9fa;
    }
    .login-container {
      max-width: 400px;
      width: 100%;
      padding: 20px;
      border-radius: 5px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      background-color: white;
    }
  </style>
</head>
<body>

<div class="login-container">
  <h2 class="text-center">Login</h2>

  <c:if test="${not empty sessionScope.errorMessage}">
    <div class="alert alert-danger">${sessionScope.errorMessage}</div>
    <c:remove var="errorMessage" scope="session" />
  </c:if>

  <form action="auth?action=login" method="post" class="needs-validation" novalidate>
    <div class="mb-3">
      <label for="username" class="form-label">Username</label>
      <input type="text" class="form-control" id="username" name="username" required>
      <div class="invalid-feedback">
        Please enter your username.
      </div>
    </div>

    <div class="mb-3">
      <label for="password" class="form-label">Password</label>
      <input type="password" class="form-control" id="password" name="password" required>
      <div class="invalid-feedback">
        Please enter your password.
      </div>
    </div>

    <button type="submit" class="btn btn-primary">Login</button>
    <a href="auth?action=register" class="text-center d-block mt-2">Don't have an account? Register here.</a>
  </form>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>

<script>
  (function () {
    'use strict';

    var forms = document.querySelectorAll('.needs-validation');

    Array.prototype.slice.call(forms)
            .forEach(function (form) {
              form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                  event.preventDefault();
                  event.stopPropagation();
                }
                form.classList.add('was-validated');
              }, false);
            });
  })();
</script>

</body>
</html>
