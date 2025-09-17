<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<c:set var="pageTitle" value="Quản lý người dùng"/>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/messages.jsp" %>

<div class="d-flex flex-column flex-md-row gap-2 justify-content-between align-items-md-center mb-3">
  <h3 class="m-0">Người dùng</h3>
  <form class="d-flex" action="<c:url value='/admin/users/search'/>" method="get">
    <input type="text" class="form-control me-2" name="q" value="${fn:escapeXml(q)}" placeholder="Tìm username / họ tên...">
    <button class="btn btn-outline-secondary"><i class="bi bi-search"></i></button>
  </form>
</div>

<div class="card">
  <div class="card-body">
    <div class="d-flex justify-content-end mb-3">
      <a class="btn btn-primary" href="<c:url value='/admin/users/add'/>"><i class="bi bi-plus-lg me-1"></i>New</a>
    </div>

    <c:choose>
      <c:when test="${page.totalElements > 0}">
        <table class="table table-hover align-middle">
          <thead>
          <tr>
            <th style="width:80px">Avatar</th>
            <th>Username</th>
            <th>Họ tên</th>
            <th>Email</th>
            <th>Phone</th>
            <th style="width:110px">Trạng thái</th>
            <th style="width:160px" class="text-end">Thao tác</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach items="${page.content}" var="u">
            <tr>
              <td>
                <img src="<c:url value='/images/${empty u.images ? "noimages.png" : u.images}'/>"
                     style="height:48px;border-radius:10px;">
              </td>
              <td>${u.username}</td>
              <td>${u.fullname}</td>
              <td>${u.email}</td>
              <td>${u.phone}</td>
              <td>
                <span class="badge ${u.active ? 'text-bg-success' : 'text-bg-secondary'}">
                  ${u.active ? 'Kích hoạt' : 'Khóa'}
                </span>
              </td>
              <td class="text-end">
                <a class="btn btn-sm btn-outline-warning" href="<c:url value='/admin/users/edit/${u.username}'/>">
                  <i class="bi bi-pencil-square"></i>
                </a>
                <a class="btn btn-sm btn-outline-danger"
                   href="<c:url value='/admin/users/delete/${u.username}'/>"
                   onclick="return confirm('Xóa người dùng này?');">
                  <i class="bi bi-trash"></i>
                </a>
              </td>
            </tr>
          </c:forEach>
          </tbody>
        </table>

        <c:if test="${page.totalPages > 1}">
          <nav>
            <ul class="pagination justify-content-center">
              <c:forEach var="i" begin="0" end="${page.totalPages-1}">
                <li class="page-item ${i == page.number ? 'active' : ''}">
                  <a class="page-link" href="<c:url value='/admin/users/search?page=${i}&size=${page.size}&q=${fn:escapeXml(q)}'/>">${i+1}</a>
                </li>
              </c:forEach>
            </ul>
          </nav>
        </c:if>
      </c:when>
      <c:otherwise>
        <div class="empty">
          <i class="bi bi-inbox fs-1 d-block mb-2"></i>
          Chưa có người dùng nào.
        </div>
      </c:otherwise>
    </c:choose>
  </div>
    <div class="d-flex justify-content-between">
    <a href="<c:url value='/admin/users'/>" class="btn btn-secondary">&laquo; Quay lại</a>
  </div>
</div>

<%@ include file="/common/footer.jsp" %>
