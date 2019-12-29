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

<title>Login</title>

<%@ include file="jspf/head.jspf"%>

</head>
<body>
	<%@ include file="jspf/header.jspf" %> 
	<!-- Page Content -->
	<div class="container">

		<div class="row">
			<div class="col-md-offset-4 col-md-4">
				<div class="text-center">
					<h1><fmt:message key="login.form.header" bundle="${bundle}" /></h1>
				</div>


				<c:if test="${not empty error}">
						<div class="alert alert-danger">							
								${error}							
						</div>
					</c:if>
				<form action="login" method="post" role="form">
					<c:if test="${not empty errors}">
						<div class="alert alert-danger">
							<c:forEach var="error" items="${errors}">
								${error}</br>
							</c:forEach>
						</div>
					</c:if>
					
					<div class="form-group">
						<label for="email"><fmt:message key="login.email" bundle="${bundle}" />:</label>
						<div class="controls">
							<input type="email" class="form-control" name="email" id="email" value="${email}" required>
						</div>
					</div>
					<div class="form-group">
						<label for="password"><fmt:message key="login.password" bundle="${bundle}" />: </label>
						<div class="controls">
							<input type="password" class="form-control" name="password" required
								id="firstPassword">
						</div>
					</div>
					<input type="hidden" name="unauthorizedRequest"  value="${unauthorizedRequest}" >
					<input type="submit" class="btn btn-default" value="<fmt:message key="submit" bundle="${bundle}" />"
						id="submit_button">

				</form>


			</div>

		</div>

	</div>
	<!-- /.container -->
	<jsp:include page="jspf/footer.jspf" />
	

</body>
</html>