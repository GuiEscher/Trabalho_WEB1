package br.ufscar.dc.dsw;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		System.out.println("Iniciando login...");
		String emailInput = request.getParameter("email");
		String senhaInput = request.getParameter("senha");
		
		
		Boolean foundPaciente = false;
		Boolean foundMedico = false;
		Boolean foundAdmin = false;
		
		String id = null;
		RequestDispatcher rd;
		
		//Procura email em paciente, medico e admin
		try (Connection con = PostgreeDBConfig.getConnection()){
			String sql_paciente = "SELECT Email, Senha, CPF FROM PACIENTE";
			String sql_medico = "SELECT Email, Senha, CRM FROM MEDICO";
			String sql_admin = "SELECT Email, Senha, ADM_KEY FROM ADMIN";
			
		// Procurando em admin
			try (PreparedStatement stmt_a = con.prepareStatement(sql_admin)) {
				System.out.println("Procurando login em admin...");
                ResultSet rs_a = stmt_a.executeQuery();

                while (rs_a.next() && foundAdmin==false) {                	
                	if (emailInput.equals(rs_a.getString("Email")) && senhaInput.equals(rs_a.getString("Senha"))) {
                		foundAdmin = true;
                		id = rs_a.getString("ADM_KEY");
                		System.out.println("Encontrou login de admin.");
                	}
                }
			}
			               

			// Procurando em pacientes
			try (PreparedStatement stmt_p = con.prepareStatement(sql_paciente)) {
				System.out.println("Procurando login em pacientes...");
                ResultSet rs_p = stmt_p.executeQuery();

                while (rs_p.next() && foundPaciente==false) {                	
                	if (emailInput.equals(rs_p.getString("Email")) && senhaInput.equals(rs_p.getString("Senha"))) {
                		foundPaciente = true;
                		id = rs_p.getString("CPF");
                		System.out.println("Encontrou login de paciente.");
                	}
                }
			}
                
			// Procurando em medicos
			try (PreparedStatement stmt_m = con.prepareStatement(sql_medico)) {
				System.out.println("Procurando login em medicos...");
                ResultSet rs_m = stmt_m.executeQuery();

                while (rs_m.next() && foundMedico==false) {
                	if (emailInput.equals(rs_m.getString("Email")) && senhaInput.equals(rs_m.getString("Senha"))) {
                		foundMedico = true;
                		id = rs_m.getString("CRM");
                		System.out.println("Encontrou login de medico.");
                	}
                }
            } 
		} catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro na busca");
        }
		
		if (foundAdmin == true) {
	      	  session.setAttribute("ADM_KEY", id);
	      	  response.sendRedirect("/home/listagemPacientes");
	    } else if (foundPaciente == true) {
      	  session.setAttribute("CPF", id);
      	  response.sendRedirect("/home/listaConsultasPaciente");
        } else if (foundMedico == true) {
            session.setAttribute("CRM", id);
          response.sendRedirect("/home/minhasConsultasMedico");
        } else {
        	// nao encontrou login
        	System.out.println("Login não encontrado.");
            response.sendRedirect("paginaLogin.jsp?errorCode=1");
            return;
        }
	}	
}