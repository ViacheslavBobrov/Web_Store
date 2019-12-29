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

<title>Order credentials</title>

<%@ include file="jspf/head.jspf"%>
</head>
<body>
	<%@ include file="jspf/header.jspf"%>
	<!-- Page Content -->
	<div class="container">
		<c:choose>
			<c:when test="${empty cartBean.productMap}">
				<div class="text-center">
					<div class="col-md-offset-4 col-md-4">
						<div class="alert alert-success">Cart is empty</div>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="row">
					<div class=" col-md-8 table-responsive">
						<table class="table">

							<c:forEach var="entry" items="${cartBean.productMap}">
								<tr id="product_${entry.getKey().id}">
									<td><img src="localimage?name=product.png" alt=""></td>
									<td><h4>${entry.getKey().name}</h4> <br /> Category:
										${entry.key.category.name}<br /> Manufacturer:
										${entry.key.manufacturer.name}</td>
									<td>${entry.key.price}$</td>
									<td>${entry.value}</td>
								</tr>
							</c:forEach>
							<tr>
								<td></td>
								<td><div class="pull-right">Total:</div></td>
								<td><span name="total_price">$</span></td>
								<td></td>

							</tr>

						</table>
					</div>
				</div>
				<div class="row">
				
					<div class="col-md-offset-2 col-md-4">
						<form action="orderCredentials" method="post" role="form">
							<div class="form-group">
								<label for="card">Card number:</label>
								<div class="controls">
									<input type="text" class="form-control" id="card" name="cardId"
										required>
								</div>
							</div>
							<div class="form-group">
								<label for="address">Address:</label>
								<div class="controls">
									<input type="text" class="form-control" id="address"
										name="address" required>
								</div>
							</div>
							<input type="submit" class="btn btn-default" value="Submit"
								id="submit_button">
						</form>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
	</div>


	<!-- /.container -->
	<jsp:include page="jspf/footer.jspf" />
	
</body>
</html>