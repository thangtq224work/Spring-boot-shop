<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<core:if test="${not empty sessionScope.cartSuccess }">
	<p class="alert alert-success">${sessionScope.cartSuccess }</p>
	<%
	request.getSession().removeAttribute("cartSuccess");
	%>
	</core:if>
	<aside class="col-md-3 px-0 mt-2">
		<div class="card px-0">
			<h3 class="card-header">
				Danh mục
			</h3>
			<ul class="list-group list-unstyled list-group-flush">
				<core:forEach items="${categories }" var="item">

					<li><a href="${pageContext.request.contextPath }/home/${item.id}"
							class="list-group-item list-group-item-action">
							${item.name}
						</a></li>
				</core:forEach>

			</ul>
		</div>
	</aside>
	<article class="col-md-9 px-0 mt-2">
		<div class="row">
			<core:forEach items="${products }" var="item">

				<div class="col-md-3 mb-2">
					<div class="card h-100">
						<img src="${pageContext.request.contextPath }/image/${item.image }" class="card-img-top"
							alt="...">
						<div class="card-body">
							<h5 class="card-title">${item.name }</h5>
							<p class="card-text">${item.price }</p>

						</div>
						<div class="card-footer">
							<a href="${pageContext.request.contextPath }/add-to-cart/${item.id }"
								class="btn btn-primary">Thêm vào giỏ hàng</a>
						</div>
					</div>
				</div>
			</core:forEach>
		</div>
		<nav class="col" aria-label="Page navigation example">
		<ul class="pagination justify-content-end pe-3">
			<core:if test="${page.number > 2 }">
				<li class="page-item"><a class="page-link"
					href="${pageContext.request.contextPath }/home">First</a></li>
				<li class="page-item"><a class="page-link" href="">...</a></li>
			</core:if>


			<core:choose>
				<core:when test="${page.number>1 && page.number < page.totalPages}">
					<li class="page-item"><a class="page-link"
						href="${pageContext.request.contextPath }/home?page=${page.number-1 }">${page.number-1 }</a></li>
					<li class="page-item"><a class="page-link"
						href="${pageContext.request.contextPath }/home?page=${page.number }">${page.number }</a></li>
				</core:when>
				<core:when test="${page.number>0 && page.number < page.totalPages}">
					<li class="page-item"><a class="page-link"
						href="${pageContext.request.contextPath }/home?page=${page.number }">${page.number }</a></li>
				</core:when>
				<core:when test="${page.number >= page.totalPages }">
					<li class="page-item"><a class="page-link"
						href="${pageContext.request.contextPath }/home?page=${page.totalPages-1}">${page.totalPages-1}</a></li>
				</core:when>
			</core:choose>

			<core:choose>
				<core:when test="${page.number +1 > page.totalPages }">
					<!-- xu li khi tran trang -->
					<%-- <li class="page-item"><a class="page-link"
							href="${pageContext.request.contextPath }/home?page=${page.totalPages }">${page.totalPages } Â</a></li>
						<li class="page-item active"><a class="page-link "
							href="${pageContext.request.contextPath  }/home?page=${page.number}">${page.number} B</a></li> --%>

					<li class="page-item"><a class="page-link"
						href="${pageContext.request.contextPath }/home?page=${page.totalPages}">Last</a></li>
				</core:when>
				<core:otherwise>

					<li class="page-item active"><a class="page-link "
						href="${pageContext.request.contextPath  }/home?page=${page.number+1}">${page.number+1 }
							</a></li>

					<core:if test="${page.number <= page.totalPages-3  }">
						<li class="page-item"><a class="page-link"
							href="${pageContext.request.contextPath }/home?page=${page.number+2 }">${page.number +2 }</a></li>
					
						<li class="page-item"><a class="page-link"
							href="${pageContext.request.contextPath }/home?page=${page.number+3 }">${page.number +3 }</a></li>
					</core:if>
					<core:if test="${page.totalPages-page.number == 2    }">
						<li class="page-item"><a class="page-link"
							href="${pageContext.request.contextPath }/home?page=${page.number+2 }">${page.number +2 }</a></li>
					</core:if>
					
				</core:otherwise>
			</core:choose>
			<core:if test="${page.number+3 < page.totalPages }">
				<li class="page-item"><a class="page-link"
					href="">...</a></li>
				<li class="page-item"><a class="page-link"
					href="${pageContext.request.contextPath }/home?page=${page.totalPages}">Last</a></li>
			</core:if>
		</ul>
	</nav>
	</article>