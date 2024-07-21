<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:url value="/login" var = "linkServletLogin"/>


<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Login</title>
</head>

<body>

	<form action="${linkServletLogin}" method="post">
		Email: <input type="text" name="email"/>
		Senha: <input type="text" name="senha"/>
		<input type="submit"/>
	</form>
        <%
            String errorCode= (String) request.getParameter("errorCode");
            if (errorCode != null) {
            	if (errorCode.equals("1")){
        %>
            <div class="error-message">Email ou senha não encontrados.</div>
        <%
            	}
            }
        %>
</body>
</html>