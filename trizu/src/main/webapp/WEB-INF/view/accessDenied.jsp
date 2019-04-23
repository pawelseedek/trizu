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

<style>
img {
  display: block;
  margin-left: auto;
  margin-right: auto;
  padding: 10%;
}
</style>    
    
</head>
<body>
<%@ include file="template.jsp" %>

    <img class="center" src="/obrazy/no.png" alt="ACCESS DENIED">

<%@ include file="footer.jsp" %>
</body>
</html>