<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url value="/cadastrarPaciente" var="home"/>

<!DOCTYPE html>
<html>

<head>

<meta charset="ISO-8859-1">

<title> form</title>
<body>

<form action="${ home }" method="post">

	Nome: <input type="text" name="Nome">
	<br/>
	CPF: <input type="text" name="CPF">
	<br/>
	Telefone: <input type="text" name="Telefone">
	<br/>
	Email: <input type="text" name="Email">
	<br/>
	Senha: <input type="password" name="Senha">
	<br/>
	Sexo: <input type="text" name="Sexo">
	<br/>
	Data Nascimento: <input type="text" name="DataNascimento">
	<br/>
	<input type="submit">

</form>
</body>
</head>
</html>