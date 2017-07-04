package business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.stereotype.Controller;
import business.exception.NegocioException;
import dto.PontuacaoDTO;
import entidade.Evento;
import entidade.Gamificacao;
import entidade.Periodo;
import entidade.Ranking;
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
  
  @Inject
  private RankingBC rankingBC;
  
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

  public List<PontuacaoDTO> listarPontuacoes(Long idPeriodo) {
	  List<PontuacaoDTO> lstPontuacoes = new ArrayList<PontuacaoDTO>();
	  List<Object[]> lstResultado = gamificacaoDAO.listarPontuacoes(idPeriodo);
	  for (Object[] objeto: lstResultado) {
		  PontuacaoDTO dto = new PontuacaoDTO();
		  // TODO verificar porque está retornando Integer
		  Integer idUsuario = (Integer) objeto[0];
		  dto.setIdUsuario(idUsuario.longValue());
		  dto.setApelido((String)objeto[1]);
		  dto.setPontuacao((BigDecimal)objeto[2]);
		  Ranking raking = rankingBC.obterRanking(dto.getPontuacao());
		  dto.setIdRanking(raking.getId());
		  dto.setAvatar(raking.getAvatar());
		  lstPontuacoes.add(dto);
	  }
	  return lstPontuacoes;
  }
  
  /**
   * Lista pontuacao no período indicado para o usuário informado
   * @param idUsuario
   * @param idPeriodo
   * @return
   */
  public PontuacaoDTO obterPontuacao(Long idUsuario, Long idPeriodo) {
	  	  
	  List<Object[]> lstResultado = gamificacaoDAO.listarPontuacoes(idUsuario, idPeriodo);
	  PontuacaoDTO dto = new PontuacaoDTO();
	 // TODO verificar porque está retornando Integer
	  dto.setIdUsuario(idUsuario.longValue());
	  for (Object[] objeto: lstResultado) {		  
		  dto.setApelido((String)objeto[1]);
		  dto.setPontuacao((BigDecimal)objeto[2]);
		  Ranking raking = rankingBC.obterRanking(dto.getPontuacao());
		  dto.setIdRanking(raking.getId());
		  dto.setAvatar(raking.getAvatar());		  
	  }
	  return dto;
  }

  public List<List<PontuacaoDTO>> listarPontuacaoAnual() {
	  List<Usuario> lstUsuario = usuarioBC.listarUsuarios();
	  List<Periodo> lstPeriodo = periodoBC.listarPeriodos();
	  List<List<PontuacaoDTO>> listPontuacaoAnual = new ArrayList<List<PontuacaoDTO>>();
	  for (Usuario usuario : lstUsuario) {
		  List<PontuacaoDTO> lstPontuacaoUsuarioPeriodo = new ArrayList<PontuacaoDTO>();
		  for (Periodo periodo: lstPeriodo) {
			  PontuacaoDTO pontuacaoUsuarioPeriodo = obterPontuacao(usuario.getId(), periodo.getId());
			  lstPontuacaoUsuarioPeriodo.add(pontuacaoUsuarioPeriodo);
		  }
		  aplicarRegraFaixaPreta(lstPontuacaoUsuarioPeriodo);
		  listPontuacaoAnual.add(lstPontuacaoUsuarioPeriodo);
	  }	  
	  return listPontuacaoAnual;
  }

  /**
   * Aplica a regra para obtenção do nivel de faixa preta
   * - 2 marrons
   * - Soma dos pontos do períodos anteriores >= 120 (1 marrom + 1 roxa + 1 azul)
   * @param listPontuacaoAnual
   */
  private void aplicarRegraFaixaPreta(List<PontuacaoDTO> lstPontuacaoUsuarioAnual) {
	  boolean temMarrom = false;
	  for (PontuacaoDTO pontuacaoUsuarioPeriodo : lstPontuacaoUsuarioAnual) {
		  
		  
	  }
  }
  
  
  
  
}