<%@ include file="/common/taglibs.jsp" %>
<c:if test="${not empty message}">
  <div class="alert alert-success alert-dismissible fade show" role="alert">
    <i class="bi bi-check-circle-fill me-1"></i><c:out value="${message}"/>
    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
  </div>
</c:if>

<c:if test="${not empty error}">
  <div class="alert alert-danger alert-dismissible fade show" role="alert">
    <i class="bi bi-exclamation-triangle-fill me-1"></i><c:out value="${error}"/>
    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
  </div>
</c:if>
