<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<div style="min-height: 500px  " class="mt-3">
<h3 class="text-center text-danger ">Chi tiết đơn hàng</h3>
<div class="table-responsive">
                    <table class="table table-striped table-sm  align-middle">
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Tên sản phẩm</th>
                                <th scope="col">Số lượng</th>
                                <th scope="col">Giá tiền</th>
                            </tr>
                        </thead>
                        <tbody>
                            <core:forEach items="${order.orderDetailEntities }" var="item">
                                <tr class="cart-number">
                                    <td>${item.product.id }</td>
									<td>${item.product.name }</td>
									<td>${item.quantity }</td>
									<td>${item.price }</td>
                                </tr>
                            </core:forEach>
                            <tr>
                            	<td colspan="4">Tổng tiền : ${total }</td>
                            </tr>
                        </tbody>
                    </table>
<a href="${pageContext.request.contextPath }/my-order"
							class="btn btn-danger">Quay lại đơn hàng</a></td>
                </div>
</div>