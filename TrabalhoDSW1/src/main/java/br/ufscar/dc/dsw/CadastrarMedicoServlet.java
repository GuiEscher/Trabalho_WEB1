package br.ufscar.dc.dsw;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/cadastrarMedico")
public class CadastrarMedicoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("formNovoMedico.jsp");
        dispatcher.forward(request, response);
    }
  
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String Nome = request.getParameter("Nome");
        String CRM = request.getParameter("CRM");
        String Email = request.getParameter("Email");
        String Senha = request.getParameter("Senha");
        String Especialidade = request.getParameter("Especialidade");

        String errorMessage = null;

        try (Connection con = PostgreeDBConfig.getConnection()) {
            String sql = "INSERT INTO MEDICO (Nome, CRM, Email, Senha, Especialidade) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, Nome);
                stmt.setString(2, CRM);
                stmt.setString(3, Email);
                stmt.setString(4, Senha);
                stmt.setString(5, Especialidade);

                int rowsAffected = stmt.executeUpdate(); 
                if (rowsAffected > 0) {
                    System.out.println("Medico cadastrado com sucesso");
                    response.sendRedirect("/home/listagemMedicos"); 
                    return;
                } else {
                	System.out.println("Medico nao cadastrado");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            errorMessage = "Erro ao cadastrar médico: " + e.getMessage();
        }

        
        request.setAttribute("errorMessage", errorMessage);
        RequestDispatcher dispatcher = request.getRequestDispatcher("formNovoMedico.jsp");
        dispatcher.forward(request, response);
    }
}
