<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="pageTitle" value="Danh sách Video"/>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/messages.jsp" %>

<div class="d-flex flex-column flex-md-row gap-2 justify-content-between align-items-md-center mb-3">
  <h3 class="m-0">Videos</h3>
  <form class="d-flex" action="<c:url value='/admin/videos/search'/>" method="get">
    <input type="text" class="form-control me-2" name="title" value="${fn:escapeXml(title)}" placeholder="Tìm theo tiêu đề...">
    <button class="btn btn-outline-secondary"><i class="bi bi-search"></i></button>
  </form>
</div>

<div class="card">
  <div class="card-body">
    <div class="d-flex justify-content-end mb-3">
      <a class="btn btn-primary" href="<c:url value='/admin/videos/add'/>"><i class="bi bi-plus-lg me-1"></i>New</a>
    </div>

    <c:choose>
      <c:when test="${page.totalElements > 0}">
        <table class="table table-hover align-middle">
          <thead>
          <tr>
            <th style="width:120px;">Mã</th>
            <th>Tiêu đề</th>
            <th>Danh mục</th>
            <th style="width:140px;">Poster</th>
            <th style="width:100px;">Views</th>
            <th style="width:110px;">Trạng thái</th>
            <th style="width:210px;" class="text-end">Thao tác</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach items="${page.content}" var="v">
            <tr>
              <td>${v.videoId}</td>
              <td>${v.title}</td>
              <td>${v.category != null ? v.category.categoryname : '-'}</td>
              <td>
                <c:if test="${not empty v.poster}">
                  <img src="<c:url value='/images/${v.poster}'/>" style="height:56px;border-radius:10px;">
                </c:if>
              </td>
              <td>${v.views}</td>
              <td>
                <span class="badge ${v.active ? 'text-bg-success' : 'text-bg-secondary'}">
                  ${v.active ? 'Active' : 'Inactive'}
                </span>
              </td>
              <td class="text-end">
                <a class="btn btn-sm btn-outline-warning" href="<c:url value='/admin/videos/edit/${v.videoId}'/>"><i class="bi bi-pencil-square"></i></a>
                <a class="btn btn-sm btn-outline-danger" href="<c:url value='/admin/videos/delete/${v.videoId}'/>"
                   onclick="return confirm('Xóa video này?');"><i class="bi bi-trash"></i></a>
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
                  <a class="page-link" href="<c:url value='/admin/videos/search?page=${i}&size=${page.size}&title=${fn:escapeXml(title)}'/>">${i+1}</a>
                </li>
              </c:forEach>
            </ul>
          </nav>
        </c:if>
      </c:when>
      <c:otherwise>
        <div class="empty">Không có video.</div>
      </c:otherwise>
    </c:choose>
  </div>
      <div class="d-flex justify-content-between">
    <a href="<c:url value='/admin/videos'/>" class="btn btn-secondary">&laquo; Quay lại</a>
  </div>
</div>

<%@ include file="/common/footer.jsp" %>
