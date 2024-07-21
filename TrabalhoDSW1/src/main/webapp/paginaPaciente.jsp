<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% String CPF= (String) request.getAttribute("CPF"); %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Pagina do Paciente</title>
	</head>
<body>
	CPF: <%=CPF %>
</body>
</html>