package business;

import java.util.List;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.stereotype.Controller;
import entidade.Usuario;
import persistence.UsuarioDAO;

@Controller
public class UsuarioBC
{
  @Inject
  private UsuarioDAO usuarioDAO;
    
  public Usuario buscarPorID(Long idUsuario) {
	  return usuarioDAO.buscarPorID(idUsuario);
  }
  
  public List<Usuario> listarUsuarios() {
	  return usuarioDAO.listarTodos();
  }
  
}