<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
	<a class="navbar-brand" href="/home">Trizu</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
  		<span class="navbar-toggler-icon"></span>
	</button>
 	 <div class="collapse navbar-collapse" id="navbarSupportedContent">
    	<ul class="navbar-nav mr-auto">
			<li class="nav-item active">
        		<a class="nav-link" href="/home">Home <span class="sr-only">(current)</span></a>
        	</li>
			<li class="nav-item">
				<c:choose>
					<c:when test="${pageContext.request.userPrincipal.name != null}">
						<a class="nav-link" href="/changePassword">Change Password</a>
    				</c:when>    
    				<c:otherwise>
						<a class="nav-link" href="/registration">Create Account</a>
    				</c:otherwise>
				</c:choose>
      		</li>
      		<li class="nav-item dropdown">
        		<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Houses</a>
        		<div class="dropdown-menu" aria-labelledby="navbarDropdown">
          			<a class="dropdown-item" href="/addHouse">Add House</a>
          			<a class="dropdown-item" href="/selectHouse">House Panel</a>
          			<div class="dropdown-divider"></div>
          			<a class="dropdown-item" href="#">Something else here</a>
        		</div>
      		</li>
    	</ul>
    	<c:choose>
    		<c:when test="${pageContext.request.userPrincipal.name != null}">
		    	<form id="logoutForm" method="POST" action="${contextPath}/logout">
            		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        		</form>
		 		<button type="button" onclick="document.forms['logoutForm'].submit()" class="btn btn-link">Logout</button>
    		</c:when>    
    		<c:otherwise>
        		<a class="nav-link" href="login">Sing in <span class="sr-only">(current)</span></a>
    		</c:otherwise>
		</c:choose>
	</div>
</nav>
