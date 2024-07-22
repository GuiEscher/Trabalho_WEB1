<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:url value="/cadastrarMedico" var="cadastro"/>
<%String ADM_KEY = (String) session.getAttribute("ADM_KEY");
  String errorMessage = (String) request.getAttribute("errorMessage"); 
%>

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
<html>

<head>
    <meta charset="ISO-8859-1">
    <title><fmt:message key="adm.novom.titulo"/></title>
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
            <h1><fmt:message key="adm.novom.header"/></h1>
            <a href="/home/listagemMedicos" class="button"><fmt:message key="button.label.voltar"/></a>
        </div>
    </header>

    <form action="/home/mudaLinguagem" style="text-align:left">
        <select id="lang" name="lang" onchange="submit()">
            <option value="pt" ${lang == 'pt' ? 'selected' : ''}>Português</option>
            <option value="en" ${lang == 'en' ? 'selected' : ''}>English</option>
        </select>
    </form>
    
    <form name="cadastroMedico" action="${cadastro}" method="post" onsubmit="return validateForm()">
        <fmt:message key="form.label.nome"/>: <input type="text" name="Nome" maxlength="50">
        <br/>
        <fmt:message key="form.label.CRM"/>: <input type="text" name="CRM" maxlength="10">
        <br/>
        <fmt:message key="form.label.email"/>: <input type="text" name="Email" maxlength="60">
        <br/>
        <fmt:message key="form.label.senha"/>: <input type="password" name="Senha" maxlength="20">
        <br/>
        <fmt:message key="form.label.especialidade"/>: <input type="text" name="Especialidade" maxlength="20">
        <br/>
    	<fmt:message key="form.label.submit" var="submit"/>
        <input type="submit" value="${submit}">
    </form>
    
    <div>
        <% if (errorMessage != null) { %>
            <div class="error"><%= errorMessage %></div>
        <% } %>
    </div>
    
    <% } %>
    
</body>

</html>
