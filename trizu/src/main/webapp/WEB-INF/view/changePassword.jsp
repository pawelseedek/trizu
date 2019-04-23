<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Change Password</title>
<%@ include file="header.jsp" %>
</head>
<body>
<%@ include file="template.jsp" %>
    <div class="container">
	<form:form method="POST" class="form-singin"  modelAttribute="passwordForm">
		<h2 class="form-signin-heading">Change your password</h2>
		<spring:bind path="password">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<form:input type="password" path="password" class="form-control" placeholder="Password"/>
				<form:errors path="password"></form:errors>
			</div>
		</spring:bind>
		
		<spring:bind path="passwordConfirm">
			<div class="form-group ${status.error ? 'has-error' : '' }">
				<form:input type="password" path="passwordConfirm" class="form-control" placeholder="Confirm Password"></form:input>
				<form:errors path="passwordConfirm"></form:errors>
			</div>
		</spring:bind>
		
		<button class="btn btn-lg btn-primary btn-block" type="submit">Change</button>
	</form:form>
	</div>
<%@ include file="footer.jsp" %>
</body>
</html>