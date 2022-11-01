<%@page import="com.application.model.OrderStatus"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
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
<div class="table-responsive">
	<table class="table table-striped table-sm  align-middle">
		<thead>
			<tr>
				<th scope="col">#</th>
				<th scope="col">Tên khách hàng</th>
				<th scope="col">Địa chỉ</th>
				<th scope="col">Số điện thoại</th>
				<th scope="col">Ngày tạo</th>
				<th scope="col">Trạng thái</th>
				<th scope="col">Thao tác</th>
				<th scope="col">Thông tin</th>
			</tr>
		</thead>
		<tbody>

			<core:if test="${list.size() > 0 }">
				<core:forEach items="${list }" var="item">

					<tr>
						<td>${item.id }</td>
						<td>${item.account.username }</td>
						<td>${item.address }</td>
						<td>${item.numberPhone }</td>
						<td>${item.createDate }</td>
						<td>${item.status==0?'Đã hủy':(item.status==1?'Thành công':(item.status==2?'Đang giao hàng':'Chờ xác nhận')) }</td>
						<td>
							<core:if test="${item.status==OrderStatus.WAITINGCONFIRM }">
								<a href="${pageContext.request.contextPath }/admin/order/confirm/${item.id}"
								class="btn btn-danger">Xác nhận</a>
								<a
								href="${pageContext.request.contextPath }/admin/order/cancel/${item.id}"
								class="btn btn-danger">Hủy đơn</a>
							</core:if>
							<core:if test="${item.status==OrderStatus.WAITING }">
								<a href="${pageContext.request.contextPath }/admin/order/success/${item.id}"
								class="btn btn-danger">Thành công</a>
								<a
								href="${pageContext.request.contextPath }/admin/order/cancel/${item.id}"
								class="btn btn-danger">Hủy đơn</a>
							</core:if>
						</td>
						
						
						
						
						
						<td><a
							href="${pageContext.request.contextPath }/admin/order/edit/${item.id}"
							class="btn btn-danger">Cập nhật</a>
							<a
							href="${pageContext.request.contextPath }/admin/order/infor/${item.id}"
							class="btn btn-danger">Chi tiết</a></td>
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
		<a href="${pageContext.request.contextPath }/admin/order/new"
			class="btn btn-success">Thêm mới</a>
	</div>
	<nav class="col" aria-label="Page navigation example">
		<ul class="pagination justify-content-end pe-3">
			<core:if test="${page.number > 2 }">
				<li class="page-item"><a class="page-link"
					href="${pageContext.request.contextPath }/admin/orders">First</a></li>
				<li class="page-item"><a class="page-link" href="">...</a></li>
			</core:if>


			<core:choose>
				<core:when test="${page.number>1 && page.number < page.totalPages}">
					<li class="page-item"><a class="page-link"
						href="${pageContext.request.contextPath }/admin/orders?page=${page.number-1 }">${page.number-1 }</a></li>
					<li class="page-item"><a class="page-link"
						href="${pageContext.request.contextPath }/admin/orders?page=${page.number }">${page.number }</a></li>
				</core:when>
				<core:when test="${page.number>0 && page.number < page.totalPages}">
					<li class="page-item"><a class="page-link"
						href="${pageContext.request.contextPath }/admin/orders?page=${page.number }">${page.number }</a></li>
				</core:when>
				<core:when test="${page.number >= page.totalPages }">
					<li class="page-item"><a class="page-link"
						href="${pageContext.request.contextPath }/admin/orders?page=${page.totalPages-1}">${page.totalPages-1}</a></li>
				</core:when>
			</core:choose>

			<core:choose>
				<core:when test="${page.number +1 > page.totalPages }">
					<!-- xu li khi tran trang -->
					<%-- <li class="page-item"><a class="page-link"
							href="${pageContext.request.contextPath }/admin/orders?page=${page.totalPages }">${page.totalPages } Â</a></li>
						<li class="page-item active"><a class="page-link "
							href="${pageContext.request.contextPath  }/admin/orders?page=${page.number}">${page.number} B</a></li> --%>

					<li class="page-item"><a class="page-link"
						href="${pageContext.request.contextPath }/admin/orders?page=${page.totalPages}">Last</a></li>
				</core:when>
				<core:otherwise>

					<li class="page-item active"><a class="page-link "
						href="${pageContext.request.contextPath  }/admin/orders?page=${page.number+1}">${page.number+1 }
							</a></li>

					<core:if test="${page.number <= page.totalPages-3  }">
						<li class="page-item"><a class="page-link"
							href="${pageContext.request.contextPath }/admin/orders?page=${page.number+2 }">${page.number +2 }</a></li>
					
						<li class="page-item"><a class="page-link"
							href="${pageContext.request.contextPath }/admin/orders?page=${page.number+3 }">${page.number +3 }</a></li>
					</core:if>
					<core:if test="${page.totalPages-page.number == 2    }">
						<li class="page-item"><a class="page-link"
							href="${pageContext.request.contextPath }/admin/orders?page=${page.number+2 }">${page.number +2 }</a></li>
					</core:if>
					
				</core:otherwise>
			</core:choose>
			<core:if test="${page.number+3 < page.totalPages }">
				<li class="page-item"><a class="page-link"
					href="">...</a></li>
				<li class="page-item"><a class="page-link"
					href="${pageContext.request.contextPath }/admin/orders?page=${page.totalPages}">Last</a></li>
			</core:if>
		</ul>
	</nav>
</div>
