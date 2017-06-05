package dto;

import java.math.BigDecimal;

public class PontuacaoDTO {
  private Integer idUsuario;
  private String apelido;
  private BigDecimal pontuacao;
  private Integer idRanking;
  private String avatar;

  public Integer getIdUsuario() {
	  return idUsuario;
  }
  public void setIdUsuario(Integer idUsuario) {
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
  public Integer getIdRanking() {
	  return idRanking;
  }
  public void setIdRanking(Integer idRanking) {
	  this.idRanking = idRanking;
  }
public String getAvatar() {
	return avatar;
}
public void setAvatar(String avatar) {
	this.avatar = avatar;
}

}
