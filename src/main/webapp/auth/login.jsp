<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet" />
    <title>DevSync  Login</title>
</head>
<body>

<jsp:include page="../layouts/header.jsp" />

<section class="bg-light d-flex align-items-center justify-content-center" style="min-height: 100vh;">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="text-center mb-4">

                </div>
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title text-center">Sign in to your account</h5>
                        <form action="${pageContext.request.contextPath}/users?action=login" method="post">
                            <div class="form-group">
                                <label for="email">Your email</label>
                                <input type="email" name="email" id="email" class="form-control" placeholder="name@company.com" required>
                            </div>
                            <div class="form-group">
                                <label for="password">Password</label>
                                <input type="password" name="password" id="password" class="form-control" placeholder="••••••••" required>
                            </div>
                            <p id="error" class="text-center text-danger" style="display: none;">Invalid credentials</p>
                            <button type="submit" class="btn btn-primary btn-block">Login</button>
                            <p class="text-center mt-3">
                                Don’t have an account yet? <a href="${pageContext.request.contextPath}/users?action=register" class="text-primary">Sign up</a>
                            </p>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<jsp:include page="../layouts/footer.jsp" />

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
    const urlParams = new URLSearchParams(window.location.search);
    const error = urlParams.get('error');
    if (error) {
        document.getElementById('error').style.display = 'block';
    }
</script>
</body>
</html>