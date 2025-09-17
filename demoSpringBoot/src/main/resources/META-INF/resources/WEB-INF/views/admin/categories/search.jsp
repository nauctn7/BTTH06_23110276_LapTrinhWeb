<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<c:set var="pageTitle" value="Danh sách Category"/>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/messages.jsp" %>

<div class="d-flex flex-column flex-md-row gap-2 justify-content-between align-items-md-center mb-3">
  <h3 class="m-0">Categories</h3>
  <form class="d-flex" action="<c:url value='/admin/categories/search'/>" method="get">
    <input type="text" class="form-control me-2" name="name" value="${fn:escapeXml(name)}" placeholder="Tìm theo tên...">
    <button class="btn btn-outline-secondary"><i class="bi bi-search"></i></button>
  </form>
</div>

<div class="card">
  <div class="card-body">
    <div class="d-flex justify-content-end mb-3">
      <a class="btn btn-primary" href="<c:url value='/admin/categories/add'/>"><i class="bi bi-plus-lg me-1"></i>New</a>
    </div>

    <c:choose>
      <c:when test="${(not empty page && page.totalElements > 0) || (not empty categories && categories.size() > 0)}">
        <table class="table table-hover align-middle">
          <thead><tr>
            <th style="width:90px">ID</th>
            <th>Tên</th>
            <th style="width:140px">Ảnh</th>
            <th style="width:120px">Trạng thái</th>
            <th style="width:220px" class="text-end">Thao tác</th>
          </tr></thead>
          <tbody>
            <c:forEach items="${not empty page ? page.content : categories}" var="c">
              <tr>
                <td>${c.categoryId != null ? c.categoryId : c.id}</td>
                <td>
                  <div class="fw-medium">${c.categoryname}</div>
                  <div class="text-secondary small">${empty c.categorycode ? '' : c.categorycode}</div>
                </td>
                <td>
                  <c:if test="${not empty c.images}">
                    <img src="<c:url value='/images/${c.images}'/>" style="height:56px;border-radius:10px;">
                  </c:if>
                </td>
                <!-- NEW: Badge trạng thái + nút toggle -->
      <td>
        <c:choose>
          <c:when test="${c.status}">
            <span class="badge text-bg-success">Kích hoạt</span>
          </c:when>
          <c:otherwise>
            <span class="badge text-bg-secondary">Ẩn</span>
          </c:otherwise>
        </c:choose>
      </td>
                  <!-- END NEW -->
                <td class="text-end">
                  <a class="btn btn-sm btn-outline-warning" href="<c:url value='/admin/categories/edit/${c.categoryId != null ? c.categoryId : c.id}'/>"><i class="bi bi-pencil-square"></i></a>
                  <a class="btn btn-sm btn-outline-danger" href="<c:url value='/admin/categories/delete/${c.categoryId != null ? c.categoryId : c.id}'/>"
                     onclick="return confirm('Xóa category này?');"><i class="bi bi-trash"></i></a>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>

        <c:if test="${not empty page && page.totalPages > 1}">
          <nav>
            <ul class="pagination justify-content-center">
              <c:forEach var="i" begin="0" end="${page.totalPages-1}">
                <li class="page-item ${i == page.number ? 'active' : ''}">
                  <a class="page-link" href="<c:url value='/admin/categories/search?page=${i}&size=${page.size}&name=${fn:escapeXml(name)}'/>">${i+1}</a>
                </li>
              </c:forEach>
            </ul>
          </nav>
        </c:if>
      </c:when>
      <c:otherwise>
        <div class="empty">
          <i class="bi bi-inbox fs-1 d-block mb-2"></i>
          Chưa có category nào. Hãy bấm <b>New</b> để tạo.
        </div>
      </c:otherwise>
    </c:choose>
      </div>
    <div class="d-flex justify-content-between">
    <a href="<c:url value='/admin/categories'/>" class="btn btn-secondary">&laquo; Quay lại</a>
  </div>
</div>

<%@ include file="/common/footer.jsp" %>
