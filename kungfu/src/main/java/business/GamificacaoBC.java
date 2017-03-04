package business;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import persistence.GamificacaoDAO;
import br.gov.frameworkdemoiselle.stereotype.Controller;
import business.exception.NegocioException;
import entidade.Gamificacao;

@Controller
public class GamificacaoBC
{
  @Inject
  private GamificacaoDAO gamificacaoDAO;
  
  public List<Gamificacao> listar()
  {
    return this.gamificacaoDAO.listar();
  }
  
  public List<Gamificacao> listar(Long idPeriodo)
  {
    return this.gamificacaoDAO.listar(idPeriodo);
  }
  
  public void inserirEvento(String idEvento, String idUsuario, Date dataEvento, String observacao)
    throws NegocioException
  {}
}