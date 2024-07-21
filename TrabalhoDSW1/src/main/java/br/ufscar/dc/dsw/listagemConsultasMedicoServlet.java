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
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = "/minhasConsultasMedico")
public class listagemConsultasMedicoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static List<ConsultaMedico> consultas = new ArrayList<>();
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
    	String inputCRM  = (String) session.getAttribute("CRM");
    	
    	try (Connection con = PostgreeDBConfig.getConnection()) {
            // Primeira consulta: Obtém as consultas e CPFs dos pacientes
            String sql = "SELECT CRM_Medico, CPF_Paciente, Horario, DataConsulta FROM CONSULTA WHERE CRM_Medico LIKE '" + inputCRM + "'";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
            	System.out.println("Procurando consultas do medico...");

                try (ResultSet rs = stmt.executeQuery()) {
                    consultas.clear();
                    
                    
                    while (rs.next()) {
                    	System.out.println("Encontrou consulta...");
                    	System.out.println("Encontrou: " + rs.getString("CRM_Medico") + rs.getString("CPF_Paciente")+rs.getString("Horario")+rs.getString("DataConsulta"));
                        // Cria um objeto ConsultaMedico para armazenar os dados
                        ConsultaMedico aux = new ConsultaMedico();
                        aux.setCRM_Medico(rs.getString("CRM_Medico"));
                        aux.setCPF_Paciente(rs.getString("CPF_Paciente"));
                        aux.setHorario(rs.getString("Horario"));
                        aux.setDataConsulta(rs.getString("DataConsulta"));
                               
                        
                     // Segunda consulta: Obtém os nomes dos pacientes
                        String sql2 = "SELECT  Nome FROM Paciente WHERE CPF LIKE '" + rs.getString("CPF_Paciente") + "'";
                        
                        try (PreparedStatement stmt2 = con.prepareStatement(sql2)) {
                        		System.out.println("	Procurando nome do paciente...");
                                ResultSet rs_aux = stmt2.executeQuery();
                                
                                while (rs_aux.next()) {
                                	System.out.println("	Encontrou nome do paciente...");
                                	System.out.println("Encontrou" + rs_aux.getString("Nome"));
                                	aux.setNome_Paciente(rs_aux.getString("Nome"));
                                }
                        }
                        consultas.add(aux);
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