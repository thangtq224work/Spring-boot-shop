<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Register</title>
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
            <div class="card border border-3 shadow-lg mx-auto mt-5" style="width: 50rem;">
            <core:if test="${ !empty message  }">
                <p class="alert alert-danger">${message }</p>
            </core:if>
            <core:if test="${ !empty messageSuccess  }">
                <p class="alert alert-success">${messageSuccess }</p>
            </core:if>
    
                <div class="p-2 d-flex justify-content-center">
    
                    <img src="https://vi.seaicons.com/wp-content/uploads/2016/08/Users-User-Male-2-icon-1.png"
                        class="card-img-top w-25 opacity-25 bg-info rounded-circle" alt="user">
    
                </div>
                <div class="card-body pt-2">
                    <form:form modelAttribute="register" action="${pageContext.request.contextPath }/register" method="post">
                        <h5 class="card-title text-center">Thông tin cá nhân</h5>
                        <form:input path="id" type="hidden"/>
                        <form:input path="admin" type="hidden"/>
                        <form:input path="activated" type="hidden"/>
                        <div class="row mx-0 mb-3">
                            <div class="mb-3">
                                <label for="exampleFormControlInput1" class="form-label">Họ tên</label>
                                <div class="input-group mb-3">
                                    <i class="bi bi-person-check-fill input-group-text"></i>
                                    <form:input path="fullname" cssClass="form-control shadow-none" placeholder="Họ tên đầy đủ"/>
                                </div>
                                    <form:errors path="fullname" cssClass="text-danger" element="p"></form:errors>
                            </div>
                            <div class="mb-3">
                                <label for="exampleFormControlInput1" class="form-label">Email</label>
                                <div class="input-group mb-3">
                                    <i class="bi bi-person-check-fill input-group-text"></i>
                                    <form:input path="email" cssClass="form-control shadow-none" placeholder="Địa chỉ email"/>
                                </div>
                                	<form:errors path="email" cssClass="text-danger" element="p"></form:errors>
                            </div>
                            <div class="mb-3">
                                <label for="exampleFormControlInput1" class="form-label">Tên đăng nhập</label>
                                <div class="input-group mb-3">
                                    <i class="bi bi-person-check-fill input-group-text"></i>
                                    <form:input path="username" cssClass="form-control shadow-none" placeholder="Tên đăng nhập"/>
                                </div>
                                	<form:errors path="username" cssClass="text-danger" element="p"></form:errors>
                            </div>
                            <div class="mb-3">
                                <label for="exampleFormControlInput1" class="form-label">Mật khẩu</label>
                                <div class="input-group mb-3">
                                    <i class="bi bi-lock-fill input-group-text"></i>
                                    <form:password path="password" cssClass="form-control shadow-none" placeholder="Mật khẩu"/>
                                </div>
                                	<form:errors path="password" cssClass="text-danger" element="p"></form:errors>
                            </div>
                            <div class="mb-3">
                                <label for="exampleFormControlInput1" class="form-label">Xác nhận mật khẩu mới</label>
                                <div class="input-group mb-3">
                                    <i class="bi bi-lock-fill input-group-text"></i>
                                    <input type="password" class="form-control shadow-none" placeholder="Xác nhận mật khẩu"
                                        name="rePassword">
                                </div>
                                     <p class="text-danger">${repasswordError }</p>
                            </div>
                            <button type="submit" class="btn btn-primary px-1">Đăng kí</button>
                            <a href="${pageContext.request.contextPath }/home" class="pt-3 text-decoration-none text-center">Về trang chủ</a>
                        </div>
                    </form:form>
                </div>
            </div>
    
        </div>
    </body>
</html>