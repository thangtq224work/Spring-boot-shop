<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
	<div class="row">
	<p class="text-center fs-3 text-danger fw-bold">${message }</p>
	<form:form modelAttribute="product" action="${pageContext.request.contextPath }/admin/product/${uri }" method="post" enctype="multipart/form-data">
		<form:input type="hidden" path="id" />
		<div class="mb-3">
			<label for="exampleFormControlTextarea1" class="form-label">Tên sản phẩm</label>
			<form:input path="name" cssClass="form-control" />
			<form:errors path="name" element="p" cssClass="text-danger"></form:errors>
		</div>
		<div class="mb-3">
			<label for="exampleFormControlTextarea1" class="form-label">Giá sản phẩm</label>
			<form:input path="price" cssClass="form-control" />
			<form:errors path="price" element="p" cssClass="text-danger"></form:errors>
		</div>
		<div class="mb-3">
			<label for="exampleFormControlTextarea1" class="form-label">Số lượng</label>
			<form:input path="quantity" cssClass="form-control" />
			<form:errors path="quantity" element="p" cssClass="text-danger"></form:errors>
		</div>
		<div class="mb-3">
			<label for="exampleFormControlTextarea1" class="form-label">Danh mục</label>
			<form:select cssClass="form-select" path="idCategory">
				<form:options items="${categories }" itemValue="id" itemLabel="name"/>
			</form:select>
			<form:errors path="idCategory" element="p" cssClass="text-danger"></form:errors>
		</div>
		<div class="mb-3">
			<label for="exampleFormControlTextarea1" class="form-label">Trạng thái</label>
			<form:radiobutton path="available" value="true" id="true" cssClass="form-check-input"/>
			<label class="form-check-label" for="true">Còn bán</label>
			<form:radiobutton path="available" value="false" id="false" cssClass="form-check-input"/>
			<label class="form-check-label" for="false">Ngừng bán</label>
			<form:errors path="available" element="p" cssClass="text-danger"></form:errors>
		</div>
		<div class="mb-3">
			<label for="exampleFormControlTextarea1" class="form-label">Hình ảnh</label>
			<core:if test="${not empty product.image}">
				<br />
				<form:input path="image" type="hidden"/>
				<img src="${pageContext.request.contextPath }/image/${product.image }" id="imagePreview" class=" mb-2" alt="image" width="250px" height="200px" />
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
