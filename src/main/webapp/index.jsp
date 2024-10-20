<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet" />
    <title>DevSync _ home</title>
</head>
<body>

<jsp:include page="layouts/header.jsp" />

<div class="container-fluid bg-light">
    <section class="py-8 antialiased md:py-16 p-0 w-full">
        <div class="row justify-content-between px-4 py-4 h-full align-items-center">
            <div class="col-md-7 text-start">
                <h1 class="mb-4 display-4 font-weight-bold">Welcome to DevSync!<br />Tasks Management</h1>
                <p class="mb-4 text-muted">Discover amazing content and enjoy your stay.</p>
                <a href="${pageContext.request.contextPath}/users?action=register" class="btn btn-primary">
                    Get Started
                </a>
            </div>
        </div>
    </section>
</div>

<jsp:include page="layouts/footer.jsp" />

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>