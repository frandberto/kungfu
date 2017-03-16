package business;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.stereotype.Controller;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import business.exception.NegocioException;
import entidade.Evento;
import entidade.Gamificacao;
import entidade.Periodo;
import entidade.Usuario;
import persistence.GamificacaoDAO;

@Controller
public class GamificacaoBC
{
  @Inject
  private GamificacaoDAO gamificacaoDAO;
  
  @Inject
  private PeriodoBC periodoBC;
  
  @Inject 
  private EventoBC eventoBC;
  
  @Inject
  private UsuarioBC usuarioBC;
  
  public List<Gamificacao> listar()
  {
    Date dataAtual = new Date();
    Periodo periodoAtual = periodoBC.obterPeriodo(dataAtual);
    return this.gamificacaoDAO.listar(periodoAtual.getId());
  }
  
  public List<Gamificacao> listar(Long idPeriodo)
  {
    return this.gamificacaoDAO.listar(idPeriodo);
  }
  
  public Gamificacao inserirEvento(Long idPeriodo, Long idEvento, Long idUsuario, Date dataRegistro, String observacao)
    throws NegocioException  {
	  Periodo periodo = periodoBC.buscarPorID(idPeriodo);
	  Evento evento = eventoBC.buscarPorID(idEvento);
	  Usuario usuario = usuarioBC.buscarPorID(idUsuario);	  
	  
	  Gamificacao gamificacao = new Gamificacao();
	  gamificacao.setDataRegistro(dataRegistro);
	  gamificacao.setEvento(evento);
	  gamificacao.setPeriodo(periodo);
	  gamificacao.setUsuario(usuario);
	  gamificacao.setObservacao(observacao);
	  return gamificacaoDAO.insert(gamificacao);
  }
}