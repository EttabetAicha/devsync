<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.aicha.model.Request, org.aicha.model.User, java.util.List" %>
<%@ page import="org.aicha.model.enums.UserRole" %>

<!DOCTYPE html>
<html class="dark">
<head>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet"/>
    <title>DevSync _ Tasks-Edit</title>
</head>
<body>

<!-- Include header -->
<jsp:include page="../layouts/header.jsp"/>

<section class="bg-light py-3">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-body">
                        <form action="<%= request.getContextPath() %>/requests?action=acceptModify" method="post">
                            <input type="hidden" name="request_id" value="<%= ((Request) request.getAttribute("request")).getId() %>">
                            <div class="form-group">
                                <label for="modified_to">Assign to:</label>
                                <select class="form-control" id="modified_to" name="modified_to">
                                    <%
                                        @SuppressWarnings("unchecked")
                                        List<User> users = (List<User>) request.getAttribute("users");
                                        if (users != null) {
                                            for (User user : users) {
                                    %>
                                    <option value="<%= user.getId() %>"><%= user.getUsername() %></option>
                                    <%
                                            }
                                        }
                                    %>
                                </select>
                            </div>
                            <button type="submit" class="btn btn-primary btn-block">Assign Task</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<jsp:include page="../layouts/footer.jsp"/>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>