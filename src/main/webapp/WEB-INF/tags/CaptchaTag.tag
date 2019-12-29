<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle basename="resources" var="bundle"/>
<div class="form-group">

	<label for="captchaValue"><fmt:message key="input_captcha" bundle="${bundle}" />:</label>
	<c:choose>
		<c:when test="${not empty captchaId}">
			<input type="hidden" name="captchaId" value="${captchaId}">
			<img src="captcha?captchaId=${captchaId}" />
		</c:when>
		<c:otherwise>
			<img src="captcha" />
		</c:otherwise>
	</c:choose>
	<div class="controls">
		<input type="text" class="form-control" name="captchaValue"
			id="captchaValue">
	</div>
</div>