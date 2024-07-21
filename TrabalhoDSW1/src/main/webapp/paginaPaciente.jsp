<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% String CPF= (String) request.getAttribute("CPF"); %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Pagina do Paciente</title>
	</head>
<body>
	<%if (CPF == null) { %>
		<c:redirect url="/paginaLogin.jsp?errorCode=1"/>
	<%} else { %>
		CPF: <%=CPF %>
	<%} %>
</body>
</html>