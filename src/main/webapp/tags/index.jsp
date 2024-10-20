<%@ page import="org.aicha.model.Tag" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet"/>
    <title>DevSync _ tags</title>
</head>
<body>

<jsp:include page="../layouts/header.jsp"/>

<section class="bg-light py-3 sm:py-5">
    <div class="container">
        <div class="table-responsive bg-white shadow-sm rounded-lg">
            <table class="table table-striped">
                <thead class="thead-light">
                <tr>
                    <th scope="col" class="text-center">
                        <a href="<%= request.getContextPath() %>/tags?action=create" class="btn btn-outline-primary btn-sm">
                            Create Tag
                        </a>
                    </th>
                    <th scope="col" class="text-center">Name</th>
                    <th scope="col" class="text-center">Usages</th>
                    <th scope="col" class="text-center">Actions</th>
                </tr>
                </thead>
                <tbody>
                <%
                    List<Tag> tags = (List<Tag>) request.getAttribute("tags");
                    if (tags == null || tags.isEmpty()) {
                %>
                <tr>
                    <td colspan="4" class="text-center p-4">No tags found</td>
                </tr>
                <%
                } else {
                    for (Tag tag : tags) {
                %>
                <tr>
                    <td class="text-center"><%= tag.getId() %></td>
                    <td class="text-center"><%= tag.getName() %></td>
                    <td class="text-center"><%= tag.getUsages() %></td>
                    <td class="text-center">
                        <a href="<%= request.getContextPath() %>/tags?action=edit&id=<%= tag.getId() %>" class="btn btn-outline-secondary btn-sm">Edit</a>
                        <form action="<%= request.getContextPath() %>/tags?action=delete" method="post" class="d-inline">
                            <input type="hidden" name="id" value="<%= tag.getId() %>">
                            <button type="submit" class="btn btn-outline-danger btn-sm delete-btn">Delete</button>
                        </form>
                    </td>
                </tr>
                <%
                        }
                    }
                %>
                </tbody>
            </table>
        </div>
    </div>
</section>

<jsp:include page="../layouts/footer.jsp"/>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
    const deleteBtns = document.querySelectorAll('.delete-btn');
    deleteBtns.forEach(btn => {
        btn.addEventListener('click', (e) => {
            e.preventDefault();
            const isConfirmed = confirm('Are you sure you want to delete this tag?');
            if (isConfirmed) {
                e.target.closest('form').submit();
            }
        });
    });
</script>
</body>
</html>