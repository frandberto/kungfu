package dto;

import business.enumeration.Avatar;
import entidade.Usuario;

public class HistoricoRankingUsuarioDTO {
  private Usuario usuario;;
  
  private int qtdFaixaBranca;
  private int qtdFaixaAzul;
  private int qtdFaixaRoxa;
  private int qtdFaixaMarom;
  private int qtdFaixaPreta;
  
	public boolean equals(Object o) {
		if (o instanceof HistoricoRankingUsuarioDTO) {
			HistoricoRankingUsuarioDTO outroObjeto = (HistoricoRankingUsuarioDTO) o;
			if (outroObjeto.getUsuario().getId().equals(this.getUsuario().getId())) {
				return true;
			} else {
				return false;
			}
		}
		return false;

	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public int getQtdFaixaBranca() {
		return qtdFaixaBranca;
	}

	public void setQtdFaixaBranca(int qtdFaixaBranca) {
		this.qtdFaixaBranca = qtdFaixaBranca;
	}

	public int getQtdFaixaAzul() {
		return qtdFaixaAzul;
	}

	public void setQtdFaixaAzul(int qtdFaixaAzul) {
		this.qtdFaixaAzul = qtdFaixaAzul;
	}

	public int getQtdFaixaRoxa() {
		return qtdFaixaRoxa;
	}

	public void setQtdFaixaRoxa(int qtdFaixaRoxa) {
		this.qtdFaixaRoxa = qtdFaixaRoxa;
	}

	public int getQtdFaixaMarom() {
		return qtdFaixaMarom;
	}

	public void setQtdFaixaMarom(int qtdFaixaMarom) {
		this.qtdFaixaMarom = qtdFaixaMarom;
	}

	public int getQtdFaixaPreta() {
		return qtdFaixaPreta;
	}

	public void setQtdFaixaPreta(int qtdFaixaPreta) {
		this.qtdFaixaPreta = qtdFaixaPreta;
	}

	public void atribuirRanking(String avatar) {
		Avatar avatarNivel = Avatar.getAvatar(avatar);

		if (avatarNivel.equals(Avatar.FAIXA_BRANCA)) {
			qtdFaixaBranca++;
		}
		if (avatarNivel.equals(Avatar.FAIXA_AZUL)) {
			qtdFaixaAzul++;
		}
		if (avatarNivel.equals(Avatar.FAIXA_ROXA)) {
			qtdFaixaRoxa++;
		}
		if (avatarNivel.equals(Avatar.FAIXA_MARROM)) {
			qtdFaixaMarom++;
		}
		if (avatarNivel.equals(Avatar.FAIXA_PRETA)) {
			qtdFaixaPreta++;
		}
	}

}
