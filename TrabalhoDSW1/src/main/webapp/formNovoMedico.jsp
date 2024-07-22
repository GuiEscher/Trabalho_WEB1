<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url value="/cadastrarMedico" var="cadastro"/>
<%String ADM_KEY = (String) session.getAttribute("ADM_KEY");
  String errorMessage = (String) request.getAttribute("errorMessage"); 
%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="ISO-8859-1">
    <title>Cadastro de Médico</title>
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
            font-weight: bold;
        }
    </style>
    <script>
        function validateForm() {
            var form = document.forms["cadastroMedico"];
            var fields = ["Nome", "CRM", "Email", "Senha", "Especialidade"];
            
            for (var i = 0; i < fields.length; i++) {
                var field = form[fields[i]].value;
                if (field === "") {
                    alert("Por favor, preencha todos os campos.");
                    return false;
                }
            }

            return true;
        }
    </script>
</head>

<body>

   <% if (ADM_KEY == null) { %>
        <c:redirect url="/paginaLogin.jsp?errorCode=1"/>
   <% } else { %>
	
    <header>
        <div class="header-container">
            <h1>Cadastro de Médico</h1>
            <a href="/home/listagemMedicos" class="button">Voltar</a>
        </div>
    </header>

    <form name="cadastroMedico" action="${cadastro}" method="post" onsubmit="return validateForm()">
        Nome: <input type="text" name="Nome" maxlength="50">
        <br/>
        CRM: <input type="text" name="CRM" maxlength="10">
        <br/>
        Email: <input type="text" name="Email" maxlength="60">
        <br/>
        Senha: <input type="password" name="Senha" maxlength="20">
        <br/>
        Especialidade: <input type="text" name="Especialidade" maxlength="20">
        <br/>
        <input type="submit" value="Cadastrar">
    </form>
    
    <div>
        <% if (errorMessage != null) { %>
            <div class="error"><%= errorMessage %></div>
        <% } %>
    </div>
    
    <% } %>
    
</body>

</html>
