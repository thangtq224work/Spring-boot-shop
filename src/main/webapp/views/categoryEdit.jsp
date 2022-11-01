<%@ page language="java" contentType="text/html; charset=uft-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<div class="row"> 
	<p class="text-center fs-3 text-danger fw-bold">${message }</p>
	<form:form modelAttribute="category" action="${pageContext.request.contextPath }/admin/category/${uri }" method="post">
<%-- 		<form:input path="ID" readonly="readonly" cssClass="form-control" />
		<form:input path="name" readonly="readonly" cssClass="form-control" />
		<input type="submit" class="btn btn-success" value="Cập nhật" /> --%>
		<%-- <div class="mb-3">
			<label for="exampleFormControlInput1" class="form-label">ID</label>
			<form:input path="ID" readonly="readonly" cssClass="form-control" />
		</div> --%>
		<form:input type="hidden" path="id" />
		<div class="mb-3">
			<label for="exampleFormControlTextarea1" class="form-label">Tên danh mục</label>
			<form:input path="name" readonly="readonly" cssClass="form-control" />
			<form:errors path="name" element="p" cssClass="text-danger"></form:errors>
		</div>
		<input type="submit" class="btn btn-success" value="${text }" />
	</form:form>
</div>