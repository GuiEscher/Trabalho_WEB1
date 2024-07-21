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

@WebServlet(urlPatterns = "/agendarConsulta")
public class AgendarConsultaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cpfPaciente = request.getParameter("CPF_Paciente");
        String crmMedico = request.getParameter("CRM_Medico");
        String horario = request.getParameter("Horario");
        String dataConsulta = request.getParameter("DataConsulta");

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
            System.out.println("Erro ao agendar consulta: " + e.getMessage());
        }

        response.sendRedirect("formConsultas.jsp");
    }
}
