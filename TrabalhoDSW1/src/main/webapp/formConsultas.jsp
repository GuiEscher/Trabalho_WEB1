<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Agendar Consulta</title>
</head>
<body>
    <h2>Agendar Consulta Médica</h2>
    <form action="agendarConsulta" method="post">
        <label for="CPF_Paciente">CPF do Paciente:</label>
        <input type="text" id="CPF_Paciente" name="CPF_Paciente" required><br>

        <label for="CRM_Medico">CRM do Médico:</label>
        <input type="text" id="CRM_Medico" name="CRM_Medico" required><br>

        <label for="Horario">Horário:</label>
        <input type="text" id="Horario" name="Horario" required><br>

        <label for="DataConsulta">Data da Consulta:</label>
        <input type="text" id="DataConsulta" name="DataConsulta" required><br>

        <input type="submit" value="Agendar Consulta">
    </form>
</body>
</html>
