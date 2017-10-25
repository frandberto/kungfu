package rest;

import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.StringUtils;

import business.ServicoAutenticacaoBC;
import dto.UsuarioDTO;

@Path("kungfu-leader")
public class AutenticacaoREST
{
  @Context
  private UriInfo uri;
  
  @Inject
  private Logger log;
  
  @Inject
  ServicoAutenticacaoBC autenticacaoBC;
  
  
  @POST
  @Path("login")
  @Consumes({"application/json"})
  @Produces({"application/json"})
  public UsuarioLoginJSON logar(LoginJSON login) {
	  UsuarioLoginJSON usuarioJSON = new UsuarioLoginJSON();
	  if ((!StringUtils.isEmpty(login.codigo)) &&
			  (!StringUtils.isEmpty(login.senha))) {

		  this.log.info("Realizando login");

		  UsuarioDTO usuarioDTO = autenticacaoBC.logar(login.codigo, login.senha);
		  
		  if (!StringUtils.isEmpty(login.novaSenha)) {
			  autenticacaoBC.trocarSenha(login.codigo, login.senha, login.novaSenha);
		  }

		  if (usuarioDTO != null) {
			  usuarioJSON = criarUsuarioLoginJSON(usuarioDTO);
		  } 
	  }
	  return usuarioJSON;
  }
  
  @POST
  @Path("gerarNovaSenha")
  @Consumes({"application/json"})
  @Produces({"application/json"})
  public LoginJSON gerarNovaSenha(LoginJSON login) {	  	  
	  if (!StringUtils.isEmpty(login.codigo)) {           
		  String novaSenha = this.autenticacaoBC.gerarNovaSenha(login.codigo);
		  login.novaSenha = novaSenha;
	  }
	  return login;
  }
  
  private UsuarioLoginJSON criarUsuarioLoginJSON(UsuarioDTO usuarioDTO) {
	  UsuarioLoginJSON usuarioJSON = new UsuarioLoginJSON();
	  usuarioJSON.apelido = usuarioDTO.getApelido();
	  usuarioJSON.codigo = usuarioDTO.getCodigo();
	  usuarioJSON.token = usuarioDTO.getToken();
	  usuarioJSON.perfil = usuarioDTO.getPerfil().getSigla();
	return usuarioJSON;
}


public static class LoginJSON
  {
	public String codigo;
	public String senha;
	public String novaSenha;
  }
  
  public static class UsuarioLoginJSON
  {
	public String codigo;
	public String apelido;
	public String token;
	// Sigla do perfil (admin ou user)
	public String perfil;
  }
  
}
