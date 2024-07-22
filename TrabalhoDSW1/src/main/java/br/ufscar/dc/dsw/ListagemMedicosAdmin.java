package br.ufscar.dc.dsw;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/listagemMedicos")
public class ListagemMedicosAdmin extends HttpServlet {
private static final long serialVersionUID = 1L;
    
    private static List<Medico> listaMedicos = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection con = PostgreeDBConfig.getConnection()) {
            String sql = "SELECT CRM, Nome, Email, Especialidade FROM MEDICO";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                listaMedicos.clear();
                System.out.println(listaMedicos);
                while (rs.next()) {
                    Medico aux = new Medico();
                    aux.setCRM(rs.getString("CRM"));
                    aux.setNome(rs.getString("Nome"));
                    aux.setEmail(rs.getString("Email"));
                    aux.setEspecialidade(rs.getString("Especialidade"));
                    listaMedicos.add(aux);
                }

                request.setAttribute("Medicos", listaMedicos);
                RequestDispatcher rd = request.getRequestDispatcher("/listagemMedicosAdm.jsp");
                rd.forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro na listagem de medicos");
        }
    }

}
