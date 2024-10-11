<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Register - DevSync</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
  <h2 class="text-center">Register for DevSync</h2>
  <form action="auth" method="post">
    <input type="hidden" name="action" value="register">

    <div class="form-group">
      <label for="username">Username</label>
      <input type="text" class="form-control" id="username" name="username" required>
    </div>

    <div class="form-group">
      <label for="firstname">First Name</label>
      <input type="text" class="form-control" id="firstname" name="firstname" required>
    </div>

    <div class="form-group">
      <label for="lastname">Last Name</label>
      <input type="text" class="form-control" id="lastname" name="lastname" required>
    </div>

    <div class="form-group">
      <label for="email">Email</label>
      <input type="email" class="form-control" id="email" name="email" required>
    </div>

    <div class="form-group">
      <label for="password">Password</label>
      <input type="password" class="form-control" id="password" name="password" required>
    </div>

    <div class="form-group">
      <label for="userType">User Type</label>
      <select name="userType" id="userType" class="form-control" required>
        <option value="">Select User Type</option>
        <option value="USER">User</option>
        <option value="MANAGER">Manager</option>
      </select>
    </div>

    <div class="text-center">
      <button type="submit" class="btn btn-primary">Register</button>
      <p class="mt-3"><a href="index.jsp">Already have an account? Login here</a></p>
    </div>

    <!-- Display error message without JSTL -->
    <div class="alert alert-danger mt-3" style="display: none;" id="error-message"></div>

  </form>
</div>

<script>
  // Function to show error message if it exists
  const urlParams = new URLSearchParams(window.location.search);
  const errorMessage = urlParams.get('message'); // Assuming the servlet passes the message as a query parameter

  if (errorMessage) {
    const errorDiv = document.getElementById('error-message');
    errorDiv.textContent = errorMessage;
    errorDiv.style.display = 'block';
  }
</script>

</body>
</html>
