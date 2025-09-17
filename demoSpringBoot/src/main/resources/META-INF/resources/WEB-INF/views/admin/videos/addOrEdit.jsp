<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<c:set var="pageTitle" value="${video.isEdit ? 'Sửa Video' : 'Thêm Video'}"/>
<%@ include file="/common/header.jsp" %>

<form action="<c:url value='/admin/videos/saveOrUpdate'/>" method="post" enctype="multipart/form-data" class="card shadow-sm p-4">
  <input type="hidden" name="videoId" value="${video.videoId}"/>
  <input type="hidden" name="poster"  value="${video.poster}"/>
  <input type="hidden" name="isEdit"  value="${video.isEdit}"/>

  <div class="mb-3">
    <label class="form-label">Tiêu đề</label>
    <input type="text" class="form-control" name="title" value="${video.title}" required>
  </div>

  <div class="mb-3">
    <label class="form-label">Mô tả</label>
    <textarea class="form-control" rows="3" name="description" required>${video.description}</textarea>
  </div>

  <div class="row">
    <div class="col-md-6 mb-3">
      <label class="form-label">Thuộc danh mục</label>
     <select class="form-select" name="categoryId">
  <option value="">-- Chọn --</option>
  <c:set var="lastName" value=""/>
  <c:forEach items="${categories}" var="cat">
    <c:if test="${cat.categoryname ne lastName}">
      <option value="${cat.categoryId}"
        ${video.categoryId == cat.categoryId ? 'selected' : ''}>
        ${cat.categoryname}
      </option>
      <c:set var="lastName" value="${cat.categoryname}"/>
    </c:if>
  </c:forEach>
</select>

    </div>

    <div class="col-md-3 mb-3">
      <label class="form-label">Lượt xem</label>
      <input type="number" class="form-control" name="views" value="${video.views}" min="0">
    </div>

    <div class="col-md-3 mb-3">
      <label class="form-label">Kích hoạt</label>
      <div class="form-check">
        <input class="form-check-input" type="checkbox" name="active" ${video.active ? 'checked' : ''}>
        <label class="form-check-label">Active</label>
      </div>
    </div>
  </div>

  <div class="mb-3">
    <label class="form-label">Poster</label>
    <input class="form-control" type="file" name="posterFile" accept="image/*">
    <c:if test="${not empty video.poster}">
      <div class="mt-2">
        <img src="<c:url value='/images/${video.poster}'/>" style="height:90px;border-radius:10px;">
      </div>
    </c:if>
  </div>

  <div class="d-flex justify-content-between">
    <a href="<c:url value='/admin/videos'/>" class="btn btn-secondary">&laquo; Quay lại</a>
     <button class="btn btn-primary"><i class="bi bi-save"></i>
          <c:choose><c:when test="${video.isEdit}"> Update</c:when><c:otherwise> Save</c:otherwise></c:choose>
  </div>

</form>

<%@ include file="/common/footer.jsp" %>
