<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<core:if test="${not empty sessionScope.alert }">
	<script>
		alert("${sessionScope.alert}");
	<%request.getSession().removeAttribute("alert");%>
		
	</script>
</core:if>
<core:if test="${not empty sessionScope.alertSuccess }">
	<p class="alert alert-success">${sessionScope.alertSuccess }</p>
	<%
	request.getSession().removeAttribute("alertSuccess");
	%>
</core:if>
<core:if test="${not empty sessionScope.alertError }">
	<p class="alert alert-danger">${sessionScope.alertError }</p>
	<%
	request.getSession().removeAttribute("alertError");
	%>
</core:if>
<div>
	<form action="${pageContext.request.contextPath }/admin/accounts" method="get">
		<div class="row">
		<div class="col-0 col-md-6"></div>
		<div class="col-12 col-md-6 row">
			<div class="col col-md-6">
				<select class="form-select" name="filter">
					<option value="-1">Tất cả</option>
					<core:forEach items="${items }" var="item">
						<option value="${item.id }"  ${check==item.id?'selected':''}>${item.name }</option>
					</core:forEach>
				</select>
			</div>
			<div class="col col-md-6">
				<input type="submit" value="Xác nhận" class="btn btn-success me-2">	
			</div>
		</div>
		</div>
	</form>
</div>
<div class="table-responsive">
	<table class="table table-striped table-sm  align-middle">
		<thead>
			<tr>
				<th scope="col">#</th>
				<th scope="col">Họ Tên</th>
				<th scope="col">Email</th>
				<th scope="col">Vai trò</th>
				<th scope="col">Trạng thái</th>
				<th scope="col">Hình ảnh</th>
				<th scope="col">Thao tác</th>
			</tr>
		</thead>
		<tbody>

			<core:if test="${list.size() > 0 }">
				<core:forEach items="${list }" var="item">

					<tr>
						<td>${item.id }</td>
						<td>${item.fullname }</td>
						<td>${item.email }</td>
						<td>${item.admin==0?'Admin':(item.admin==1?'Nhân viên':'Khách hàng') }</td>
						<td>${item.activated==1?'Còn truy cập':'Ngưng truy cập' }</td>
						<td><core:if test="${not empty item.photo}">
								<img
									src="${pageContext.request.contextPath }/image/${item.photo }"
									width="150px" height="100px" />
							</core:if></td>
						<td><a
							href="${pageContext.request.contextPath }/admin/account/edit/${item.id}"
							class="btn btn-danger">Cập nhật</a>
							<a
							href="${pageContext.request.contextPath }/admin/account/delete/${item.id}"
							class="btn btn-danger">Xóa</a></td>
					</tr>
				</core:forEach>
			</core:if>
		</tbody>
	</table>
	<core:if test="${list.size() == 0 }">
		<p class="h2">No data to display</p>
	</core:if>
</div>
<div class="row">
	<div class="col">
		<a href="${pageContext.request.contextPath }/admin/account/new"
			class="btn btn-success">Thêm mới</a>
	</div>
	<%-- <nav class="col" aria-label="Page navigation example">
		<ul class="pagination justify-content-end pe-3">
			<core:if test="${page.number > 2 }">
				<li class="page-item"><a class="page-link"
					href="${pageContext.request.contextPath }/admin/products">First</a></li>
				<li class="page-item"><a class="page-link" href="">...</a></li>
			</core:if>


			<core:choose>
				<core:when test="${page.number>1 && page.number < page.totalPages}">
					<li class="page-item"><a class="page-link"
						href="${pageContext.request.contextPath }/admin/products?page=${page.number-1 }">${page.number-1 }</a></li>
					<li class="page-item"><a class="page-link"
						href="${pageContext.request.contextPath }/admin/products?page=${page.number }">${page.number }</a></li>
				</core:when>
				<core:when test="${page.number>0 && page.number < page.totalPages}">
					<li class="page-item"><a class="page-link"
						href="${pageContext.request.contextPath }/admin/products?page=${page.number }">${page.number }</a></li>
				</core:when>
				<core:when test="${page.number >= page.totalPages }">
					<li class="page-item"><a class="page-link"
						href="${pageContext.request.contextPath }/admin/products?page=${page.totalPages-1}">${page.totalPages-1}</a></li>
				</core:when>
			</core:choose>

			<core:choose>
				<core:when test="${page.number +1 > page.totalPages }">
					<!-- xu li khi tran trang -->
					<li class="page-item"><a class="page-link"
							href="${pageContext.request.contextPath }/admin/products?page=${page.totalPages }">${page.totalPages } Â</a></li>
						<li class="page-item active"><a class="page-link "
							href="${pageContext.request.contextPath  }/admin/products?page=${page.number}">${page.number} B</a></li>

					<li class="page-item"><a class="page-link"
						href="${pageContext.request.contextPath }/admin/products?page=${page.totalPages}">Last</a></li>
				</core:when>
				<core:otherwise>

					<li class="page-item active"><a class="page-link "
						href="${pageContext.request.contextPath  }/admin/products?page=${page.number+1}">${page.number+1 }
							</a></li>

					<core:if test="${page.number <= page.totalPages-3  }">
						<li class="page-item"><a class="page-link"
							href="${pageContext.request.contextPath }/admin/products?page=${page.number+2 }">${page.number +2 }</a></li>
					
						<li class="page-item"><a class="page-link"
							href="${pageContext.request.contextPath }/admin/products?page=${page.number+3 }">${page.number +3 }</a></li>
					</core:if>
					<core:if test="${page.totalPages-page.number == 2    }">
						<li class="page-item"><a class="page-link"
							href="${pageContext.request.contextPath }/admin/products?page=${page.number+2 }">${page.number +2 }</a></li>
					</core:if>
					
				</core:otherwise>
			</core:choose>
			<core:if test="${page.number+3 < page.totalPages }">
				<li class="page-item"><a class="page-link"
					href="">...</a></li>
				<li class="page-item"><a class="page-link"
					href="${pageContext.request.contextPath }/admin/products?page=${page.totalPages}">Last</a></li>
			</core:if>
		</ul>
	</nav> --%>
</div>
