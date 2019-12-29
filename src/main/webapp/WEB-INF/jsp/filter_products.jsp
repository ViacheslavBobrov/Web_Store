<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>
<%@ taglib prefix="ct" uri="/WEB-INF/tld/tags.tld"%>

<html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Search products</title>

<%@ include file="jspf/head.jspf"%>
</head>
<body>
	<%@ include file="jspf/header.jspf"%>
	<!-- Page Content -->
	<div class="container">

		<div class="row">
			<div class="">
				<%@ include file="jspf/filter_sidebar.jspf"%>
			</div>
			<div class="col-md-9">

				<div class="row">

					<c:choose>
						<c:when test="${empty products}">
							<div class="text-center">
								<div class="col-md-offset-4 col-md-4">
									<div class="alert alert-success">No products found</div>
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<c:forEach var="product" items="${products}">
								<div class="col-sm-3 col-lg-3 col-md-3">
									<div class="thumbnail">
										<img src="localimage?name=${product.image}" alt="">
										<div class="caption">
											<h4 class="pull-right">$ ${product.price}</h4>
											<h4>
												<a href="#">${product.name}</a>
											</h4>

											<p>
												Category: ${product.category.name}<br /> Manufacturer:
												${product.manufacturer.name}<br /> ${product.description}
											</p>
											<button type="button" class="btn btn-info" role="button" name="cart_add_button" productId="${product.id}">
												<fmt:message key="add_to_cart" bundle="${bundle}" /></button>
											<!--  <a href="addCartProduct?productId=${product.id}"
												class="btn btn-info" "></a>-->
										</div>

									</div>
								</div>
							</c:forEach>
						</c:otherwise>
					</c:choose>

				</div>
				<div class="row">
					<div class="text-center">
						<ct:paginator />
					</div>
				</div>
			</div>


		</div>
	</div>
	<!-- /.container -->
	<jsp:include page="jspf/footer.jspf" />
	
	
</body>
</html>