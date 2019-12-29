<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle basename="resources" var="bundle"/>
<ul class="nav  navbar-nav navbar-right">
	<c:choose>
		<c:when test="${not empty user}">

			<li>
				<div class="btn-group">
					<button type="button"
						class="btn btn-default dropdown-toggle navbar-btn"
						data-toggle="dropdown">
						${user.surname}  ${user.name}<span class="caret"></span>
					</button>
					<ul class="dropdown-menu" role="menu">
						<li>
							<div class="col-md-offset-1 col-md-10">
								<div class="text-center">
									<img src="localimage?name=${user.avatar}"
										class="img-responsive"><br />
									<h5>${user.surname} ${user.name}</h5>
								</div>
							</div>

						</li>

					</ul>
				</div>
			</li>
			<li><a href="logout"><fmt:message key="logout" bundle="${bundle}" /></a></li>
		</c:when>
		<c:otherwise>
			<li>
				<div class="btn-group">
					<button type="button"
						class="btn btn-default dropdown-toggle navbar-btn"
						data-toggle="dropdown">
						<fmt:message key="login.form.header" bundle="${bundle}" /> <span class="caret"></span>
					</button>
					<ul class="dropdown-menu" role="menu">
						<li>
							<div class="col-md-offset-1 col-md-10">
								<form action="login" method="post" role="form">

									<div class="form-group">
										<label for="email"><fmt:message key="login.email" bundle="${bundle}" />:</label>
										<div class="controls">
											<input type="email" class="form-control" name="email"
												required id="email">
										</div>
									</div>
									<div class="form-group">
										<label for="password"><fmt:message key="login.password" bundle="${bundle}" />: </label>
										<div class="controls">
											<input type="password" class="form-control" name="password"
												required id="firstPassword">
										</div>
									</div>

									<input type="submit" class="btn btn-default" value="<fmt:message key="submit" bundle="${bundle}" />"
										id="submit_button">
								</form>
								<div class="text-center">
									<p class="navbar-btn">
										<a href="login" class="btn btn-success"><fmt:message key="go_to_login" bundle="${bundle}" /></a>
									</p>
								</div>

							</div>

						</li>

					</ul>
				</div>
			</li>
			<li><a href="registration"><fmt:message key="registration" bundle="${bundle}" /></a></li>
		</c:otherwise>
	</c:choose>
</ul>

