<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:url value="/login" var="linkServletLogin"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    
</head>

<body>

    <header>
        <div class="header-container">
            <h1>Consultas.com</h1>
            <a href="/home/homepage" class="button">Homepage</a>
        </div>
    </header>

    <div class="form-container">
    	<h2>Login</h2>
        <form action="${linkServletLogin}" method="post">
            Email: <input type="text" name="email"/>
            Senha: <input type="text" name="senha"/>
            <input type="submit" value="Login"/>
        </form>
        
        <%
            String errorCode = (String) request.getParameter("errorCode");
            if (errorCode != null) {
                if (errorCode.equals("1")) {
        %>
            <div class="error-message">Email ou senha não encontrados.</div>
        <%
                }
            }
        %>
    </div>
    
    
<style>
        @import url('https://fonts.googleapis.com/css2?family=Ubuntu:wght@400;700&display=swap');

        body {
            font-family: 'Ubuntu', sans-serif;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
            background-color: #f5f5f5;
        }

        header {
            background-color: #474284; 
            color: #fff;
            padding: 10px 0;
            text-align: center;
            width: 100%;
            position: fixed;
            top: 0;
            left: 0;
            z-index: 1000;
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

        .form-container {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-top: 80px; 
            width: 100%;
            max-width: 400px;
            background-color: #fff;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        .form-container input[type="text"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            margin-bottom: 10px;
            box-sizing: border-box; 
        }

        .form-container input[type="submit"] {
            padding: 10px 20px;
            border: 1px solid #003366; 
            background-color: #003366; 
            color: #fff;
            border-radius: 5px;
            cursor: pointer;
        }

        .form-container input[type="submit"]:hover {
            background-color: #002244;
        }

        .error-message {
            color: red;
            text-align: center;
            margin-top: 20px;
        }
    </style>

</body>
</html>
