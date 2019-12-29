<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle basename="resources" var="bundle" />
<ul class="nav  navbar-nav navbar-left">
	<li>
		<div class="btn-group">
			<button type="button"
				class="btn btn-default dropdown-toggle navbar-btn"
				data-toggle="dropdown">
				<fmt:message key="language" bundle="${bundle}" />
				<span class="caret"></span>
			</button>
			<ul class="dropdown-menu" role="menu">
				<c:choose>
					<c:when test="${empty pageContext.request.queryString}">
						<li><a href="${request.getRequestURL()}?lang=en">English</a></li>
						<li><a href="${request.getRequestURL()}?lang=ru">Русский</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="${request.getRequestURL()}?${pageContext.request.queryString}&lang=en">English</a></li>
						<li><a href="${request.getRequestURL()}?${pageContext.request.queryString}&lang=ru">Русский</a></li>
					</c:otherwise>
				</c:choose>
				
			</ul>
		</div>
	</li>
</ul>

;
