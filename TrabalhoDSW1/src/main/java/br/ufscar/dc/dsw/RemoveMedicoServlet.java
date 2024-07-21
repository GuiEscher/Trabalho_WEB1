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

@WebServlet(urlPatterns = "/removeMedico")
public class RemoveMedicoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String crm = request.getParameter("CRM");
        
        try (Connection con = PostgreeDBConfig.getConnection()) {
            String sql = "DELETE FROM MEDICO WHERE CRM = ?";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, crm);
                int rowsAffected = stmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    System.out.println("Medico deletado com sucesso.");
                } else {
                    System.out.println("Nenhum Medico encontrado com o CPF especificado.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao deletar Medico.");
        }

        response.sendRedirect("/home/listagemMedicos");
    }

}
