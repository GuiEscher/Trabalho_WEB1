package br.ufscar.dc.dsw;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/homepage")
public class ListagemMedicosServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static List<Medico> listaMedicos = new ArrayList<>();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection con = PostgreeDBConfig.getConnection()) {
            // Consulta no SQL
            String sql = "SELECT Nome, CRM, Especialidade FROM MEDICO";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                listaMedicos.clear(); 

                while (rs.next()) {
                    Medico aux = new Medico();

                    // Usar os valores do ResultSet
                    aux.setNome(rs.getString("Nome"));
                    aux.setCRM(rs.getString("CRM"));
                    aux.setEspecialidade(rs.getString("Especialidade"));
                    listaMedicos.add(aux);

                    System.out.println(aux.getNome());
                }
                System.out.println("Listagem bem sucedida");

                // Encaminha a requisição para a página JSP
                request.setAttribute("Medicos", listaMedicos);
                RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
                rd.forward(request, response);
            }
        } catch (SQLException e) {
            // Tratamento da excessão
            e.printStackTrace();
            System.out.println("Erro na listagem");
        }
    }
}
