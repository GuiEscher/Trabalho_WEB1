<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% String CRM= (String) request.getAttribute("CRM"); %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Pagina do Medico</title>
	</head>
<body>
	<%if (CRM == null) { %>
		<c:redirect url="/paginaLogin.jsp?errorCode=1"/>
	<%} else { %>
		CRM: <%=CRM %>
	<%} %>
</body>
</html>