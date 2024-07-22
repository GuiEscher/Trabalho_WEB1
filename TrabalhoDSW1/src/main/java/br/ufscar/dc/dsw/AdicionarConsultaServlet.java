package br.ufscar.dc.dsw;

import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/agendarConsulta")
public class AdicionarConsultaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
    
        // Para listar os médicos na hora da consulta
        List<Medico> listaMedicos = new ArrayList<>();
        System.out.println("Buscando Medicos");

        try (Connection con = PostgreeDBConfig.getConnection()) {
            String sql = "SELECT CRM, Nome FROM MEDICO";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    // Busca o CRM para mandar pro post e nome para mostrar na listagem
                    Medico aux = new Medico();
                    aux.setCRM(rs.getString("CRM"));
                    aux.setNome(rs.getString("Nome"));
                    listaMedicos.add(aux);
                }
                
             // Depuração
                for (Medico medico : listaMedicos) {
                    System.out.println("CRM: " + medico.getCRM() + ", Nome: " + medico.getNome());
                }
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro na listagem de médicos");
        }

        request.setAttribute("Medicos", listaMedicos);
        RequestDispatcher rd = request.getRequestDispatcher("/formConsultas.jsp");
        rd.forward(request, response);
    }

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
            session.setAttribute("errorMessage", "Insira o seu CPF!");
            response.sendRedirect("/home/agendarConsulta");
            return;
        }
        if (!cpfPaciente.matches("\\d{11}")) {
            session.setAttribute("errorMessage", "CPF deve ter 11 dígitos numéricos.");
            response.sendRedirect("/home/agendarConsulta");
            return;
        }
        if (!crmMedico.matches("\\d{4,10}")) {
            session.setAttribute("errorMessage", "CRM deve ter entre 4 e 10 dígitos.");
            response.sendRedirect("/home/agendarConsulta");
            return;
        }
        if (!horario.matches("\\d{2}:\\d{2}")) {
            session.setAttribute("errorMessage", "Horário deve estar no formato HH:MM.");
            response.sendRedirect("/home/agendarConsulta");
            return;
        }
        if (!dataConsulta.matches("\\d{2}/\\d{2}/\\d{4}")) {
            session.setAttribute("errorMessage", "Data da Consulta deve estar no formato DD/MM/AAAA.");
            response.sendRedirect("/home/agendarConsulta");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            sdf.parse(dataConsulta);
        } catch (ParseException e) {
            session.setAttribute("errorMessage", "Data da Consulta inválida.");
            response.sendRedirect("/home/agendarConsulta");
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
                        session.setAttribute("errorMessage", "O horário inserido já está ocupado.");
                        response.sendRedirect("/home/agendarConsulta");
                        return;
                    }
                }
            }
        } catch (SQLException e) {
            session.setAttribute("errorMessage", "Erro ao verificar disponibilidade de horário.");
   
            response.sendRedirect("/home/agendarConsulta");
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
                    session.setAttribute("errorMessage", "Você já tem uma consulta nesse horário!");
                    response.sendRedirect("/home/agendarConsulta");
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
                        session.setAttribute("errorMessage", "Erro ao enviar e-mail. Tente novamente mais tarde.");
                        response.sendRedirect("/home/agendarConsulta");
                        System.out.println(e.getMessage());
                        return;
                    }
                }
            }
        } catch (SQLException e) {
            session.setAttribute("errorMessage", "Erro ao agendar consulta: Verifique se os dados inseridos existem!");
            response.sendRedirect("/home/agendarConsulta");
            return;
        }
        
        session.setAttribute("errorMessage", null);
        response.sendRedirect("/home/agendarConsulta");
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
