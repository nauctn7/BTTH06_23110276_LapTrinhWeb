<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>${empty pageTitle ? 'Admin' : pageTitle}</title>

  <!-- Bootstrap CSS (PHẢI có) -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
  <!-- Bootstrap Icons (tùy chọn) -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet" />
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container">
    <a class="navbar-brand" href="<c:url value='/'/>">Admin</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#nav">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div id="nav" class="collapse navbar-collapse">
      <ul class="navbar-nav me-auto">
        <li class="nav-item"><a class="nav-link" href="<c:url value='/admin/categories'/>">Category</a></li>
        <li class="nav-item"><a class="nav-link" href="<c:url value='/admin/videos'/>">Video</a></li>
        <li class="nav-item"><a class="nav-link" href="<c:url value='/admin/users'/>">User</a></li>
      </ul>
    </div>
  </div>
</nav>

<div class="container py-4"><!-- mở container -->
