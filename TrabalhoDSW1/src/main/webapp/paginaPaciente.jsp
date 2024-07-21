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
	    <h2>Listagem de Consultas</h2>
	    <table>
	        <tr>
	            <th>Nome</th>
	            <th>CRM</th>
	            <th>Especialidade</th>
	        </tr>
	        <c:forEach var="consulta" items="${Consultas}">
	            <tr>
	                <td>${medico.nome}</td>
	                <td>${medico.CRM}</td>
	                <td>${medico.especialidade}</td>
	            </tr>
	        </c:forEach>
	    </table>
	<%} %>
</body>
</html>