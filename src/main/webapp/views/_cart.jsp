<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>

        <h2 class="text-center">Giỏ hàng</h2>
        <div style="min-height: 500px">
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
                                <th scope="col">Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <core:forEach items="${list }" var="item">
                                <tr class="cart-number" onchange="tinhTien(this,'${pageContext.request.contextPath }')">
                                    <td scope="row">${item.id }</td>
                                    <td>${item.name }</td>
                                    <td id="price">${item.price }</td>
                                    <td>
                                        <input name="quantity" value="${item.quantity }" id="quantity" type="number" min="1">
                                    </td>
                                    <td id="totalPrice">${item.price*item.quantity }</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath }/remove/${item.id}"
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
                <a href="${pageContext.request.contextPath }/clear-cart" class="btn btn-success mb-3">Xóa tất cả sản
                    phẩm khỏi giỏ hàng</a>
                <a href="${pageContext.request.contextPath }/checkout" class="btn btn-success mb-3">Mua hàng</a>
            </core:if>
            <core:if test="${list.size() == 0 }">
                <h2>Giỏ hàng chưa có sản phẩm nào</h2>
            </core:if>
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