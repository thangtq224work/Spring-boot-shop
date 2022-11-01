<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
<link href="${pageContext.request.contextPath }/css/bootstrap5.min.css"
	rel="stylesheet" crossorigin="anonymous">
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/css/bootstrapicons.css">

<style>
table, thead, tr, tbody, th, td {
	text-align: center;
}

.bd-placeholder-img {
	font-size: 1.125rem;
	text-anchor: middle;
	-webkit-user-select: none;
	-moz-user-select: none;
	user-select: none;
}

@media ( min-width : 768px) {
	.bd-placeholder-img-lg {
		font-size: 3.5rem;
	}
}

body {
	font-size: .875rem;
}

.feather {
	width: 16px;
	height: 16px;
	vertical-align: text-bottom;
}

/*
* Sidebar
*/
.sidebar {
	position: fixed;
	top: 0;
	/* rtl:raw:
right: 0;
*/
	bottom: 0;
	/* rtl:remove */
	left: 0;
	z-index: 100;
	/* Behind the navbar */
	padding: 48px 0 0;
	/* Height of navbar */
	box-shadow: inset -1px 0 0 rgba(0, 0, 0, .1);
}

@media ( max-width : 767.98px) {
	.sidebar {
		top: 5rem;
	}
}

.sidebar-sticky {
	position: relative;
	top: 0;
	height: calc(100vh - 48px);
	padding-top: .5rem;
	overflow-x: hidden;
	overflow-y: auto;
	/* Scrollable contents if viewport is shorter than content. */
}

.sidebar .nav-link {
	font-weight: 500;
	color: #333;
}

.sidebar .nav-link .feather {
	margin-right: 4px;
	color: #727272;
}

.sidebar .nav-link.active {
	color: #2470dc;
}

.sidebar .nav-link:hover .feather, .sidebar .nav-link.active .feather {
	color: inherit;
}

.sidebar-heading {
	font-size: .75rem;
	text-transform: uppercase;
}

/*
* Navbar
*/
.navbar-brand {
	padding-top: .75rem;
	padding-bottom: .75rem;
	font-size: 1rem;
	background-color: rgba(0, 0, 0, .25);
	box-shadow: inset -1px 0 0 rgba(0, 0, 0, .25);
}

.navbar .navbar-toggler {
	top: .25rem;
	right: 1rem;
}

.navbar .form-control {
	padding: .75rem 1rem;
	border-width: 0;
	border-radius: 0;
}

.form-control-dark {
	color: #fff;
	background-color: rgba(255, 255, 255, .1);
	border-color: rgba(255, 255, 255, .1);
}

.form-control-dark:focus {
	border-color: transparent;
	box-shadow: 0 0 0 3px rgba(255, 255, 255, .25);
}
</style>


<!-- Custom styles for this template -->
</head>

<body>

	<header
		class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0 shadow">
		<%-- ${sessionScope.user.role.id } --%>
		<p class="navbar-brand col-md-3 col-lg-2 me-0 px-3 h2">${sessionScope.user.username }</p>
		<button class="navbar-toggler position-absolute d-md-none collapsed"
			type="button" data-bs-toggle="collapse" data-bs-target="#sidebarMenu"
			aria-controls="sidebarMenu" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<input class="form-control form-control-dark w-100" type="text"
			placeholder="Search" aria-label="Search">
		<div class="navbar-nav">
			<div class="nav-item text-nowrap">
				<a class="nav-link px-3" href="${pageContext.request.contextPath }/logout">Sign out</a>
			</div>
		</div>
	</header>

	<div class="container-fluid">
		<div class="row">
			<nav id="sidebarMenu"
				class="col-md-3 col-lg-2 d-md-block bg-light sidebar collapse">
				<div class="position-sticky pt-3">
					<ul class="nav flex-column">
						<h6
							class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
							<span>Manager System</span>
						</h6>
						<core:if test="${sessionScope.user.admin ==0 }">
						<li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath }/admin/accounts"> <i
								class="bi bi-people-fill pe-2"></i> Nhân viên
						</a></li>
						</core:if>
						<li class="nav-item"><a class="nav-link"
							href="${pageContext.request.contextPath }/admin/products"> <i
								class="bi bi-archive-fill pe-2"></i> Sản phẩm
						</a></li>
						<li class="nav-item"><a class="nav-link"
							href="${pageContext.request.contextPath }/admin/categories"> <i
								class="bi bi-bookmark-dash-fill pe-2"></i> Danh mục
						</a></li>
						<li class="nav-item"><a class="nav-link"
							href="${pageContext.request.contextPath }/admin/orders"> <i
								class="bi bi-clipboard-fill  pe-2"></i> Hóa đơn
						</a></li>
						<li class="nav-item"> 
							<a class="nav-link" data-bs-toggle="collapse" href="#collapseExample"><i class="bi bi-bar-chart-line-fill pe-2"></i>   Thống kê</a>
							  <div class="collapse" id="collapseExample">
								  <div class="card card-body sub-nav">
									<a class="nav-link" href="${pageContext.request.contextPath }/admin/categories">
									<i class="bi bi-list-check  pe-2"></i> Danh mục
									</a>
									<a class="nav-link" href="${pageContext.request.contextPath }/admin/brands">
									<i class="bi bi-list-check  pe-2"></i> Nhãn hiệu
									</a>
								   		<!-- <a href="#" class="nav-link">Khách</a>
								   		<a href="#" class="nav-link">Hàng</a>  -->
								   </div>
							  </div>
						</li>
					</ul>

					<h6
						class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
						<span>Manager System</span>
						<!-- <a class="link-secondary" href="#" aria-label="Add a new report">
                        <span data-feather="plus-circle"></span>
                    </a> -->
					</h6>
					<ul class="nav flex-column mb-2">
						<li class="nav-item"><a class="nav-link" href="#"> <span
								data-feather="file-text"></span> Current month
						</a></li>
						<li class="nav-item"><a class="nav-link" href="#"> <span
								data-feather="file-text"></span> Last quarter
						</a></li>
						<li class="nav-item"><a class="nav-link" href="#"> <span
								data-feather="file-text"></span> Social engagement
						</a></li>
						<li class="nav-item"><a class="nav-link" href="#"> <span
								data-feather="file-text"></span> Year-end sale
						</a></li>
					</ul>
				</div>
			</nav>

			<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
				<div
					class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
					<h1 class="h2">Dashboard</h1>
					<div class="btn-toolbar mb-2 mb-md-0">
						<div class="btn-group me-2">
							<button type="button" class="btn btn-sm btn-outline-secondary">Share</button>
							<button type="button" class="btn btn-sm btn-outline-secondary">Export</button>
						</div>
						<button type="button"
							class="btn btn-sm btn-outline-secondary dropdown-toggle">
							<span data-feather="calendar"></span> This week
						</button>
					</div>
				</div>

				<!-- <canvas class="my-4 w-100" id="myChart" width="900" height="380"></canvas> -->

				<core:if test="${ not empty view }">
					<jsp:include page="${view }"></jsp:include>
				</core:if>

			</main>
		</div>
	</div>


	<script
		src="${pageContext.request.contextPath }/js/bootstrap5.bundle.min.js"></script>
</body>

</html>