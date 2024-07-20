package br.ufscar.dc.dsw;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet(urlPatterns = "/agendarConsulta")
public class AgendarConsultaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cpfPaciente = request.getParameter("CPF_Paciente");
        String crmMedico = request.getParameter("CRM_Medico");
        String horario = request.getParameter("Horario");
        String dataConsulta = request.getParameter("DataConsulta");

        // Verificar se os dados são válidos
        if (!cpfPaciente.matches("\\d{11}")) {
            request.setAttribute("errorMessage", "CPF deve ter 11 dígitos numéricos.");
            request.getRequestDispatcher("formConsultas.jsp").forward(request, response);
            return;
        }
        if (!crmMedico.matches("\\d{4,10}")) {
            request.setAttribute("errorMessage", "CRM deve ter entre 4 e 10 dígitos.");
            request.getRequestDispatcher("formConsultas.jsp").forward(request, response);
            return;
        }
        if (!horario.matches("\\d{2}:\\d{2}")) {
            request.setAttribute("errorMessage", "Horário deve estar no formato HH:MM.");
            request.getRequestDispatcher("formConsultas.jsp").forward(request, response);
            return;
        }
        if (!dataConsulta.matches("\\d{2}/\\d{2}/\\d{4}")) {
            request.setAttribute("errorMessage", "Data da Consulta deve estar no formato DD/MM/AAAA.");
            request.getRequestDispatcher("formConsultas.jsp").forward(request, response);
            return;
        }

        // Converter DataConsulta para o formato correto
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            sdf.parse(dataConsulta);
        } catch (ParseException e) {
            request.setAttribute("errorMessage", "Data da Consulta inválida.");
            request.getRequestDispatcher("formConsultas.jsp").forward(request, response);
            return;
        }

        // Tentar agendar a consulta no banco de dados
        try (Connection con = PostgreeDBConfig.getConnection()) {
            String sql = "INSERT INTO CONSULTA (CPF_Paciente, CRM_Medico, Horario, DataConsulta) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, cpfPaciente);
                stmt.setString(2, crmMedico);
                stmt.setString(3, horario);
                stmt.setString(4, dataConsulta);
                stmt.executeUpdate(); // Execute the insert query
                System.out.println("Consulta agendada");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erro ao agendar consulta: " + "Verifique se os dados inseridos existem!");
            request.getRequestDispatcher("formConsultas.jsp").forward(request, response);
            return;
        }

        response.sendRedirect("formConsultas.jsp?success=true");
    }
}
