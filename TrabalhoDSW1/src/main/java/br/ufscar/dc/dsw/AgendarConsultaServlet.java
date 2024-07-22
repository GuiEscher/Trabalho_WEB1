package br.ufscar.dc.dsw;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet(urlPatterns = "/agendarConsulta")
public class AgendarConsultaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String cpfPaciente = request.getParameter("CPF_Paciente");
        String crmMedico = request.getParameter("CRM_Medico");
        String horario = request.getParameter("Horario");
        String dataConsulta = request.getParameter("DataConsulta");
        String cpfUsuario = (String) session.getAttribute("CPF");
        System.out.println(cpfPaciente + " Cpf ");
        System.out.println(cpfUsuario + " Cpf que veio");
        if (cpfUsuario == null || !cpfPaciente.equals(cpfUsuario)) {
            request.setAttribute("errorMessage", "Insira o seu CPF!");
            request.getRequestDispatcher("formConsultas.jsp").forward(request, response);
            return;
        }
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

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            sdf.parse(dataConsulta);
        } catch (ParseException e) {
            request.setAttribute("errorMessage", "Data da Consulta inválida.");
            request.getRequestDispatcher("formConsultas.jsp").forward(request, response);
            return;
        }

        try (Connection con = PostgreeDBConfig.getConnection()) {
            String sql = "SELECT COUNT(*) FROM CONSULTA WHERE CRM_Medico = ? AND Horario = ? AND DataConsulta = ?";
            try (PreparedStatement stmtVerifica = con.prepareStatement(sql)) {
                stmtVerifica.setString(1, crmMedico);
                stmtVerifica.setString(2, horario);
                stmtVerifica.setString(3, dataConsulta);
                
                try (ResultSet rs = stmtVerifica.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        request.setAttribute("errorMessage", "O horário inserido já está ocupado.");
                        request.getRequestDispatcher("formConsultas.jsp").forward(request, response);
                        return;
                    }
                }
            }
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Erro ao verificar disponibilidade de horário.");
            request.getRequestDispatcher("formConsultas.jsp").forward(request, response);
            return;
        }

        try (Connection con = PostgreeDBConfig.getConnection()) {
            String sqlConf = "SELECT COUNT(*) FROM CONSULTA WHERE CPF_Paciente = ? AND Horario = ? AND DataConsulta = ?";
            try (PreparedStatement stmt1 = con.prepareStatement(sqlConf)) {
                stmt1.setString(1, cpfPaciente);
                stmt1.setString(2, horario);
                stmt1.setString(3, dataConsulta);
                
                ResultSet count = stmt1.executeQuery();
                if (count.next() && count.getInt(1) > 0) {
                    request.setAttribute("errorMessage", "Você já tem uma consulta nesse horário!");
                    request.getRequestDispatcher("formConsultas.jsp").forward(request, response);
                    return;
                } else {
                    String sql = "INSERT INTO CONSULTA (CPF_Paciente, CRM_Medico, Horario, DataConsulta) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement stmt = con.prepareStatement(sql)) {
                        stmt.setString(1, cpfPaciente);
                        stmt.setString(2, crmMedico);
                        stmt.setString(3, horario);
                        stmt.setString(4, dataConsulta);
                        stmt.executeUpdate(); 
                        
                        String pacienteEmail = getEmailByCPF(con, cpfPaciente);
                        String medicoEmail = getEmailByCRM(con, crmMedico);
                        
                        String subject = "Consulta agendada";
                        String bodyPaciente = String.format("Olá, você agendou uma consulta com CRM %s no dia %s às %s.", crmMedico, dataConsulta, horario);
                        String bodyMedico = String.format("Olá, você tem uma consulta agendada com o paciente CPF %s no dia %s às %s.", cpfPaciente, dataConsulta, horario);
                        
                        if (pacienteEmail != null) {
                            EmailUtil.sendEmail(pacienteEmail, subject, bodyPaciente);
                            System.out.println("Email enviado ao paciente");
                        }
                        if (medicoEmail != null) {
                            EmailUtil.sendEmail(medicoEmail, subject, bodyMedico);
                            System.out.println("Email enviado ao Medico");
                        }
                    } catch (MessagingException e) {
                        request.setAttribute("errorMessage", "Erro ao enviar e-mail. Tente novamente mais tarde.");
                        request.getRequestDispatcher("formConsultas.jsp").forward(request, response);
                        System.out.println(e.getMessage());
                        return;
                    }
                }
            }
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Erro ao agendar consulta: Verifique se os dados inseridos existem!");
            request.getRequestDispatcher("formConsultas.jsp").forward(request, response);
            return;
        }

        response.sendRedirect("formConsultas.jsp?success=true");
    }
    
    private String getEmailByCPF(Connection con, String cpfPaciente) throws SQLException {
        String sql = "SELECT Email FROM PACIENTE WHERE CPF = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, cpfPaciente);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Email");
                }
            }
        }
        return null;
    }

    private String getEmailByCRM(Connection con, String crmMedico) throws SQLException {
        String sql = "SELECT Email FROM MEDICO WHERE CRM = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, crmMedico);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Email");
                }
            }
        }
        return null;
    }
}
