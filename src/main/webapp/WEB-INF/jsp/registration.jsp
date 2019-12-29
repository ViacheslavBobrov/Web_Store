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

<title>Registration</title>


<%@ include file="jspf/head.jspf"%>
</head>
<body>
	<%@ include file="jspf/header.jspf" %> 
	<!-- Page Content -->
	<div class="container">

		<div class="row">
			<div class="col-md-offset-4 col-md-4">
				<div class="text-center">
					<h1><fmt:message key="registration" bundle="${bundle}" /></h1>
				</div>



				<form action="registration" method="post" role="form" enctype="multipart/form-data">
					<c:if test="${not empty errors}">
						<div class="alert alert-danger">
							<c:forEach var="error" items="${errors}">
								${error}</br>
							</c:forEach>
						</div>
					</c:if>
					<c:if test="${not empty successMessage}">
						<div class="alert alert-success">${successMessage}</div>
					</c:if>
					<div class="form-group">
						<label for="name"><fmt:message key="registartion.name" bundle="${bundle}" />:</label>
						<div class="controls">
							<input type="text" class="form-control" name="name" id="name"
								value="${formBean.name}"> <span class="help-inline"
								id="name_error"> Name must be 2-25 symbols long and
								consist of latin letters or numbers </span>
						</div>

					</div>
					<div class="form-group">
						<label for="surname"><fmt:message key="registartion.surname" bundle="${bundle}" />:</label>
						<div class="controls">
							<input type="text" class="form-control" name="surname"
								id="surname" value="${formBean.surname}"> <span
								class="help-inline" id="surname_error"> Surname must be
								2-25 symbols long and consist of latin letters or numbers </span>
						</div>

					</div>
					<div class="form-group">
						<label for="email"><fmt:message key="login.email" bundle="${bundle}" />:</label>
						<div class="controls">
							<input type="email" class="form-control" name="email" id="email"
								value="${formBean.email}"> <span class="help-inline"
								id="email_error"> Not valid Email </span>
						</div>
					</div>
					<div class="form-group">
						<label for="firstPassword"><fmt:message key="login.password" bundle="${bundle}" />: </label>
						<div class="controls">
							<input type="password" class="form-control" name="firstPassword"
								id="firstPassword""> <span class="help-inline"
								id="firstPassword_error">Password must be 5-25 symbols
								long and consist of latin letters or numbers </span>
						</div>
					</div>
					<div class="form-group">
						<label for="secondPassword"><fmt:message key="registration.password_again" bundle="${bundle}" />:</label>
						<div class="controls">
							<input type="password" class="form-control" name="secondPassword"
								id="secondPassword"> <span class="help-inline"
								id="secondPassword_error"> Passwords must be equal </span>
						</div>
					</div>
					<div class="form-group">
						<label for="photo"><fmt:message key="registration.upload_photo" bundle="${bundle}" /></label>
						<div class="controls">
							<input type="file" class="btn btn-default" name="avatar" 
								id="photo"> 
						</div>
					</div>
					
					<div class="checkbox">
						<label> <c:if test="${formBean.deliveriesChecked}">
								<input type="checkbox" name="deliveriesChecked" checked>
							</c:if> <c:if test="${not formBean.deliveriesChecked}">
								<input type="checkbox" name="deliveriesChecked">
							</c:if> <fmt:message key="registration.email_deliveries" bundle="${bundle}" />
						</label>
					</div>
					<tag:CaptchaTag />
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