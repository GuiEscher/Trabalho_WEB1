<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%String ADM_KEY = (String) session.getAttribute("ADM_KEY");%>

<% 
	String lang = (String) session.getAttribute("lang");
	// Portugues por default
	if (lang == null){
		lang = "pt";
		session.setAttribute("lang", lang);
	}
%>

<fmt:setLocale value="${lang}" />
<fmt:setBundle basename="linguagem"/>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="base-url" content="http://localhost:8080/home/homepage">
    <title><fmt:message key="adm.paciente.titulo"/></title>
    <link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@400;700&display=swap" rel="stylesheet">
</head>
<body>
 	<% if (ADM_KEY == null) { %>
        <c:redirect url="/paginaLogin.jsp?errorCode=1"/>
    <% } else { %>

    <header>
        <div class="header-container">
            <h1><a href="#" onclick="voltarParaBase(); return false;" style="color: #fff; text-decoration: none;"><fmt:message key="adm.paciente.header"/></a></h1>
            <a href="/home/listagemMedicos" class="button"><fmt:message key="adm.paciente.medico"/></a>
            <a href="/home/cadastrarPaciente" class="button"><fmt:message key="adm.paciente.novo"/></a>
            <a href="/home/logout" class="button">Logout</a>
        </div>
    </header>
    
    <form action="/home/mudaLinguagem" style="text-align:left">
        <select id="lang" name="lang" onchange="submit()">
            <option value="pt" ${lang == 'pt' ? 'selected' : ''}>Português</option>
            <option value="en" ${lang == 'en' ? 'selected' : ''}>English</option>
        </select>
    </form>

    <h2><fmt:message key="adm.paciente.lista"/></h2>

    <table>
        <tr>
            <th><fmt:message key="table.label.nomePaciente"/></th>
            <th><fmt:message key="table.label.CPFPaciente"/></th>
            <th><fmt:message key="form.label.datanascimento"/></th>
            <th><fmt:message key="form.label.email"/></th>
            <th><fmt:message key="form.label.sexo"/></th>
            <th><fmt:message key="form.label.telefone"/></th>
            <th><fmt:message key="table.label.config"/></th>
        </tr>
        <c:forEach var="paciente" items="${Pacientes}">
            <tr>
                <td>${paciente.nome}</td>
                <td>${paciente.CPF}</td>
                <td>${paciente.data_nascimento}</td>
                <td>${paciente.email}</td>
                <td>${paciente.sexo}</td>
                <td>${paciente.telefone}</td>
                <td>
	                <a href="/home/editaPaciente?CPF=${paciente.CPF}"><fmt:message key="table.label.edita"/></a> 
	                <a href="/home/removePaciente?CPF=${paciente.CPF}"><fmt:message key="table.label.remove"/></a>
                </td>
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
            max-width: 1200px; /* Aumenta a largura máxima da tabela */
        }

        th, td {
            border-top: 1px solid black;
            border-bottom: 1px solid black;
            padding: 12px; /* Aumenta o espaçamento entre as células */
            text-align: left;
        }

        th {
            background-color: #d0e0f0;
            border-top: 1px solid black;
            border-bottom: 2px solid black;
        }
    </style>
    
     <% } %>
</body>
</html>
