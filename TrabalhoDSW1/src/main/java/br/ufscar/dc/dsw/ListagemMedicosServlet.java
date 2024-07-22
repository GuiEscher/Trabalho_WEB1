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
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = "/homepage")
public class ListagemMedicosServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static List<Medico> listaMedicos = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
        String especialidadeFiltro = request.getParameter("especialidade");
        if (especialidadeFiltro == null) {
            especialidadeFiltro = "";
        } else {
            especialidadeFiltro = especialidadeFiltro.toLowerCase(); // Converte para minúsculas
        }
        
        try (Connection con = PostgreeDBConfig.getConnection()) {
            // Consulta SQL usando LOWER para garantir comparação case-insensitive nas especialidades
            String sql = "SELECT Nome, CRM, Email, Senha, Especialidade FROM MEDICO WHERE LOWER(Especialidade) LIKE LOWER(?)";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, "%" + especialidadeFiltro + "%"); 
                ResultSet rs = stmt.executeQuery();
                listaMedicos.clear();

                while (rs.next()) {
                	// Cria um objeto medico para armazenar os dados e colocar na lista que será enviada para o frontend
                    Medico aux = new Medico();
                    aux.setNome(rs.getString("Nome"));
                    aux.setCRM(rs.getString("CRM"));
                    aux.setEmail(rs.getString("Email"));
                    aux.setSenha(rs.getString("Senha"));
                    aux.setEspecialidade(rs.getString("Especialidade"));
                    listaMedicos.add(aux);
                }

                request.setAttribute("Medicos", listaMedicos);
                RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
                rd.forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro na listagem");
        }
    }
}
