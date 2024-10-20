<%@ page import="org.aicha.model.User" %>
<%@ page import="org.aicha.model.Request" %>
<%@ page import="org.aicha.model.enums.RequestStatus" %>
<%@ page import="org.aicha.model.enums.UserRole" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet"/>
    <title>DevSync _ Requests</title>
</head>
<body>

<jsp:include page="../layouts/header.jsp"/>

<div class="container-fluid bg-light">

    <section class="py-3 sm:py-5">
        <div class="px-4 mx-auto">
            <div class="table-responsive bg-white shadow-sm rounded-lg">
                <table class="table table-striped">
                    <thead class="thead-light">
                    <tr>
                        <th scope="col">Requester</th>
                        <th scope="col">Task</th>
                        <th scope="col">Type</th>
                        <th scope="col">Requested at</th>
                        <th scope="col">Status</th>
                        <% User user = (User) session.getAttribute("user"); %>
                        <% if (user.getRole() == UserRole.manager) { %>
                        <th scope="col">Actions</th>
                        <% } %>
                    </tr>
                    </thead>
                    <tbody>

                    <%
                        List<Request> requests = (List<Request>) request.getAttribute("requests");
                        if (requests == null || requests.isEmpty()) {
                    %>
                    <tr>
                        <td colspan="6" class="text-center p-4">No requests found</td>
                    </tr>
                    <% } else { %>
                    <% for (Request req : requests) { %>
                    <tr>
                        <td><%= req.getRequester().getUsername() %></td>
                        <td>
                            <span class="badge badge-primary"><%= req.getTask().getTitle() %></span>
                        </td>
                        <td>
                            <span class="badge badge-secondary"><%= req.getType() %></span>

                        </td>
                        <td><%= req.getRequestedAt() %></td>
                        <td>
                            <% if (req.getStatus() == RequestStatus.PENDING) { %>
                            <span class="badge badge-warning">Pending</span>
                            <% } else if (req.getStatus() == RequestStatus.REJECTED) { %>
                            <span class="badge badge-secondary">Rejected</span>
                            <% } else if (req.getStatus() == RequestStatus.ACCEPTED) { %>
                            <span class="badge badge-success">Accepted</span>
                            <% } else if (req.getStatus() == RequestStatus.DENIED) { %>
                            <span class="badge badge-dark">Denied</span>
                            <% } %>
                        </td>
                        <% if (user.getRole() == UserRole.manager) { %>
                        <td>
                            <% if (req.getStatus() == RequestStatus.PENDING || req.getStatus() == RequestStatus.REJECTED) { %>
                            <form action="${pageContext.request.contextPath}/requests?action=accept" method="post" class="d-inline">
                                <input type="hidden" name="id" value="<%= req.getId() %>">
                                <button type="submit" class="btn btn-outline-primary btn-sm">Accept</button>
                            </form>
                            <% } %>
                            <% if (req.getStatus() == RequestStatus.PENDING) { %>
                            <form action="${pageContext.request.contextPath}/requests?action=reject" method="post" class="d-inline">
                                <input type="hidden" name="id" value="<%= req.getId() %>">
                                <button type="submit" class="btn btn-outline-warning btn-sm">Reject</button>
                            </form>
                            <% } %>
                        </td>
                        <% } %>
                    </tr>
                    <% } %>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </section>

</div>

<jsp:include page="../layouts/footer.jsp"/>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
    const deleteBtns = document.querySelectorAll('.delete-btn');
    deleteBtns.forEach(btn => {
        btn.addEventListener('click', (e) => {
            e.preventDefault();
            const isConfirmed = confirm('Are you sure you want to delete this task?');
            if (isConfirmed) {
                e.target.closest('form').submit();
            }
        });
    });
</script>
</body>
</html>