<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet"/>
  <title>DevSync - Tags-edit</title>
</head>
<body>

<jsp:include page="../layouts/header.jsp"/>

<section class="bg-light py-3 sm:py-5">
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-md-6">
        <div class="card">
          <div class="card-body">
            <h5 class="card-title text-center">Edit Tag</h5>
            <form action="${pageContext.request.contextPath}/tags?action=update" method="post">
              <input type="hidden" name="id" value="${tag.id}">
              <div class="form-group">
                <label for="name">Enter Tag name</label>
                <input type="text" name="name" id="name" value="${tag.name}" class="form-control" placeholder="Tag name" required>
              </div>
              <button type="submit" class="btn btn-primary btn-block mt-3">Update</button>
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