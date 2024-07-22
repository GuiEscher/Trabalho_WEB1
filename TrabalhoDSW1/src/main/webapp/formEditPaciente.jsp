<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url value="/editaPaciente" var="Salvar"/>
<% String ADM_KEY = (String) session.getAttribute("ADM_KEY"); %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="ISO-8859-1">
    <title>Editar Paciente</title>
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

        form {
            max-width: 960px;
            margin: 20px auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            background-color: #f9f9f9;
        }

        form input[type="text"],
        form input[type="password"] {
            width: calc(100% - 22px);
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            margin-bottom: 10px;
            box-sizing: border-box;
        }

        form input[type="submit"] {
            padding: 10px 20px;
            border: 1px solid #003366;
            background-color: #003366;
            color: #fff;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            text-align: center;
        }

        form input[type="submit"]:hover {
            background-color: #002244;
        }

        .error {
            color: red;
            font-size: 0.9em;
        }

        .success {
            color: green;
            font-size: 0.9em;
        }
    </style>
    <script>
        function validateForm() {
            var form = document.forms["pacienteForm"];
            var fields = ["Nome", "Telefone", "Email", "Senha", "Sexo", "DataNascimento"];
            
            for (var i = 0; i < fields.length; i++) {
                var field = form[fields[i]].value;
                if (field === "") {
                    alert("Por favor, preencha todos os campos.");
                    return false;
                }
            }

            var sexo = form["Sexo"].value;
            if (sexo !== "M" && sexo !== "F") {
                alert("Por favor, insira 'M' ou 'F' no campo Sexo.");
                return false;
            }

            var dataNascimento = form["DataNascimento"].value;
            if (!isValidDate(dataNascimento)) {
                alert("Por favor, insira uma data válida no formato 'dd/mm/aaaa'.");
                return false;
            }

            return true;
        }

        function isValidDate(dateString) {
            // Verifica se está no formato dd/mm/aaaa
            var regex = /^(\d{2})\/(\d{2})\/(\d{4})$/;
            if (!regex.test(dateString)) return false;

            // Divide a data em partes
            var parts = dateString.split("/");
            var day = parseInt(parts[0], 10);
            var month = parseInt(parts[1], 10);
            var year = parseInt(parts[2], 10);

            // Verifica a validade da data
            var date = new Date(year, month - 1, day);
            return date.getFullYear() === year && date.getMonth() + 1 === month && date.getDate() === day;
        }
    </script>
</head>

<body>

  <% if (ADM_KEY == null) { %>
        <c:redirect url="/paginaLogin.jsp?errorCode=1"/>
   <% } else { %>
    <header>
        <div class="header-container">
            <h1>Edição de paciente</h1>
            <a href="/home/listagemPacientes" class="button">Voltar</a>
        </div>
    </header>

    <form name="pacienteForm" action="${Salvar}" method="post" onsubmit="return validateForm()">
        CPF: <input type="text" name="CPF" value="${paciente.CPF}" readonly>
        <br/>
        Nome: <input type="text" name="Nome" value="${paciente.nome}" maxlength="50">
        <br/>
        Telefone: <input type="text" name="Telefone" value="${paciente.telefone}" maxlength="15">
        <br/>
        Email: <input type="text" name="Email" value="${paciente.email}" maxlength="60">
        <br/>
        Senha: <input type="password" name="Senha" value="${paciente.senha}" maxlength="20">
        <br/>
        Sexo: <input type="text" name="Sexo" value="${paciente.sexo}" maxlength="1">
        <br/>
        Data Nascimento: <input type="text" name="DataNascimento" value="${paciente.data_nascimento}">
        <br/>
        <input type="submit" value="Salvar">
    </form>
    
  <% } %>
</body>

</html>
