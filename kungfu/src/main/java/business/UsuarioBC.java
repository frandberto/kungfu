package business;

import java.util.List;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.stereotype.Controller;
import br.gov.frameworkdemoiselle.transaction.Transactional;
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
  
  public Usuario buscarPorCodigo(String codigoUsuario) {
	  return usuarioDAO.obterUsuario(codigoUsuario);
  }
  
  public List<Usuario> listarUsuarios() {
	  return usuarioDAO.listar();
  }
  
  public List<Usuario> listarUsuariosSemAdmin() {
	  return usuarioDAO.listarSemAdmin();
  }

  @Transactional
  public void salvar(Usuario usuario) {
	usuarioDAO.update(usuario);	
  }
  
}