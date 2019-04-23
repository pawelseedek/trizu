<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add House</title>
<%@ include file="header.jsp" %> 
</head>
<body>
<%@ include file="template.jsp" %> 
 
	<div class="container">

        <form:form method="POST" modelAttribute="houseForm" class="form-signin">
            <h2 class="form-signin-heading">Add your House</h2>
            <spring:bind path="housename">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="housename" class="form-control" placeholder="House name..."
                                autofocus="true"></form:input>
                    <form:errors path="housename"></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="houseid">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="houseid" class="form-control" placeholder="House Id..."></form:input>
                    <form:errors path="houseid"></form:errors>
                </div>
            </spring:bind>
			<spring:bind path="password">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="text" path="password" class="form-control" placeholder="Password..."></form:input>
                    <form:errors path="password"></form:errors>
                </div>
            </spring:bind>
            
       <!--       <spring:bind path="housePins">
                    <form:input type="hidden" path="housePins" class="form-control" value ="{\"okno\" : \"true\"}"></form:input>
			</spring:bind> 
			
			-->

            <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
        </form:form>
        
		<c:if test="${noConnection != null }"> ${noConnection}</c:if>

    </div>
    
<%@ include file="footer.jsp" %>
</body>
</html>