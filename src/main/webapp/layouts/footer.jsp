<footer class="bg-light text-dark">
    <div class="container py-4 d-flex flex-column flex-md-row justify-content-between align-items-center">
        <ul class="nav mt-3 mt-md-0">
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}" class="nav-link px-2 text-muted">About</a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}" class="nav-link px-2 text-muted">Privacy Policy</a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}" class="nav-link px-2 text-muted">Licensing</a>
            </li>
            <li class="nav-item">
                <a href="${pageContext.request.contextPath}" class="nav-link px-2 text-muted">Contact</a>
            </li>
        </ul>
    </div>
</footer>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
    function getUrlParameter(name) {
        name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
        var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
        var results = regex.exec(location.search);
        return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
    }


    var errorMessage = getUrlParameter('error');
    if (errorMessage) {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: errorMessage,
            confirmButtonText: 'OK',
            confirmButtonColor: '#3085d6'
        });
    }

    var infoMessage = getUrlParameter('info');
    if (infoMessage) {
        Swal.fire({
            icon: 'info',
            title: 'Info',
            position: 'top-end',
            toast: true,
            timer: 4000,
        });
    }
    // Check if there's a success
    var successMessage = getUrlParameter('success');
    if (successMessage) {
        Swal.fire({
            icon: 'success',
            title: 'Success',
            text: successMessage,
            position: 'top-end',
            toast: true,
            timer: 4000,
        });
    }

    var currentUrl = new URL(window.location.href);
    currentUrl.searchParams.delete('success');
    currentUrl.searchParams.delete('info');
    currentUrl.searchParams.delete('error');
    window.history.replaceState({}, document.title, currentUrl.toString());
</script>