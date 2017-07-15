package business;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.inject.Inject;

import dto.UsuarioDTO;
import entidade.Usuario;

public class ServicoAutenticacaoBC {
	
	@Inject
	private UsuarioBC usuarioBC;
	
	public UsuarioDTO logar(String codigo, String senha) {
		Usuario usuario = usuarioBC.buscarPorCodigo(codigo);
		
		if (usuario == null) {
			return null;
		}
		
		String senhaUsuario = usuario.getSenha();
		
		if (!senhaUsuario.equals(codificar(senha))) {
			return null;
		}
		
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuario.setToken(gerarToken(usuario.getCpf()));
		usuarioDTO.popular(usuario);
		salvarUsuario(usuario);
		return usuarioDTO;
	}
	
	public void logout(String codigo, String token) {
		Usuario usuario = usuarioBC.buscarPorCodigo(codigo);
		
		if (usuario == null) {
			return;
		}
		
		// Libera o token
		if (usuario.getToken().equals(token)) {
			usuario.setToken(null);
			usuario.setDataToken(null);
			usuarioBC.salvar(usuario);
		}		
	}
	
	private void salvarUsuario(Usuario usuario) {
		usuario.setDataToken(new Date());
		usuarioBC.salvar(usuario);		
	}

	private UsuarioDTO criarUsuarioDTO(Usuario usuario) {
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setApelido(usuario.getApelido());
		usuarioDTO.setEmail(usuario.getEmail());
		String token = gerarToken(usuario.getCpf());
		usuarioDTO.setToken(token);
		return usuarioDTO;
	}

	private String gerarToken(String codigo) {
		Date data = new Date();
		String semente = codigo+String.valueOf(data.getTime());
		return codificar(semente);
	}

	/**
	 * Codifica um codigo em string em MD5
	 * @param codigo a ser codificado
	 * @return código codificado
	 */
	private String codificar(String codigoOriginal) {
		String codigo = new String(codigoOriginal);
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			return codigo;
		}
		messageDigest.update(codigo.getBytes(),0,codigo.length());
		return new BigInteger(1,messageDigest.digest()).toString(16);
	}

}
