<%@page import="com.application.model.OrderStatus"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<core:if test="${not empty sessionScope.cartSuccess }">
	<p class="alert alert-success">${sessionScope.cartSuccess }</p>
	<%
	request.getSession().removeAttribute("cartSuccess");
	%>
	</core:if>
<div style="min-height: 500px  " class="mt-3">
<h3 class="text-center text-danger ">Lịch sử đơn hàng</h3>
<div class="table-responsive">
                    <table class="table table-striped table-sm  align-middle">
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Địa chỉ</th>
                                <th scope="col">Số điện thoại</th>
                                <th scope="col">Ngày đặt hàng</th>
                                <th scope="col">Trạng thái</th>
                                <th scope="col">Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <core:forEach items="${list }" var="item">
                                <tr class="cart-number">
                                    <td scope="row">${item.id }</td>
                                    <td>${item.address }</td>
                                    <td id="price">${item.numberPhone }</td>
                                    <td>${item.createDate }</td>
                                    <td>${item.status==0?'Đã hủy':(item.status==1?'Thành công':(item.status==2?'Đang giao hàng':'Chờ xác nhận')) }</td>
									<td>
										<core:if test="${item.status != OrderStatus.CANCEL && item.status != OrderStatus.SUCCESS }">
										 <a href="${pageContext.request.contextPath }/order/cancel/${item.id}" class="btn btn-success mb-3">Hủy đơn hàng</a>										
										</core:if>
										<a href="${pageContext.request.contextPath }/order/infor/${item.id}" class="btn btn-success mb-3">Chi tiết đơn hàng</a>
									
									</td>
                                    
                                </tr>
                            </core:forEach>
                        </tbody>
                    </table>
                </div>
                </div>