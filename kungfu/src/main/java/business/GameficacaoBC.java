package business;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import persistence.GameficacaoDAO;
import br.gov.frameworkdemoiselle.stereotype.Controller;
import business.exception.NegocioException;
import entidade.Gameficacao;

@Controller
public class GameficacaoBC
{
  @Inject
  private GameficacaoDAO gameficacaoDAO;
  
  public List<Gameficacao> listar()
  {
    return this.gameficacaoDAO.listar();
  }
  
  public List<Gameficacao> listar(Long idPeriodo)
  {
    return this.gameficacaoDAO.listar(idPeriodo);
  }
  
  public void inserirEvento(String idEvento, String idUsuario, Date dataEvento, String observacao)
    throws NegocioException
  {}
}