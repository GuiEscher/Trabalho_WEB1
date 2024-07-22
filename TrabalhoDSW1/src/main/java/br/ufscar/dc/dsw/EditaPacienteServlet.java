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

@WebServlet(urlPatterns = "/editaPaciente")
public class EditaPacienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    Paciente pac = new Paciente();
	    String cpf = request.getParameter("CPF");
	    System.out.println("CPF = " + cpf);

	    try (Connection con = PostgreeDBConfig.getConnection()) {
	        String sql = "SELECT CPF, Nome, Email, Telefone, Senha, DataNascimento, Sexo FROM PACIENTE WHERE CPF = ?";
	        
	        try (PreparedStatement stmt = con.prepareStatement(sql)) {
	            stmt.setString(1, cpf); // Usando parâmetro seguro
	            try (ResultSet rs = stmt.executeQuery()) {
	                if (rs.next()) { // Move para a primeira linha válida
	                    System.out.println("paciente encontrado");

	                    pac.setCPF(rs.getString("CPF"));
	                    pac.setNome(rs.getString("Nome"));
	                    pac.setEmail(rs.getString("Email"));
	                    pac.setSexo(rs.getString("Sexo"));
	                    pac.setTelefone(rs.getString("Telefone"));
	                    pac.setData_nascimento(rs.getString("DataNascimento"));
	                    pac.setSenha(rs.getString("Senha"));
	                    
	                    // Envia para o form para preencher com os valores ja do banco
	                    request.setAttribute("paciente", pac); 
	                    RequestDispatcher rd = request.getRequestDispatcher("/formEditPaciente.jsp");
	                    rd.forward(request, response);
	                } else {
	                    System.out.println("Paciente não encontrado");
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("Erro na listagem de pacientes");
	        
	    }
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String cpf = request.getParameter("CPF");
	    String nome = request.getParameter("Nome");
	    String email = request.getParameter("Email");
	    String senha = request.getParameter("Senha");
	    String telefone = request.getParameter("Telefone");
	    String sexo = request.getParameter("Sexo");
	    String datanascimento = request.getParameter("DataNascimento");
	    
	    String errorMessage = null;

	    // Verifica se o CPF é nulo ou vazio
	    if (cpf == null || cpf.isEmpty()) {
	    	System.out.println("CPF é obrigatório");
	        return;
	    }

	    try (Connection con = PostgreeDBConfig.getConnection()) {
	        String sql = "UPDATE PACIENTE SET Nome = ?, Email = ?, Senha = ?, Telefone = ?, Sexo = ?, DataNascimento = ? WHERE CPF = ?";
	        try (PreparedStatement stmt = con.prepareStatement(sql)) {
	            stmt.setString(1, nome);
	            stmt.setString(2, email);
	            stmt.setString(3, senha);
	            stmt.setString(4, telefone);
	            stmt.setString(5, sexo);
	            stmt.setString(6, datanascimento);
	            stmt.setString(7, cpf);

	            int rowsUpdated = stmt.executeUpdate();
	            if (rowsUpdated > 0) {
	                System.out.println("Paciente atualizado com sucesso");
	                response.sendRedirect("/home/listagemPacientes"); 
	                return;
	            } else {
	                System.out.println("Erro ao atualizar paciente: Nenhum paciente encontrado com o CPF fornecido");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        errorMessage = "Erro ao cadastrar médico: " + e.getMessage();
	        System.out.println("Erro ao atualizar paciente");
	    }
	    
	    request.setAttribute("errorMessage", errorMessage);
        RequestDispatcher dispatcher = request.getRequestDispatcher("formEditPaciente.jsp");
        dispatcher.forward(request, response);
	}


}
