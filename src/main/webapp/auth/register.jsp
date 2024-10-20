<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet" />
    <title>DevSync _ Register</title>
</head>
<body>

<jsp:include page="../layouts/header.jsp" />

<section class="bg-light d-flex align-items-center justify-content-center" style="min-height: 100vh;">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card shadow-lg">
                    <div class="card-body p-6">
                        <h1 class="text-center text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white">
                            Create an account
                        </h1>
                        <form class="space-y-4 md:space-y-6" action="${pageContext.request.contextPath}/users?action=register" method="post">
                            <div class="form-group">
                                <label for="username" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Your Username</label>
                                <input type="text" name="username" id="username" class="form-control" placeholder="Your Username" required>
                            </div>
                            <div class="form-group">
                                <label for="email" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Your email</label>
                                <input type="email" name="email" id="email" class="form-control" placeholder="name@company.com" required>
                            </div>
                            <div class="form-group">
                                <label for="password" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Password</label>
                                <input type="password" name="password" id="password" class="form-control" placeholder="••••••••" required>
                            </div>
                            <div class="form-group">
                                <label for="firstName" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">First Name</label>
                                <input type="text" name="firstName" id="firstName" class="form-control" placeholder="First Name" required>
                            </div>
                            <div class="form-group">
                                <label for="lastName" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Last Name</label>
                                <input type="text" name="lastName" id="lastName" class="form-control" placeholder="Last Name" required>
                            </div>
                            <div class="form-group">
                                <label for="role" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Role</label>
                                <select name="role" id="role" class="form-control">
                                    <option selected value="user">User</option>
                                    <option value="manager">Manager</option>
                                </select>
                            </div>
                            <button type="submit" class="btn btn-primary btn-block">
                                Create an Account
                            </button>
                            <p class="text-center text-sm font-light text-gray-500 dark:text-gray-400">
                                Already have an account? <a href="${pageContext.request.contextPath}/users?action=login" class="font-medium text-primary-600 hover:underline dark:text-primary-500">Login here</a>
                            </p>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>