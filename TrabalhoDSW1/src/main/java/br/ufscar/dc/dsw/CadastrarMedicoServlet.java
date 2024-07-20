package br.ufscar.dc.dsw;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/cadastrarMedico")
public class CadastrarMedicoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


	        String Nome = request.getParameter("Nome");
	        String CRM = request.getParameter("CRM");
	        String Email = request.getParameter("Email");
	        String Senha = request.getParameter("Senha");
	        String Especialidade = request.getParameter("Especialidade");

	        try (Connection con = PostgreeDBConfig.getConnection()) {
	            String sql = "INSERT INTO MEDICO (Nome, CRM, Email, Senha, Especialidade) VALUES (?, ?, ?, ?, ?)";
	            try (PreparedStatement stmt = con.prepareStatement(sql)) {
	                stmt.setString(1, Nome);
	                stmt.setString(2, CRM);
	                stmt.setString(3, Email);
	                stmt.setString(4, Senha);
	                stmt.setString(5, Especialidade);

	                stmt.executeUpdate(); // Execute the insert query
	                System.out.println("Medico cadastrado");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            System.out.println("Medico nao cadastrado");
	        }

	        response.sendRedirect("formNovoMedico.jsp");
	    }
	}


