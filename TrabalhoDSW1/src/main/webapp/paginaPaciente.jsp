<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% //String CPF = (String) request.getAttribute("CPF"); %>
<%String CPF = (String) session.getAttribute("CPF");%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pagina do Paciente</title>
    <style>
        body {
            font-family: 'Ubuntu', sans-serif;
            margin: 0;
            padding: 0;
        }

        header {
            background-color: #474284; /* Cor do cabeçalho */
            color: #fff;
            padding: 10px 0;
            text-align: center;
            margin: 0; /* Remove a margem */
        }

        .header-container {
            display: flex;
            justify-content: space-between;
            align-items: center;
            max-width: 960px;
            margin: 0 auto;
            padding: 0 20px;
        }

        .header-container h1 {
            margin: 0;
            font-size: 24px;
        }

        .header-container a.button {
            display: inline-block;
            padding: 10px 20px;
            background-color: #003366; /* Azul mais escuro para o botão */
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
        }

        .header-container a.button:hover {
            background-color: #002244; /* Azul ainda mais escuro para o hover */
        }

        h2 {
            margin: 20px auto;
            max-width: 960px;
            text-align: center;
            color: #003366; /* Azul para o título */
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px auto;
            max-width: 960px;
        }

        th, td {
            border-top: 1px solid black;
            border-bottom: 1px solid black;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #d0e0f0; /* Azul claro */
            border-top: 1px solid black;
            border-bottom: 2px solid black;
        }

        .container {
            max-width: 960px;
            margin: 20px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body>
	
    <% if (CPF == null) { %>
        <c:redirect url="/paginaLogin.jsp?errorCode=1"/>
    <% } else { %>
        <header>
            <div class="header-container">
                <h1>Bem-vindo, Paciente</h1>
                <a href="logout.jsp" class="button">Sair</a>
                <a href="formConsultas.jsp" class="button">Cadastrar Consulta</a>
            </div>
        </header>

        <div class="container">
            <h2>Listagem de Consultas</h2>
            <table>
                <tr>
                    <th>Nome</th>
                    <th>CRM</th>
                    <th>Especialidade</th>
                </tr>
                <c:forEach var="consulta" items="${Consultas}">
                    <tr>
                        <td>${consulta.nome}</td>
                        <td>${consulta.CRM}</td>
                        <td>${consulta.especialidade}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    <% } %>
</body>
</html>