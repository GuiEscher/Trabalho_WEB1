<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% String CRM = (String) request.getAttribute("CRM"); %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Minhas Consultas - Médico</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="base-url" content="http://localhost:8080/home/minhasConsultasMedico">
    <link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@400;700&display=swap" rel="stylesheet">
</head>
    
    
<body>
    <% if (CRM == null) { %>
        <c:redirect url="/home/login?errorCode=1"/>
    <% } else { %>
        
    <% } %>
    
    <header>
        <div class="header-container">
            <h1>Consultas.com  - Meu Id: <%= CRM %></h1>
            <a href="/home/homepage" class="button">Logout</a>
        </div>
    </header>
    
    <h2>Minhas consultas</h2>

    <table>
        <tr>
            <th>Nome do Paciente</th>
            <th>CPF do Paciente</th>
            <th>Data</th>
            <th>Horário</th>
        </tr>
        <c:forEach var="consulta" items="${Consultas}">
            <tr>
                <td>${consulta.nomepaciente}</td>
                <td>${consulta.cpf_paciente}</td>
                <td>${consulta.dataconsulta}</td>
                <td>${consulta.horario}</td>
            </tr>
        </c:forEach>
    </table>
    
    <style>
        body {
            font-family: 'Ubuntu', sans-serif;
            margin: 0;
            padding: 0;
        }

        header {
            background-color: #474284;
            color: #fff;
            padding: 10px 0;
            text-align: center;
            margin: 0;
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
            background-color: #003366;
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
        }

        .header-container a.button:hover {
            background-color: #002244;
        }

        .search-container {
            display: flex;
            justify-content: center;
            margin: 20px auto;
        }

        .search-container input[type="text"] {
            width: 300px;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            margin-right: 10px;
            box-sizing: border-box;
        }

        .search-container input[type="button"] {
            padding: 10px 20px;
            border: 1px solid #003366;
            background-color: #003366;
            color: #fff;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            text-align: center;
            display: inline-block;
        }

        .search-container input[type="button"]:hover {
            background-color: #002244;
        }

        h2 {
            margin: 20px auto;
            max-width: 960px;
            text-align: center;
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
            background-color: #d0e0f0;
            border-top: 1px solid black;
            border-bottom: 2px solid black;
        }
    </style>
    
</body>
</html>