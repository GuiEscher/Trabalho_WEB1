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

@WebServlet(urlPatterns = "/minhasConsultasMedico")
public class listagemConsultasMedicoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static List<ConsultaMedico> consultas = new ArrayList<>();
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection con = PostgreeDBConfig.getConnection()) {
            // Primeira consulta: Obtém as consultas e CPFs dos pacientes
            String sql = "SELECT CRM_Medico, CPF_Paciente, Horario, DataConsulta FROM CONSULTA WHERE CRM_Medico = ?";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, request.getParameter("CRM_Medico")); // Assumindo que o CRM do médico é passado como parâmetro

                try (ResultSet rs = stmt.executeQuery()) {
                    consultas.clear();
                    List<String> cpfs = new ArrayList<>();
                    
                    while (rs.next()) {
                        // Cria um objeto ConsultaMedico para armazenar os dados
                        ConsultaMedico aux = new ConsultaMedico();
                        aux.setCRM_Medico(rs.getString("CRM_Medico"));
                        aux.setCPF_Paciente(rs.getString("CPF_Paciente"));
                        aux.setHorario(rs.getString("Horario"));
                        aux.setData(rs.getString("DataConsulta"));
                        
                        cpfs.add(rs.getString("CPF_Paciente"));
                        consultas.add(aux);
                    }

                    // Segunda consulta: Obtém os nomes dos pacientes
                    String sql2 = "SELECT CPF_Paciente, Nome FROM Paciente WHERE CPF_Paciente = ?";
                    
                    try (PreparedStatement stmt2 = con.prepareStatement(sql2)) {
                        for (ConsultaMedico consulta : consultas) {
                            String cpf = consulta.getCPF_Paciente();
                            stmt2.setString(1, cpf);
                            
                            try (ResultSet rs2 = stmt2.executeQuery()) {
                                if (rs2.next()) {
                                    String nomePaciente = rs2.getString("Nome");
                                    consulta.setNomePaciente(nomePaciente); // Supondo que você tenha um método setNomePaciente na classe ConsultaMedico
                                }
                            }
                        }
                    }
                }
                
                // Define os atributos da requisição e encaminha para o JSP
                request.setAttribute("Consultas", consultas);
                RequestDispatcher rd = request.getRequestDispatcher("paginaMedico.jsp");
                rd.forward(request, response);
                
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erro na listagem");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro na conexão com o banco de dados");
        }
    }
}