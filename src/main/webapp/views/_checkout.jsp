<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<div class="row"> 
	<p class="text-center fs-3 text-danger fw-bold">${message }</p>
	<p class="text-center fs-3 text-danger fw-bold">Danh sách sản phẩm</p>
	<core:if test="${list.size() > 0 }">
                <div class="table-responsive">
                    <table class="table table-striped table-sm  align-middle">
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Tên sản phẩm</th>
                                <th scope="col">Giá bán</th>
                                <th scope="col">Số lượng</th>
                                <th scope="col">Tổng giá</th>
                            </tr>
                        </thead>
                        <tbody>
                            <core:forEach items="${list }" var="item">
                                <tr class="cart-number" onchange="tinhTien(this,'${pageContext.request.contextPath }')">
                                    <td scope="row">${item.id }</td>
                                    <td>${item.name }</td>
                                    <td id="price">${item.price }</td>
                                    <td>${item.quantity }</td>
                                    <td id="totalPrice">${item.price*item.quantity }</td>
                                    
                                </tr>
                            </core:forEach>
                        </tbody>
                        <tfoot>
                            <tr>
                                <td colspan="6" class="h3">Tổng tiền hàng : <div id="total" style="display: inline;">
                                        ${totalPrice }</div>
                                </td>
                            </tr>
                        </tfoot>
                    </table>
                </div>
    </core:if>        
    
	<p class="text-center fs-3 text-danger fw-bold">Thông tin đơn hàng</p>
	<div class="row"> 
	<p class="text-center fs-3 text-danger fw-bold">${message }</p>
	<form:form modelAttribute="order" action="${pageContext.request.contextPath }/checkout" method="post">
		<form:input type="hidden" path="id" />
		
		<div class="mb-3">
			<label for="exampleFormControlTextarea1" class="form-label">Số điện thoại liên hệ</label>
			<form:input path="numberPhone" cssClass="form-control" />
			<form:errors path="numberPhone" element="p" cssClass="text-danger"></form:errors>
		</div>
		<div class="mb-3">
			<label for="exampleFormControlTextarea1" class="form-label">Địa chỉ</label>
			<form:input path="address" cssClass="form-control" />
			<form:errors path="address" element="p" cssClass="text-danger"></form:errors>
		</div>
		<div class="mb-3">
			<label for="exampleFormControlTextarea1" class="form-label">Ghi chú</label>
			<form:textarea path="note" cssClass="form-control" />
			<form:errors path="note" element="p" cssClass="text-danger"></form:errors>
		</div>
		<input type="submit" class="btn btn-success mb-3" value="Đặt hàng" />
	</form:form>
</div>
</div>