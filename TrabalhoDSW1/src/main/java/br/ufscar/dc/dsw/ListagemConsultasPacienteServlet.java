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

@WebServlet(urlPatterns = "/listaConsultasPaciente")
public class ListagemConsultasPacienteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static List<ConsultaPaciente> listaConsultas = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	String inputCPF = (String) session.getAttribute("CPF");
    	
        try (Connection con = PostgreeDBConfig.getConnection()) {
            String sql = "SELECT CPF_Paciente, CRM_Medico, DataConsulta, Horario "
            		+ "FROM CONSULTA WHERE CPF_Paciente LIKE '" + inputCPF + "'";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
            	System.out.println("Procurando consultas do paciente...");
                ResultSet rs = stmt.executeQuery();
                listaConsultas.clear();

                while (rs.next()) {
                	// Cria um objeto consulta para armazenar os dados e colocar na lista que será enviada para o frontend
                	System.out.println("Encontrou paciente...");
                    ConsultaPaciente aux = new ConsultaPaciente();
                    aux.setCPF_Paciente(rs.getString("CPF_Paciente"));
                    aux.setCRM_Medico(rs.getString("CRM_Medico"));
                    aux.setDataConsulta(rs.getString("DataConsulta"));
                    aux.setHorario(rs.getString("Horario"));
                    
                    String sql_aux = "SELECT Nome FROM MEDICO WHERE CRM LIKE '" + rs.getString("CRM_Medico") + "'";
                    try (PreparedStatement stmt_aux = con.prepareStatement(sql_aux)){
						System.out.println("	Procurando nome do medico...");
                    	ResultSet rs_aux = stmt_aux.executeQuery();
                    	while (rs_aux.next()){
							System.out.println("		Encontrou nome do medico...");
							// Chave unica
							aux.setNome_Medico(rs_aux.getString("Nome"));
						}
                    	
                    }
                    
                   // aux.setNome_Medico();
                    listaConsultas.add(aux);
                }

                request.setAttribute("Consultas", listaConsultas);
                RequestDispatcher rd = request.getRequestDispatcher("paginaPaciente.jsp");
                rd.forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro na listagem");
        }
    }
}
