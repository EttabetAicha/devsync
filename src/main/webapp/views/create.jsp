<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create User</title>
    <style>
        form {
            max-width: 400px;
            margin: 0 auto;
        }
        label {
            display: block;
            margin-top: 10px;
        }
        input[type="text"], input[type="email"] {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
        }
        input[type="submit"] {
            margin-top: 20px;
            padding: 10px 20px;
        }
    </style>
</head>
<body>

<h2>Create New User</h2>

<form action="${pageContext.request.contextPath}/create" method="POST">
    <label for="username">Username</label>
    <input type="text" id="username" name="username" required>

    <label for="firstName">First Name</label>
    <input type="text" id="firstName" name="firstName" required>

    <label for="lastName">Last Name</label>
    <input type="text" id="lastName" name="lastName" required>

    <label for="email">Email</label>
    <input type="email" id="email" name="email" required>

    <label for="password">Password</label>
    <input type="password" id="password" name="password" required>

    <input type="submit" value="Create User">
</form>

<a href="${pageContext.request.contextPath}/list">Back to User List</a>

</body>
</html>
