<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<core:if test="${not empty sessionScope.alert }">
	<script>
		alert("${sessionScope.alert}");
	<%request.getSession().removeAttribute("alert");%>
		
	</script>
</core:if>
	<div class="row">
	<p class="text-center fs-3 text-danger fw-bold">${message }</p>
	<form:form modelAttribute="account" action="${pageContext.request.contextPath }/admin/account/${uri }" method="post" enctype="multipart/form-data">
		<form:input type="hidden" path="id" />
		<div class="mb-3">
			<label for="exampleFormControlTextarea1" class="form-label">Họ tên</label>
			<form:input path="fullname" cssClass="form-control" />
			<form:errors path="fullname" element="p" cssClass="text-danger"></form:errors>
		</div>
		<div class="mb-3">
			<label for="exampleFormControlTextarea1" class="form-label">Email</label>
			<form:input path="email" cssClass="form-control" />
			<form:errors path="email" element="p" cssClass="text-danger"></form:errors>
		</div>
<%-- 		<div class="mb-3">
			<label for="exampleFormControlTextarea1" class="form-label">Số lượng</label>
			<form:input path="quantity" cssClass="form-control" />
			<form:errors path="quantity" element="p" cssClass="text-danger"></form:errors>
		</div> --%>
		<div class="mb-3">
			<label for="exampleFormControlTextarea1" class="form-label">Vai trò</label>
			<form:select cssClass="form-select" path="admin">
				<form:options items="${role }" itemValue="id" itemLabel="name"/>
			</form:select>
			<form:errors path="admin" element="p" cssClass="text-danger"></form:errors>
		</div>
		<core:if test="${uri eq 'save' }">
			<div class="mb-3">
			<label for="exampleFormControlTextarea1" class="form-label">Tên đăng nhập</label>
			<form:input path="username" cssClass="form-control" />
			<form:errors path="username" element="p" cssClass="text-danger"></form:errors>
			</div>
			<div class="mb-3">
			<label for="exampleFormControlTextarea1" class="form-label">Mật khẩu</label>
			<form:password path="password" cssClass="form-control" />
			<form:errors path="password" element="p" cssClass="text-danger"></form:errors>
			</div>
			<div class="mb-3">
			<label for="exampleFormControlTextarea1" class="form-label">Xác nhận mật khẩu</label>
			<input name="repassword" class="form-control" type="password" />
			<p class="text-danger">${errorPass }</p>
			</div>
		</core:if>
		<div class="mb-3">
			<label for="exampleFormControlTextarea1" class="form-label">Trạng thái</label>
			<form:radiobutton path="activated" value="true" id="true" cssClass="form-check-input"/>
			<label class="form-check-label" for="true">Còn hoạt động</label>
			<form:radiobutton path="activated" value="false" id="false" cssClass="form-check-input"/>
			<label class="form-check-label" for="false">Ngừng hoạt động</label>
			<form:errors path="activated" element="p" cssClass="text-danger"></form:errors>
		</div>
		<div class="mb-3">
			<label for="exampleFormControlTextarea1" class="form-label">Hình ảnh</label>
			<core:if test="${not empty account.photo}">
				<br />
				<form:input path="image" type="hidden"/>
				<img src="${pageContext.request.contextPath }/image/${account.photo }" id="imagePreview" class=" mb-2" alt="image" width="250px" height="200px" />
			</core:if>
			<core:if test="${empty product.image}">
				<br />
				<img src="" style="visibility: hidden;" id="imagePreview" class=" mb-2" alt="image" width="250px" height="200px" />
			</core:if>
			<input type="file" class="form-control" onchange="preview(event)" name="imageupload" />
			<%-- <form:errors path="image" element="p" cssClass="text-danger"></form:errors> --%>
		</div>
		
		<input type="submit" class="btn btn-success" value="${text }" />
	</form:form>
</div>
<script type="text/javascript">
        function preview(event){
        	console.log(event);
            const urlImage = URL.createObjectURL(event.target.files[0]);
            console.log(urlImage);
            document.getElementById("imagePreview").style.visibility="visible";
            document.getElementById("imagePreview").src = urlImage;
            
        }
</script>
