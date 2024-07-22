<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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

<% 
    // Recuperar mensagens de erro e sucesso (se houver)
    String errorMessage = (String) session.getAttribute("errorMessage");
    String successMessage = (String) session.getAttribute("successMessage");

    // Se for a primeira vez na página, limpar mensagens
    if (errorMessage == null && successMessage == null) {
        session.removeAttribute("errorMessage");
        session.removeAttribute("successMessage");
    }

    // Determinar qual mensagem exibir
    String messageToShow = null;
    if (successMessage != null) {
        messageToShow = successMessage;
        session.removeAttribute("successMessage");
    } else if (errorMessage != null) {
        messageToShow = errorMessage;
        session.removeAttribute("errorMessage");
    }
%>
<c:url value="/agendarConsulta" var="home"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="https://fonts.googleapis.com/css2?family=Ubuntu:wght@400;700&display=swap" rel="stylesheet">
    <title><fmt:message key="pagina.pacientec.titulo"/></title>
    <style>
        body {
            font-family: 'Ubuntu', sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f0f0f0;
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

        .container {
            max-width: 960px;
            margin: 20px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        .form-group {
            margin-bottom: 15px;
        }

        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }

        .form-group input[type="text"],
        .form-group select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        .form-group input[type="submit"] {
            padding: 10px 20px;
            border: 1px solid #003366;
            background-color: #003366;
            color: #fff;
            border-radius: 5px;
            cursor: pointer;
        }

        .form-group input[type="submit"]:hover {
            background-color: #002244;
        }

        .error-message {
            color: red;
            margin-top: 10px;
            text-align: center;
        }

        .success-message {
            color: green;
            margin-top: 10px;
            text-align: center;
        }
    </style>
    <script>
        function validarFormulario() {
            const cpf = document.getElementById("CPF_Paciente").value;
            const crm = document.getElementById("CRM_Medico").value;
            const horario = document.getElementById("Horario").value;
            const dataConsulta = document.getElementById("DataConsulta").value;

            const cpfRegex = /^\d{11}$/;
            const crmRegex = /^\d{4,10}$/;
            const horarioRegex = /^([01]\d|2[0-3]):([0-5]\d)$/; // HH:MM formato 24h
            const dataRegex = /^\d{2}\/\d{2}\/\d{4}$/;

            if (!cpfRegex.test(cpf)) {
                alert("CPF deve ter 11 dígitos numéricos.");
                return false;
            }
            if (!crmRegex.test(crm)) {
                alert("CRM deve ter entre 4 e 10 dígitos.");
                return false;
            }
            if (!horarioRegex.test(horario)) {
                alert("Horário deve estar no formato HH:MM.");
                return false;
            }
            if (!dataRegex.test(dataConsulta)) {
                alert("Data da Consulta deve estar no formato DD/MM/AAAA.");
                return false;
            }

            const [hours, minutes] = horario.split(':').map(Number);
            if (hours < 8 || hours > 18 || (minutes !== 0 && minutes !== 30)) {
                alert("Horário deve ser entre 08:00 e 18:00, em intervalos de 30 minutos.");
                return false;
            }

            return true;
        }
    </script>
</head>
<body>
    <header>
        <div class="header-container">
            <h1><fmt:message key="pagina.pacientec.header"/></h1>
            <a href="/home/listaConsultasPaciente" class="button"><fmt:message key="button.label.voltar"/></a>
        </div>
    </header>
    
    <form action="/home/mudaLinguagem" style="text-align:left">
        <select id="lang" name="lang" onchange="submit()">
            <option value="pt" ${lang == 'pt' ? 'selected' : ''}>Português</option>
            <option value="en" ${lang == 'en' ? 'selected' : ''}>English</option>
        </select>
    </form>
    
    <div class="container">
        <h2><fmt:message key="pagina.pacientec.agendar"/></h2>
        <form name="consultaForm" action="${home}" method="post" onsubmit="return validarFormulario()">
            <div class="form-group">
                <label for="CPF_Paciente"><fmt:message key="table.label.CPFPaciente"/>:</label>
                <input type="text" id="CPF_Paciente" name="CPF_Paciente" value="${CPF}" readonly required>
            </div>

            <div class="form-group">
                <label for="CRM_Medico"><fmt:message key="table.label.nomeMedico"/>:</label>
                <select id="CRM_Medico" name="CRM_Medico" required>
                    <c:forEach var="medico" items="${Medicos}">
                        <option value="${medico.CRM}">${medico.nome}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="Horario"><fmt:message key="table.label.horario"/>:</label>
                <input type="text" id="Horario" name="Horario" required>
            </div>

            <div class="form-group">
                <label for="DataConsulta"><fmt:message key="table.label.data"/>:</label>
                <input type="text" id="DataConsulta" name="DataConsulta" required>
            </div>
			
			<fmt:message key="pagina.pacientec.save" var="save"/>
            <div class="form-group">
                <input type="submit" value="${save}">
            </div>
        </form>

        <% 
            if (messageToShow != null) { 
                if (successMessage != null) {
        %>
            <div class="success-message"><%= messageToShow %></div>
        <% 
                } else {
        %>
            <div class="error-message"><%= messageToShow %></div>
        <% 
                }
            }
        %>
    </div>
</body>
</html>
