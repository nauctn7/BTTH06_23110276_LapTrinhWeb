<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<c:set var="pageTitle" value="${empty category.categoryId ? 'Thêm Category' : 'Sửa Category'}"/>
<%@ include file="/common/header.jsp" %>
<%@ include file="/common/messages.jsp" %>

<div class="card">
  <div class="card-body">
    <div class="d-flex justify-content-between align-items-center mb-3">
      <h4 class="m-0"><i class="bi bi-tag me-2"></i><c:out value="${pageTitle}"/></h4>
      <a class="btn btn-light border" href="<c:url value='/admin/categories'/>"><i class="bi bi-arrow-left"></i> Quay lại</a>
    </div>

    <form action="<c:url value='/admin/categories/saveOrUpdate'/>" method="post"
          enctype="multipart/form-data" class="row g-4">

      <input type="hidden" name="categoryId" value="${category.categoryId}"/>
      <input type="hidden" name="images"     value="${category.images}"/>
      <input type="hidden" name="isEdit"     value="${category.isEdit}"/>

      <div class="col-md-6">
        <label class="form-label">Category Name <span class="text-danger">*</span></label>
        <input type="text" class="form-control" name="categoryname"
               value="${category.categoryname}" required placeholder="VD: Phim hài">
      </div>

      <div class="col-md-6">
        <label class="form-label">Category Code</label>
        <input type="text" class="form-control" name="categorycode"
               value="${category.categorycode}" placeholder="VD: PH">
      </div>

      <div class="col-md-6">
        <label class="form-label">Ảnh</label>
        <input class="form-control" type="file" id="imageFile" name="imageFile" accept="image/*">
        <div class="form-text">Hỗ trợ JPG/PNG, &lt; 5MB.</div>
      </div>

      <div class="col-md-6">
        <label class="form-label d-block">Xem trước</label>
        <img id="preview" src="<c:if test='${not empty category.images}'><c:url value='/images/${category.images}'/></c:if>"
             style="height:100px;border-radius:10px; background:#f1f5f9;" />
      </div>

      <div class="col-12">
        <div class="form-check">
          <input class="form-check-input" type="checkbox" id="status" name="status"
                 ${category.status ? 'checked' : ''}>
          <label class="form-check-label" for="status">Kích hoạt</label>
        </div>
      </div>

      <div class="col-12 d-flex justify-content-end gap-2">
           <a href="<c:url value='/admin/categories'/>" class="btn btn-secondary">&laquo; Quay lại</a>
        <button class="btn btn-primary"><i class="bi bi-save"></i>
          <c:choose><c:when test="${category.isEdit}"> Update</c:when><c:otherwise> Save</c:otherwise></c:choose>
        </button>
      </div>
    </form>
  </div>
</div>

<script>
  const file = document.getElementById('imageFile');
  const preview = document.getElementById('preview');
  if (file) {
    file.addEventListener('change', e => {
      const f = e.target.files?.[0];
      if (!f) return;
      if (!f.type.startsWith('image/')) { alert('Vui lòng chọn file ảnh'); e.target.value=''; return; }
      const url = URL.createObjectURL(f);
      preview.src = url;
    });
  }
</script>

<%@ include file="/common/footer.jsp" %>
