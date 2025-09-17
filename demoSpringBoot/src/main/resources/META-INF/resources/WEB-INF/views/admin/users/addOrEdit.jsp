<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<c:set var="pageTitle" value="${user.isEdit ? 'Sửa người dùng' : 'Thêm người dùng'}"/>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/messages.jsp" %>

<form action="<c:url value='/admin/users/saveOrUpdate'/>" method="post" enctype="multipart/form-data" class="card shadow-sm p-4">
  <input type="hidden" name="images" value="${user.images}"/>
  <input type="hidden" name="isEdit" value="${user.isEdit}"/>

  <div class="mb-3">
    <label class="form-label">Tên đăng nhập</label>
    <input type="text" class="form-control" name="username" value="${user.username}" ${user.isEdit ? 'readonly' : ''} required>
  </div>

  <div class="mb-3">
    <label class="form-label">Mật khẩu <span class="text-secondary small">(để trống nếu không đổi)</span></label>
    <input type="password" class="form-control" name="password">
  </div>

  <div class="row">
    <div class="col-md-6 mb-3">
      <label class="form-label">Họ tên</label>
      <input type="text" class="form-control" name="fullname" value="${user.fullname}">
    </div>
    <div class="col-md-6 mb-3">
      <label class="form-label">Email</label>
      <input type="email" class="form-control" name="email" value="${user.email}">
    </div>
  </div>

  <div class="row">
    <div class="col-md-6 mb-3">
      <label class="form-label">Số điện thoại</label>
      <input type="text" class="form-control" name="phone" value="${user.phone}">
    </div>
    <div class="col-md-6 mb-3">
      <label class="form-label">Trạng thái</label><br/>
      <div class="form-check form-switch">
        <input class="form-check-input" type="checkbox" name="active" ${user.active ? 'checked' : ''}>
        <label class="form-check-label">Kích hoạt</label>
      </div>
    </div>
  </div>

  <div class="mb-3">
    <label class="form-label">Ảnh đại diện</label>
    <input class="form-control" type="file" name="imageFile" accept="image/*">
    <c:if test="${not empty user.images}">
      <div class="mt-2">
        <img src="<c:url value='/images/${user.images}'/>" style="height:90px;border-radius:10px;">
      </div>
    </c:if>
  </div>
  <div class="d-flex justify-content-between">
    <a href="<c:url value='/admin/users'/>" class="btn btn-secondary">&laquo; Quay lại</a>
     <button class="btn btn-primary"><i class="bi bi-save"></i>
          <c:choose><c:when test="${user.isEdit}"> Update</c:when><c:otherwise> Save</c:otherwise></c:choose>
  </div>
</form>

<%@ include file="/common/footer.jsp" %>
