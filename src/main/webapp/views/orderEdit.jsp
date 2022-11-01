<%@ page language="java" contentType="text/html; charset=uft-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>


<div class="row"> 
	<p class="text-center fs-3 text-danger fw-bold">${message }</p>
	<form:form modelAttribute="order" action="${pageContext.request.contextPath }/admin/order/${uri }" method="post">
		<form:input type="hidden" path="id" />
		
		<div class="mb-3">
			<label for="exampleFormControlTextarea1" class="form-label">Số điện thoại liên hệ</label>
			<form:input path="numberPhone" cssClass="form-control" />
			<form:errors path="numberPhone" element="p" cssClass="text-danger"></form:errors>
		</div>
		<div class="mb-3">
			<label for="exampleFormControlTextarea1" class="form-label">Địa chỉ</label>
			<form:input path="address" cssClass="form-control" />
			<form:errors path="address" element="p" cssClass="text-danger"></form:errors>
		</div>
		<core:if test="${uri eq 'save' }">
			<div class="mb-3">
			<label for="exampleFormControlTextarea1" class="form-label">Tên đăng nhập chủ đơn(có thể bỏ trống)</label>
			<input name="username" class="form-control" />
			<p class="text-danger">${InvalidAccount }</p>
		</div>
		</core:if>
		<div class="mb-3">
			<label for="exampleFormControlTextarea1" class="form-label">Ghi chú</label>
			<form:textarea path="note" cssClass="form-control" />
			<form:errors path="note" element="p" cssClass="text-danger"></form:errors>
		</div>
		<%-- <div class="mb-3">
			<label for="exampleFormControlTextarea1" class="form-label">Trạng thái</label>
			<form:select path="status" cssClass="form-select">
				<form:options items="${statusOrder }"/>
			</form:select>
			<form:errors path="status" element="p" cssClass="text-danger"></form:errors>
		</div> --%>
		<input type="submit" class="btn btn-success" value="${text }" />
	</form:form>
</div>