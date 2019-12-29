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

<title>Error</title>

<%@ include file="jspf/head.jspf"%>
</head>
<body>
	<%@ include file="jspf/header.jspf" %> 
	<!-- Page Content -->
	<div class="container">

		<div class="row">
			<div class="col-md-offset-4 col-md-4">
				<div class="alert alert-danger">
				Error occurred</br>
				${error}</div>
			</div>
		</div>

	</div>
	<!-- /.container -->
	<jsp:include page="jspf/footer.jspf" />


	
</body>
</html>