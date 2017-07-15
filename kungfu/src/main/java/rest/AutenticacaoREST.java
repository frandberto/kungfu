package rest;

import java.net.URISyntaxException;
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
import business.UsuarioBC;
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
  public UsuarioLoginJSON logar(LoginJSON login)
    throws URISyntaxException {
    if ((!StringUtils.isEmpty(login.codigo)) && 
    		(!StringUtils.isEmpty(login.senha))) {
      
      this.log.info("Realizando login");
      
      UsuarioDTO usuarioDTO = autenticacaoBC.logar(login.codigo, login.senha);
      UsuarioLoginJSON usuarioJSON = criarUsuarioLoginJSON(usuarioDTO);
      return usuarioJSON;

    } else {
      this.log.info("Existe algum parametro nulo. Verifique os valores");
      return new UsuarioLoginJSON();
    }
  }
  
  
  private UsuarioLoginJSON criarUsuarioLoginJSON(UsuarioDTO usuarioDTO) {
	  UsuarioLoginJSON usuarioJSON = new UsuarioLoginJSON();
	  usuarioJSON.apelido = usuarioDTO.getApelido();
	  usuarioJSON.codigo = usuarioDTO.getCodigo();
	  usuarioJSON.token = usuarioDTO.getToken();
	  usuarioJSON.perfil = "admin";
	return usuarioJSON;
}


public static class LoginJSON
  {
	public String codigo;
	public String senha;
  }
  
  public static class UsuarioLoginJSON
  {
	public String codigo;
	public String apelido;
	public String token;
	public String perfil;
  }
  
}
