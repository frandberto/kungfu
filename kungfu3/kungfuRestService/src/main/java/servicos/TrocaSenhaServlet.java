package servicos;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TrocaSenhaServlet
 */
@WebServlet("trocaSenha")
public class TrocaSenhaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TrocaSenhaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String codigo = request.getParameter("codigo");
		String token = request.getParameter("token");
		if (!codigo.isEmpty())  {
			  if (token.isEmpty()) {
				  validarCodigo(codigo);
			  } else {
				  validarToken(codigo, token);
			  }
		}
	}

	/**
	 * Valida o usuário e o token. Caso validado gerar nova senha e envia por e-mail.
	 * @param token
	 */
	private void validarToken(String codigo, String token) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Valida se  o usuário existe. Caso positivo, gerar token de confirmação para
	 * envio por e-mail.
	 * @param codigo
	 */
	private void validarCodigo(String codigo) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
