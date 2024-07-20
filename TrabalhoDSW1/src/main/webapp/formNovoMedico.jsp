<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url value="/cadastrarMedico" var="cadastro"/>

<!DOCTYPE html>
<html>

<head>

<meta charset="ISO-8859-1">

<title> form</title>
<body>

<form action="${ cadastro }" method="post">

	Nome: <input type="text" name="Nome">
	<br/>
	CRM: <input type="text" name="CRM">
	<br/>
	Email: <input type="text" name="Email">
	<br/>
	Senha: <input type="password" name="Senha">
	<br/>
	Especialidade: <input type="text" name="Especialidade">
	<br/>
	<input type="submit">

</form>
</body>
</head>
</html>