<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Select House</title>
<%@ include file="header.jsp" %>
</head>
<body>
<%@ include file="template.jsp" %>
<div class="container">
<table class="table table-striped">
  <thead>
    <tr>    
      <th scope="col">House Name</th>
    </tr>
  </thead>
  <tbody>
      <c:forEach items="${houseList}" var="item">
			<tr>
   				<td>
   					<div style="float:left"> <a href="/housePanel/${item.houseid}" >${item.housename}</a> </div>
   					<div style="float:right"><a href="/editHouse/${item.houseid}" class="btn btn-info" role="button" >Edit</a><br></div>
   				</td>
   			</tr>
		</c:forEach>
  </tbody>
</table>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>