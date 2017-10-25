package dto;

import business.enumeration.Perfil;
import entidade.Usuario;

public class UsuarioDTO {

	private String codigo;
	private String apelido;
	private String email;
	private String token;
	private Perfil perfil;
	
	public String getApelido() {
		return apelido;
	}
	public void setApelido(String apelido) {
		this.apelido = apelido;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public void popular(Usuario usuario) {
		this.apelido = usuario.getApelido();
		this.codigo = usuario.getCpf();
		this.token = usuario.getToken();
		this.perfil =  usuario.getPerfil();
	}
	public Perfil getPerfil() {
		return perfil;
	}
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
}
