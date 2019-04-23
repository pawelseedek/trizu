<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add House</title>
<%@ include file="header.jsp" %> 
</head>
<body>
<%@ include file="template.jsp" %> 
 
<!--  wczytaj pola domu jesli istnieja, umozliw dodanie nowych jesli nie istnieja  -->
 
 
	<div class="container">
	
	<c:if test="${connectionError != null }">${connectionError}</c:if>
	<form:form method="POST" modelAttribute="pin" class="form-signin">
            <h2 class="form-signin-heading">Add Pin</h2>
			
            <spring:bind path="pinNumber">
                	Select pin: 
					<select name="pinNumber">
					  <c:forEach items="${freePins}" var="freePins">
						<option value="${freePins}">
							${freePins}
						</option>
					  </c:forEach>
					</select>
            </spring:bind>
             <spring:bind path="pinType">
                	Select type: 
					<select name="PinType">
						<option value="true"> output </option>
						<option value="false"> input </option>
					</select>
            </spring:bind>
            <spring:bind path="name">
                    <form:input type="text" path="name" class="form-control" placeholder="Pin name..."></form:input>
            </spring:bind>
            
             <spring:bind path="state">
                    <form:input type="hidden" path="state" class="form-control" state="F"></form:input>
			</spring:bind>

            <button class="btn btn-lg btn-primary btn-block" type="submit">Add</button>
        </form:form>
	
		<table class="table table-striped">
			<thead>
			<tr>    
			  <th scope="col">House Pins</th>
			</tr>
			<tr>
				<th>Name</th>
				<th>Number</th>
				<th>Type</th>
			</tr>
			</thead>
			<tbody>
				<c:forEach items="${housePins}" var="list">
				<tr>
					<td>${list.name}</td>
					<td>${list.pinNumber}</td>
					<td><c:if test="${list.pinType == true }"> Output</c:if>
						<c:if test="${list.pinType == false }"> Input</c:if></td>
					<td><div style="float:right"><a href="/deletePin/${houseid}/${list.pinId}" class="btn btn-danger" role="button" >Delete</a></div></td>
				</tr>
			</c:forEach>
		  </tbody>
		</table>
    </div>
    
<%@ include file="footer.jsp" %>
</body>
</html>