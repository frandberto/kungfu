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
  
  public Gamificacao inserirGamificacao(Long idGamificacao, Long idPeriodo, Long idEvento, Long idUsuario, Date dataRegistro, String observacao)
    throws NegocioException  {
	  if (idGamificacao != null) {
	      return atualizar(idGamificacao, idPeriodo, 
	    		  idEvento, idUsuario, dataRegistro, observacao);
	  } else {
		  return inserir(idGamificacao, idPeriodo, 
				  idEvento, idUsuario, dataRegistro, observacao);
	  }
  }
  
  public void excluir(Long idEvento) {
	  gamificacaoDAO.delete(idEvento);
  }
  
  private Gamificacao atualizar(Long idGamificacao, Long idPeriodo, Long idEvento, Long idUsuario, Date dataRegistro, String observacao) {
	  Gamificacao gamificacao = gamificacaoDAO.buscarPorID(idGamificacao);
	  if (!gamificacao.getUsuario().getId().equals(idUsuario)) {
		  Usuario usuario = usuarioBC.buscarPorID(idUsuario);
		  gamificacao.setUsuario(usuario);
	  }
	  if (!gamificacao.getEvento().getId().equals(idEvento)) {
		  Evento evento = eventoBC.buscarPorID(idEvento);
		  gamificacao.setEvento(evento);
	  }
	  gamificacao.setDataRegistro(dataRegistro);
      gamificacao.setObservacao(observacao);
      return (Gamificacao) gamificacaoDAO.sincronizar(gamificacao);
  }
  
  private Gamificacao inserir(Long idGamificacao, Long idPeriodo, Long idEvento, Long idUsuario, Date dataRegistro, String observacao) {
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