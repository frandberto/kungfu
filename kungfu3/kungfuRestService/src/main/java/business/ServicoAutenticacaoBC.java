package business;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import javax.inject.Inject;
import org.apache.commons.lang.StringUtils;
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

	/**
	 * Valida e troca a senha
	 * @param codigoUsuario código do usuário
	 * @param senhaAnterior senha anterior
	 * @param novaSenha nova senha
	 */
	public void trocarSenha(String codigoUsuario, String senhaAnterior, String novaSenha) {
		novaSenha = novaSenha.trim();
		validar(senhaAnterior, novaSenha);
		Usuario usuario = usuarioBC.buscarPorCodigo(codigoUsuario);
		String senhaCodificada = codificar(novaSenha);
		usuario.setSenha(senhaCodificada);
		usuarioBC.salvar(usuario);
	}

	// Regras de validação de senha
	private void validar(String senha, String novaSenha) {
		// TODO Auto-generated method stub

	}

	public String gerarNovaSenha(String idUsuario) {
		Usuario usuario = usuarioBC.buscarPorID(Long.valueOf(idUsuario));
		if (usuario != null) {
			String novaSenha = gerarSenha();
			String senhaCodificada = codificar(novaSenha);
			usuario.setSenha(senhaCodificada);
			salvarUsuario(usuario);
			return novaSenha;
		}
		return "";
	}

	private String gerarSenha() {
		double numeroDouble = 99 * Math.random();
		String numeroString = Long.toString(Math.round(numeroDouble));
		return "nova" + StringUtils.leftPad(numeroString, 2, "0");
	}

}