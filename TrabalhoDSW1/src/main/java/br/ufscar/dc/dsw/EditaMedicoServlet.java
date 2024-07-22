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

@WebServlet(urlPatterns = "/editaMedico")
public class EditaMedicoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    Medico med = new Medico();
	    String crm = request.getParameter("CRM");
	    System.out.println("CRM = " + crm);

	    try (Connection con = PostgreeDBConfig.getConnection()) {
	        String sql = "SELECT CRM, Nome, Email, Especialidade, Senha FROM MEDICO WHERE CRM = ?";
	        
	        try (PreparedStatement stmt = con.prepareStatement(sql)) {
	            stmt.setString(1, crm); // Usando parâmetro seguro
	            try (ResultSet rs = stmt.executeQuery()) {
	                if (rs.next()) { // Move para a primeira linha válida
	                    System.out.println("Medico encontrado");

	                    med.setCRM(rs.getString("CRM"));
	                    med.setNome(rs.getString("Nome"));
	                    med.setEmail(rs.getString("Email"));
	                    med.setEspecialidade(rs.getString("Especialidade"));
	                    med.setSenha(rs.getString("Senha"));

	                    request.setAttribute("medico", med); 
	                    RequestDispatcher rd = request.getRequestDispatcher("/formEditMedico.jsp");
	                    rd.forward(request, response);
	                } else {
	                    System.out.println("Medico não encontrado");
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Erro na listagem de medicos");
	        
	    }
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String crm = request.getParameter("CRM");
	    String nome = request.getParameter("Nome");
	    String email = request.getParameter("Email");
	    String senha = request.getParameter("Senha");
	    String especialidade = request.getParameter("Especialidade");
	    
	    String errorMessage = null;

	    try (Connection con = PostgreeDBConfig.getConnection()) {
	        String sql = "UPDATE MEDICO SET Nome = ?, Email = ?, Senha = ?, Especialidade = ? WHERE CRM = ?";
	        try (PreparedStatement stmt = con.prepareStatement(sql)) {
	            stmt.setString(1, nome);
	            stmt.setString(2, email);
	            stmt.setString(3, senha);
	            stmt.setString(4, especialidade);
	            stmt.setString(5, crm);
	            
	            int rowsUpdated = stmt.executeUpdate();
	            if (rowsUpdated > 0) {
	                System.out.println("Medico atualizado com sucesso");
	                response.sendRedirect("/home/listagemMedicos"); 
	                return;
	            } else {
	            	
	                System.out.println("Erro ao atualizar medico");
	            
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        errorMessage = "Erro ao atualizar medico: " + e.getMessage();
	        System.out.println("Erro ao atualizar medico");
	     
	    }
	    
	    request.setAttribute("errorMessage", errorMessage);
        RequestDispatcher dispatcher = request.getRequestDispatcher("formEditMedico.jsp");
        dispatcher.forward(request, response);
	}

}
