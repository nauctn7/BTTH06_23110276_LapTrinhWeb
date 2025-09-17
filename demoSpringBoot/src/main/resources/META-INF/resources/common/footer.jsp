</div><!-- đóng .container -->

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!-- Bootstrap Icons -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">

<footer class="border-top py-3 bg-light">
  <div class="container d-flex flex-column flex-md-row justify-content-between align-items-center text-secondary small">
    
    <!-- Bên trái -->
    <div class="mb-2 mb-md-0">
      <jsp:useBean id="now" class="java.util.Date" />
      © <fmt:formatDate value="${now}" pattern="yyyy"/> - Spring Boot JSP Demo - 23110276 - Cap Nhan Thanh - HCMUTE
    </div>

    <!-- Bên phải -->
    <div class="d-flex gap-3">
      <a href="mailto:thanhnau25@gmail.com" class="text-secondary text-decoration-none">
        <i class="bi bi-envelope-fill me-1"></i>Email
      </a>
      <a href="https://facebook.com/thanhcap.nhan" target="_blank" class="text-secondary text-decoration-none">
        <i class="bi bi-facebook me-1"></i>Facebook
      </a>
      <a href="https://github.com/nauctn7" target="_blank" class="text-secondary text-decoration-none">
        <i class="bi bi-github me-1"></i>GitHub
      </a>
    </div>
  </div>
</footer>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
