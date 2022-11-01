<%@ page language="java" contentType="text/html; charset=uft-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<core:if test="${not empty sessionScope.alert }">
	<script>
		alert("${sessionScope.alert}");
		<% request.getSession().removeAttribute("alert");  %>
	</script>
</core:if>
<core:if test="${not empty sessionScope.alertSuccess }">
	<%-- <script>
		alert("${sessionScope.alertSuccess}");
	</script> --%>
	<p class="alert alert-success">${sessionScope.alertSuccess }</p>
	<%
	request.getSession().removeAttribute("alertSuccess");
	%>
</core:if>
<%-- <core:if test="${alertSuccess != null }">
<p class="alert alert-success">${alertSuccess }</p>
</core:if> --%>
<div class="table-responsive">
	<table class="table table-striped table-sm">
		<thead>
			<tr>
				<th scope="col">#</th>
				<th scope="col">Tên danh mục</th>
				<th scope="col">Thao tác</th>
			</tr>
		</thead>
		<tbody>

			<core:if test="${list.size() > 0 }">
				<core:forEach items="${list }" var="item">

					<tr>
						<td>${item.id }</td>
						<td>${item.name }</td>
						<td><a
							href="${pageContext.request.contextPath }/admin/category/edit/${item.id}"
							class="btn btn-danger">Cập nhật</a></td>
					</tr>
				</core:forEach>
			</core:if>

		</tbody>
	</table>
	<core:if test="${list.size() == 0 }">
		<p class="h3">No data to display</p>
	</core:if>
</div>
<div class="row">
	<div class="col">
		<a href="${pageContext.request.contextPath }/admin/category/new" class="btn btn-success">Thêm mới</a>
	</div>
	<div class="col"></div>
</div>
