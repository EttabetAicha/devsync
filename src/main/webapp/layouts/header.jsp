<!-- Add the Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

<style>
    section {
        min-height: 100vh !important;
    }

    .custom-radio-selector input[type="radio"] {
        display: none;
    }

    .custom-radio-selector label {
        display: block;
        padding: 0.5rem 1rem;
        cursor: pointer;
        transition: all 150ms ease-in-out;
    }

    .custom-radio-selector input[type="radio"]:checked + label {
        color: #7ab3fa;
        border: 1px solid #215fad;
        border-radius: 10px;
    }
</style>

<%@ page import="org.aicha.model.User" %>
<%@ page import="org.aicha.model.enums.UserRole" %>
<%@ page import="org.aicha.model.User" %>
<%
    User user = (User) session.getAttribute("user");
%>

<header>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}">DevSync</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/tasks">Tasks</a>
                    </li>
                    <%
                        if (user != null && user.getRole() == UserRole.manager) {
                    %>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/tags">Tags</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/statistics">Statistics</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/users">Users</a>
                    </li>
                    <%
                        }
                    %>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/requests">Requests</a>
                    </li>
                </ul>

                <div class="d-flex">
                    <%
                        if (user != null) {
                    %>
                    <div class="d-flex align-items-center me-3">
                        <img class="rounded-circle me-2" src="https://flowbite.com/docs/images/people/profile-picture-3.jpg" width="40" height="40" alt="">
                        <div class="text-dark">
                            <span>${user.username}</span>
                            <span class="badge bg-primary">
                                <%
                                    if (user.getRole() == UserRole.manager) {
                                        out.print("Manager");
                                    } else {
                                        out.print("User");
                                    }
                                %>
                            </span>
                            <div class="text-muted">${user.email}</div>
                        </div>
                    </div>
                    <a href="${pageContext.request.contextPath}/users?action=logout" class="btn btn-outline-dark">Log out</a>
                    <%
                    } else {
                    %>
                    <a href="${pageContext.request.contextPath}/users?action=login" class="btn btn-outline-dark me-2">Log in</a>
                    <a href="${pageContext.request.contextPath}/users?action=register" class="btn btn-primary">Register</a>
                    <%
                        }
                    %>
                </div>
            </div>
        </div>
    </nav>
</header>

<!-- Add the Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
