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

@WebServlet(urlPatterns = "/listagemPacientes")
public class ListagemPacientesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static List<Paciente> listaPacientes = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection con = PostgreeDBConfig.getConnection()) {
            String sql = "SELECT CPF, Nome, Email, Telefone, Sexo, DataNascimento FROM PACIENTE";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                listaPacientes.clear();
                System.out.println(listaPacientes);
                while (rs.next()) {
                    Paciente aux = new Paciente();
                    aux.setCPF(rs.getString("CPF"));
                    aux.setNome(rs.getString("Nome"));
                    aux.setEmail(rs.getString("Email"));
                    aux.setTelefone(rs.getString("Telefone"));
                    aux.setSexo(rs.getString("Sexo"));
                    aux.setData_nascimento(rs.getString("Sexo"));
                    listaPacientes.add(aux);
                }

                request.setAttribute("Pacientes", listaPacientes);
                RequestDispatcher rd = request.getRequestDispatcher("listagemPacientesAdm.jsp");
                rd.forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro na listagem de pacientes");
        }
    }
}
