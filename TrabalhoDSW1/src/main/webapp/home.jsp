<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url value="/listagemMedicos" var="home"/>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Consultas.com - Home</title>
    <link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@400;700&display=swap" rel="stylesheet">
    
</head>
<body>
    <header>
        <div class="header-container">
            <h1>Consultas.com</h1>
            <a href="pagina-de-login.html" class="button">Login</a>
        </div>
    </header>
    
    <div class="search-container">
        <input type="text" name="Especialidade" placeholder="Pesquise uma especialidade" >
        <input type="submit">
    </div>

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
                <td>${medico.CRM}</td>
                <td>${medico.especialidade}</td>
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
            box-sizing: border-box; /* Ensure padding is included in the width */
        }

        .search-container input[type="submit"] {
            padding: 10px 20px;
            border: 1px solid #003366; /* Azul mais escuro para o botão */
            background-color: #003366; /* Azul mais escuro para o botão */
            color: #fff;
            border-radius: 5px;
            cursor: pointer;
        }

        .search-container input[type="submit"]:hover {
            background-color: #002244; /* Azul ainda mais escuro para o hover */
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
            background-color: #d0e0f0; /* Azul claro */
            border-top: 1px solid black;
            border-bottom: 2px solid black;
        }
    </style>
    
</body>
</html>
