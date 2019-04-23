<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <title>House Control Panel</title>
    <%@ include file="header.jsp" %>
    <script src="/webjars/jquery/jquery.min.js"></script>
    <script src="/webjars/sockjs-client/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/stomp.min.js"></script>
    <script src="/js/app.js"></script>
</head>
<style>
.label-large {
  vertical-align: super;
  font-size: large;
  }
  #error{
  color: red;
   font-size: large;
  }
}
</style>
<body>
	<%@ include file="template.jsp" %>
<script>
	var houseid = '${houseid}';
</script>

	<div id="main-content" class="container" > 
	<div id="error" style="width:100%"></div>
		<table id="table" class="table table-striped" style="width: 60%">
			<thead>
				<tr>    
					<th scope="col" ><h1>House Objects</h1></th>
				</tr>
				<tr>
					<th>Name</th>
					<th>State</th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="greetings">
	</div>
	<%@ include file="footer.jsp" %>
</body>
</html>