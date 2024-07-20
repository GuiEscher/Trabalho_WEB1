<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url value="/listagemMedicos" var="home"/>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Consultas.com - Home</title>
    
</head>
<body>
    <header>
        <div class="header-container">
            <h1>Consultas.com</h1>
            <a href="pagina-de-login.html" class="button">Login</a>
        </div>
    </header>
    
    
    <input type="text" name="Especialidade" value="Pesquise uma especialidade">
    <input type="submit">
    
    <br/>
  

<h2>Listagem de Médicos</h2>

<table>
    <tr>
        <th>Nome</th>
        <th>CRM</th>
        <th>Especialidade</th>
    </tr>
    <c:forEach var="medico" items="${Medicos}">
        <tr>
            <td>${medico.nome}</td>
            <td>${medico.crm}</td>
            <td>${medico.especialidade}</td>
        </tr>
    </c:forEach>
</table>
    
    
    
</body>

<style>
        /* Estilos opcionais para o cabeçalho */
        header {
            background-color: #474284;
            color: #fff;
            padding: 10px 0;
            text-align: center;
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
            background-color: #373737;
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
        }
        .header-container a.button:hover {
            background-color: #505050;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
        }
    </style>
</html>