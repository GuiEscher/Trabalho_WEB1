package br.ufscar.dc.dsw;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = "/logout")

// Faz o logout do usuario
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		System.out.println("Iniciando logout...");
		if (session != null) {
			session.invalidate();
			System.out.println("	Invalidado!");
			response.sendRedirect("homepage");
			System.out.println("	Logout concluído!");
		}
	}
}
