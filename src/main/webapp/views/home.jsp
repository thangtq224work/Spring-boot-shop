<%@page import="com.application.model.AccountRole"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
	<!DOCTYPE html>
	<html>

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>Insert title here</title>
		<link href="${pageContext.request.contextPath }/css/bootstrap5.min.css" rel="stylesheet"
			crossorigin="anonymous">
		<link rel="stylesheet" href="${pageContext.request.contextPath }/css/bootstrapicons.css">
		<style>
			img.card-img-top {
				width: 100%;
				height: 150px;
			}
		</style>
	</head>

	<body>
		<div class="container">
			<header>
				<div class="row  mx-0">
					<nav class="navbar navbar-expand-lg navbar-light bg-light">
						<div class="container-fluid">
							<a class="navbar-brand" href="${pageContext.request.contextPath }/home">
								<i class="bi bi-bag-fill pe-2"></i>
							</a>
							<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
								data-bs-target="#menu">
								<span class="navbar-toggler-icon"></span>
							</button>
							<div class="collapse navbar-collapse" id="menu">
								<div class="navbar-nav">
									<a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath }/home">Home</a>
									<a class="nav-link" href="${pageContext.request.contextPath }/cart">Giỏ hàng</a>
									<a class="nav-link" href="${pageContext.request.contextPath }/my-order">Đơn hàng</a>
									<core:if test="${sessionScope.user.admin != AccountRole.CUSTOMER.id}">
									<a class="nav-link" href="${pageContext.request.contextPath }/admin/categories">Quản lý</a>
									</core:if>
								</div>
							</div>
							<div class="navbar-nav ms-auto pe-2">
			                    <div class="nav-item dropdown">
			                        <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown"
			                            id="dropdownMenu">Tài khoản</a>
			                        <div class="dropdown-menu dropdown-menu-end">
				                        	<core:choose>			                        		
				                            <core:when test="${sessionScope.user == null}">			                            
					                            <div>
					                                <a class="dropdown-item" href="${pageContext.request.contextPath }/register">Đăng ký</a>
					                                <a class="dropdown-item" href="${pageContext.request.contextPath }/login">Đăng nhập</a>
					                                <a class="dropdown-item" href="${pageContext.request.contextPath }/forgot-password">Quên mật khẩu</a>
					                            </div>
				                            </core:when>
				                            <core:otherwise>
					                            <div class="nav-item">
					                                <i class="bi bi-person-circle ps-3"></i>
					                                <span>${sessionScope.user.username}</span>
					                                <hr class="dropdown-divider">
					                                <a class="dropdown-item" href="${pageContext.request.contextPath }/logout">Đăng xuất</a>
					                                <a class="dropdown-item" href="${pageContext.request.contextPath }/change-password">Đổi mật khẩu</a>
					                                <a class="dropdown-item" href="${pageContext.request.contextPath }/update-infor">Cập nhật tài khoản</a>
					                            </div>
				                            </core:otherwise>
			                        	</core:choose>
			                        </div>
			                    </div>
			                </div>
						</div>
					</nav>
				</div>
			</header>
			<main class="row mx-0">
				<jsp:include page="${views }"></jsp:include>
			</main>
			<footer class="row  bg-dark">
				<div class="col-md-6">
					<h4 class="text-white mt-4 ms-4">
						Thông tin
					</h4>
					<ul class="text-white list-unstyled ms-4 mt-4">
						<li>
							<i class="bi bi-telephone-fill"></i>
							Hotline : 0123456789
						</li>
						<li>
							<i class="bi bi-geo-alt-fill"></i>
							Địa chỉ : Số 1, Đường Cầu Giấy, Hà Nội.
						</li>
						<li>
							<i class="bi bi-alarm-fill"></i>
							Giờ làm việc : 7h - 18h, T2-T7
						</li>
						<li>
							<i class="bi bi-envelope-fill"></i>
							Email : quizzzz@ez.com
						</li>
					</ul>
				</div>
				<div class="col-md-6">
					<h4 class="text-white mt-4 ms-4">
						Mạng xã hội
					</h4>
					<ul class="text-white list-unstyled ms-4 mt-4">
						<li>
							<i class="bi bi-facebook"></i>
							<a href="https://www.facebook.com/" class="text-decoration-none text-white">Facebook</a>

						</li>
						<li>
							<i class="bi bi-twitter"></i>
							<a href="https://twitter.com/?lang=vi" class="text-decoration-none text-white">Twitter</a>

						</li>
						<li>
							<i class="bi bi-github"></i>
							<a href="https://github.com/" class="text-decoration-none text-white">Github</a>
						</li>
						<li>

						</li>
					</ul>
				</div>
			</footer>
		</div>
		<script
		src="${pageContext.request.contextPath }/js/bootstrap5.bundle.min.js"></script>
	</body>

	</html>