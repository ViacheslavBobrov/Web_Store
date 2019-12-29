<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Cart</title>

<%@ include file="jspf/head.jspf"%>
</head>
<body>
	<%@ include file="jspf/header.jspf"%>
	<!-- Page Content -->
	<div class="container">

		<div class="row">
			<c:choose>
				<c:when test="${empty cartBean.productMap}">
					<div class="text-center">
						<div class="col-md-offset-4 col-md-4">
							<div class="alert alert-success">Cart is empty</div>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<div class=" col-md-10 table-responsive">
						<div class="text-center">
							<h1><fmt:message key="cart.your_cart" bundle="${bundle}" /></h1>
						</div>
						<table class="table">

							<c:forEach var="entry" items="${cartBean.productMap}">
								<tr id="product_${entry.getKey().id}">
									<td><img src="localimage?name=product.png" alt=""></td>
									<td><h4>${entry.getKey().name}</h4> <br /> <fmt:message key="cart.category" bundle="${bundle}" />:
										${entry.getKey().category.name}<br /> <fmt:message key="cart.manufacturer" bundle="${bundle}" />:
										${entry.getKey().manufacturer.name}</td>
									<td>${entry.getKey().price}$</td>
									<td><button type="button" class="btn btn-success"
											name="cart_decrement_button" productId="${entry.getKey().id}">-</button>
										<button type="button" class="btn btn-success"
											name="cart_increment_button" productId="${entry.getKey().id}">+</button>
										<div name="product_count">${entry.value}</div></td>
									<td><button type="button" class="btn btn-danger"
											name="cart_delete_button" productId="${entry.getKey().id}">Delete</button></td>
								</tr>
							</c:forEach>
							<tr>
								<td><a href="clearCart" class="btn btn-danger"><fmt:message key="cart.clear" bundle="${bundle}" /></a></td>
								<td><div class="pull-right"><fmt:message key="cart.total" bundle="${bundle}" />:</div></td>
								<td><span name="total_price"></span></td>
								<td></td>
								<td><a href="orderCredentials" class="btn btn-success"><fmt:message key="cart.create_order" bundle="${bundle}" /></a></td>
							</tr>


						</table>
					</div>
				</c:otherwise>
			</c:choose>
		</div>

	</div>
	<!-- /.container -->
	<jsp:include page="jspf/footer.jspf" />
	
</body>
</html>