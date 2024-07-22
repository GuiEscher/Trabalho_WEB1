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

@WebServlet(urlPatterns = "/removePaciente")
public class RemovePacienteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cpf = request.getParameter("CPF");
        
        try (Connection con = PostgreeDBConfig.getConnection()) {
            String sql = "DELETE FROM PACIENTE WHERE CPF = ?";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, cpf);
                int rowsAffected = stmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    System.out.println("Paciente deletado com sucesso.");
                } else {
                    System.out.println("Nenhum paciente encontrado com o CPF especificado.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao deletar paciente.");
        }

        response.sendRedirect("/home/listagemPacientes");
    }
}
	

