package dto;

import java.util.ArrayList;
import java.util.List;

public class PublicacaoDTO
{
  private String numero;
  private String versao;
  private List<String> datasPublicacao;
  
  public PublicacaoDTO()
  {
    this.datasPublicacao = new ArrayList();
  }
  
  public String getNumero()
  {
    return this.numero;
  }
  
  public void setNumero(String numero)
  {
    this.numero = numero;
  }
  
  public String getVersao()
  {
    return this.versao;
  }
  
  public void setVersao(String versao)
  {
    this.versao = versao;
  }
  
  public List<String> getDatasPublicacao()
  {
    return this.datasPublicacao;
  }
  
  public void setDatasPublicacao(List<String> datasPublicacao)
  {
    this.datasPublicacao = datasPublicacao;
  }
}
