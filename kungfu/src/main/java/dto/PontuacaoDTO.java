package dto;

import java.math.BigDecimal;

public class PontuacaoDTO {
  private Long idUsuario;
  private String apelido;
  private BigDecimal pontuacao;
  private Long idRanking;
  private String avatar;

  public Long getIdUsuario() {
	  return idUsuario;
  }
  public void setIdUsuario(Long idUsuario) {
	  this.idUsuario = idUsuario;
  }
  public String getApelido() {
	  return apelido;
  }
  public void setApelido(String apelido) {
	  this.apelido = apelido;
  }
  public BigDecimal getPontuacao() {
	  return pontuacao;
  }
  public void setPontuacao(BigDecimal pontuacao) {
	  this.pontuacao = pontuacao;
  }
  public Long getIdRanking() {
	  return idRanking;
  }
  public void setIdRanking(Long idRanking) {
	  this.idRanking = idRanking;
  }
  public String getAvatar() {
	  return avatar;
  }
  public void setAvatar(String avatar) {
	  this.avatar = avatar;
  }

  public boolean equals(Object o) {
	  if (o instanceof PontuacaoDTO) {
		  PontuacaoDTO outroObjeto = (PontuacaoDTO)o;
		  if (outroObjeto.getIdUsuario().equals(this.getIdUsuario())) {
			  return true;
		  } else {
			  return false;
		  }
	  }
	  return false;

  }

}
