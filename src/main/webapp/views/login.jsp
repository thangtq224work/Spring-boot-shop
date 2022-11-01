<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/bootstrap5.min.css">
<script src="${pageContext.request.contextPath }/js/bootstrap5.bundle.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/bootstrapicons.css">
<style>
    body {
        background-color: rgba(6, 115, 151, 0.7);
    }
</style>
</head>

<body>
    <div class="container">
    	<%-- <core:if test="${ !empty message  }">
    		<p class="alert alert-danger mx-auto" style="width: 25rem;">${message }</p>
    	</core:if> --%>
        <div class="card border border-3 shadow-lg mx-auto mt-5" style="width: 25rem;">
        <core:if test="${ !empty message  }">
    		<p class="alert alert-danger">${message }</p>
    	</core:if>
            <div class="p-2 d-flex justify-content-center">

                <img src="https://vi.seaicons.com/wp-content/uploads/2016/08/Users-User-Male-2-icon-1.png"
                    class="card-img-top w-25 opacity-25 bg-info rounded-circle" alt="user">

            </div>
            <div class="card-body pt-2">
                <form action="${pageContext.request.contextPath }/login" method="post">
                <h5 class="card-title text-center">Login into Shop</h5>
                <div class="row mx-0 mb-3">
                    <div class="mb-3">
                        <label for="exampleFormControlInput1" class="form-label">Tài khoản</label>
                        <div class="input-group mb-3">
                            <i class="bi bi-person-check-fill input-group-text"></i>
                            <input type="text" class="form-control shadow-none" placeholder="Username" name="username" value="${username }">
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="exampleFormControlInput1" class="form-label">Mật khẩu</label>

                        <div class="input-group mb-3">
                            <i class="bi bi-lock-fill input-group-text"></i>
                            <input type="password" class="form-control shadow-none" placeholder="Password"
                                name="password">
                        </div>
                    </div>

                    <!-- <div class="form-floating mb-3 px-1">
                        <input type="email" class="form-control px-2" id="floatingInput" placeholder="name@example.com">
                        <label for="floatingInput">Email address</label>
                    </div>
                    <div class="form-floating px-1">
                        <input type="password" class="form-control px-2" id="floatingPassword" placeholder="Password">
                        <label for="floatingPassword">Password</label>
                    </div> -->

                    <button type="submit" class="btn btn-primary px-1">Đăng nhập</button>
                    <a href="#" class="pt-3 text-decoration-none text-center">Tạo mới tài khoản</a>
                </div>
                </form>
            </div>
        </div>

    </div>
</body>
</html>