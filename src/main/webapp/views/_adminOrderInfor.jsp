<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>

<core:if test="${not empty sessionScope.alertSuccess }">
	<p class="alert alert-success">${sessionScope.alertSuccess }</p>
	<%
	request.getSession().removeAttribute("alertSuccess");
	%>
</core:if>
<core:if test="${not empty sessionScope.alertError }">
	<p class="alert alert-danger">${sessionScope.alertError }</p>
	<%
	request.getSession().removeAttribute("alertError");
	%>
</core:if>
		<div>
			<h2 class="text-center">Đơn hàng</h2>
			<core:if test="${entity.orderDetailEntities.size() > 0 }">
                <div class="table-responsive">
                    <table class="table table-striped table-sm  align-middle">
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Tên sản phẩm</th>
                                <th scope="col">Giá bán</th>
                                <th scope="col">Số lượng</th>
                                <th scope="col">Tổng giá</th>
                                <th scope="col">Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <core:forEach items="${entity.orderDetailEntities }" var="item">
                                <tr class="cart-number" onchange="tinhTien(this,'${pageContext.request.contextPath }')">
                                    <td scope="row">${item.product.id }</td>
                                    <td>${item.product.name }</td>
                                    <td id="price">${item.product.price }</td>
                                    <td>
                                        <input name="quantity" value="${item.quantity }" id="quantity" type="number" min="1">
                                    </td>
                                    <td id="totalPrice">${item.price}</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath }/admin/remove-from-order/${entity.id }/${item.id}"
                                            class="btn btn-primary">Xóa khỏi giỏ hàng</a>
                                    </td>
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
                <a href="${pageContext.request.contextPath }/admin/clear-order/${entity.id }" class="btn btn-success mb-3">Xóa tất cả sản
                    phẩm khỏi đơn hàng</a>
            </core:if>
            <core:if test="${entity.orderDetailEntities.size() == 0 }">
            	<p class="text-center mb-5">Đơn hàng rỗng</p>
            </core:if>
		</div>
		<div class="row">
			<h2 class="text-center">Sản phẩm</h2>
			<core:forEach items="${products }" var="item">

				<div class="col-md-3 mb-2">
					<div class="card h-100">
						<img src="${pageContext.request.contextPath }/images/${item.image }" class="card-img-top"
							alt="...">
						<div class="card-body">
							<h5 class="card-title">${item.name }</h5>
							<p class="card-text">${item.price }</p>

						</div>
						<div class="card-footer">
							<a href="${pageContext.request.contextPath }/admin/add-to-order/${entity.id }/${item.id }"
								class="btn btn-primary">Thêm vào hóa đơn</a>
						</div>
					</div>
				</div>
			</core:forEach>
		</div>
		<script>
            function tinhTien(element, contextPath) {
                let id = element.getElementsByTagName("td")[0];
                let number = element.getElementsByTagName("input")[0];
                if (!isNaN(Number(id.textContent)) && !isNaN(Number(number.value)) &&
                     id.textContent.trim().length > 0 && number.value.trim().length > 0) {
                    console.log("AA")
                    let xhttp = new XMLHttpRequest();
                    /* console.log(xhttp) */
                    let url = contextPath + "/cart/" + id.textContent + "/" + number.value;
                    console.log("1");
                    xhttp.onload = function () { // has run when xhttp.send() completely run
                        console.log("3");
                        document.getElementById("total").innerHTML = this.responseText;
                    }
                    console.log("2");
                    xhttp.open("GET", url);
                    xhttp.send();
                    console.log("4");
                    element.getElementsByTagName("td")[4].textContent = Number(number.value)*Number(element.getElementsByTagName("td")[2].textContent);
                }
            }
        </script>