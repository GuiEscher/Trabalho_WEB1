package br.ufscar.dc.dsw;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/cadastrarPaciente")
public class CadastrarPacienteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String Nome = request.getParameter("Nome");
        String CPF = request.getParameter("CPF");
        String Telefone = request.getParameter("Telefone");
        String Email = request.getParameter("Email");
        String Senha = request.getParameter("Senha");
        String Sexo = request.getParameter("Sexo");
        String DataNascimento = request.getParameter("DataNascimento");

        try (Connection con = PostgreeDBConfig.getConnection()) {
            String sql = "INSERT INTO PACIENTE (Nome, CPF, Telefone, Email, Senha, Sexo, DataNascimento) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, Nome);
                stmt.setString(2, CPF);
                stmt.setString(3, Telefone);
                stmt.setString(4, Email);
                stmt.setString(5, Senha);
                stmt.setString(6, Sexo);
                stmt.setString(7, DataNascimento);
                stmt.executeUpdate(); // Execute the insert query
                System.out.println("Paciente cadastrado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Paciente nao cadastrado");
        }

        response.sendRedirect("formNovoPaciente.jsp");
    }
}
