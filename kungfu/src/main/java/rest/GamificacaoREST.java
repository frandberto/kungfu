package rest;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.StringUtils;

import business.EventoBC;
import business.GamificacaoBC;
import business.PeriodoBC;
import business.UsuarioBC;
import business.exception.NegocioException;
import dto.PontuacaoDTO;
import entidade.Evento;
import entidade.Gamificacao;
import entidade.Periodo;
import entidade.Usuario;
import util.DateUtil;

@Path("kungfu-leader")
public class GamificacaoREST
{
  @Context
  private UriInfo uri;
  @Inject
  private GamificacaoBC gamificacaoBC;
  
  @Inject
  private EventoBC eventoBC;
  
  @Inject
  private PeriodoBC periodoBC;
  
  @Inject
  private UsuarioBC usuarioBC;
  
  @Inject
  private Logger log;
  
  @Path("gamificacoes")
  @Produces({"application/json"})  
  @GET
  public List<GamificacaoJSON> listar()
  {
    List<Gamificacao> listaGameficacao = this.gamificacaoBC.listar();
    List<GamificacaoJSON> dadosGameficacacao = criarDadosListagemGameficacao(listaGameficacao);
    return dadosGameficacacao;
  }
  
  @Path("pontuacoes")
  @Produces({"application/json"})
  @GET
  public List<PontuacaoJSON> listarPontuacoes(String idPeriodoJason) {
	Long idPeriodo;
	
	if (idPeriodoJason.isEmpty()) {		
		Date hoje = new Date();	    	
	    Periodo periodo = periodoBC.obterPeriodo(hoje);
	    idPeriodo = periodo.getId();
	} else {
		idPeriodo = Long.valueOf(idPeriodoJason);
	}
    List<PontuacaoDTO> listaPontuacoes = this.gamificacaoBC.listarPontuacoes(idPeriodo);
    List<PontuacaoJSON> dadosPontuacao = criarDadosListagemPontuacao(listaPontuacoes);
    return dadosPontuacao;
  }
  
  private List<PontuacaoJSON> criarDadosListagemPontuacao(List<PontuacaoDTO> listaPontuacoes) {
	  
	  List<PontuacaoJSON> lstPontucacaoJSON = new ArrayList<PontuacaoJSON>();
	  for (PontuacaoDTO pontuacaoDTO: listaPontuacoes) {
		  PontuacaoJSON pontuacaoJSON = new PontuacaoJSON();
		  pontuacaoJSON.apelido = pontuacaoDTO.getApelido();
		  pontuacaoJSON.idUsuario = String.valueOf(pontuacaoDTO.getIdUsuario());
		  pontuacaoJSON.idRaking = String.valueOf(pontuacaoDTO.getIdRanking());
		  pontuacaoJSON.pontuacao = String.valueOf(pontuacaoDTO.getPontuacao());
		  pontuacaoJSON.avatar = String.valueOf(pontuacaoDTO.getAvatar());
		  lstPontucacaoJSON.add(pontuacaoJSON);
	   }
	   return lstPontucacaoJSON;
}

@Path("selecaoEventos")
  @Produces({"application/json"})
  @GET
  public List<SelecaoEvento> listarEvento() {
    List<Evento> lstEvento = eventoBC.listarEventos();
    List<SelecaoEvento> lstSelecaoEvento = criarListaSelecaoEvento(lstEvento);
    return lstSelecaoEvento;
  }
  
  private List<SelecaoEvento> criarListaSelecaoEvento(List<Evento> lstEvento) {
	List<SelecaoEvento> lstSelecaoEvento = new ArrayList<SelecaoEvento>();
	for (Evento evento: lstEvento) {
		SelecaoEvento selecaoEvento = new SelecaoEvento();
		selecaoEvento.idEvento = String.valueOf(evento.getId());
		selecaoEvento.descricao = evento.getDescricao();
		selecaoEvento.pontuacao = evento.getPontuacao();
		lstSelecaoEvento.add(selecaoEvento);
	}
	return lstSelecaoEvento;
   }
  
  @Path("selecaoPeriodos")
  @Produces({"application/json"})
  @GET
  public List<SelecaoPeriodo> listarPeriodo() {
    List<Periodo> lstPeriodo = periodoBC.listarPeriodos();
    List<SelecaoPeriodo> lstSelecaoPeriodo = criarListaSelecaoPeriodo(lstPeriodo);
    return lstSelecaoPeriodo;
  }
  
  private List<SelecaoPeriodo> criarListaSelecaoPeriodo(List<Periodo> lstPeriodo) {
	List<SelecaoPeriodo> lstSelecaoPeriodo = new ArrayList<SelecaoPeriodo>();
	for (Periodo periodo: lstPeriodo) {
		SelecaoPeriodo selecaoPeriodo = new SelecaoPeriodo();
		selecaoPeriodo.idPeriodo = String.valueOf(periodo.getId());
		selecaoPeriodo.descricao = periodo.getDescricao();		
		lstSelecaoPeriodo.add(selecaoPeriodo);
	}
	return lstSelecaoPeriodo;
   }
  
  @Path("selecaoUsuarios")
  @Produces({"application/json"})
  @GET
  public List<SelecaoUsuario> listarUsuario() {
    List<Usuario> lstUsuario = usuarioBC.listarUsuarios();
    List<SelecaoUsuario> lstSelecaoUsuario = criarListaSelecaoUsuario(lstUsuario);
    return lstSelecaoUsuario;
  }
  
  private List<SelecaoUsuario> criarListaSelecaoUsuario(List<Usuario> lstUsuario) {
	List<SelecaoUsuario> lstSelecaoUsuario = new ArrayList<SelecaoUsuario>();
	for (Usuario usuario: lstUsuario) {
		SelecaoUsuario selecaoUsuario = new SelecaoUsuario();
		selecaoUsuario.idUsuario = String.valueOf(usuario.getId());
		selecaoUsuario.apelido = usuario.getApelido();
		lstSelecaoUsuario.add(selecaoUsuario);
	}
	return lstSelecaoUsuario;
   }

  @POST
  @Path("gamificacao")
  @Consumes({"application/json"})
  public void registrarEvento(GamificacaoJSON entrada)
    throws URISyntaxException {
    if ((!StringUtils.isEmpty(entrada.idEvento)) && 
    		(!StringUtils.isEmpty(entrada.dataRegistro)) &&
    		(!StringUtils.isEmpty(entrada.idUsuario))) {
      
      this.log.info("Realizando registro de evento");
      Date dataEvento = DateUtil.toDate(entrada.dataRegistro);
      try {
    	Long idGamificacao = null;
    	if (StringUtils.isNotEmpty(entrada.idGamificacao)) {
    		idGamificacao = Long.valueOf(entrada.idGamificacao);
    	}
    	Periodo periodo = periodoBC.obterPeriodo(dataEvento);  	
    	
        Gamificacao novaGamificacao = this.gamificacaoBC.inserirGamificacao(idGamificacao, 
        		periodo.getId(), 
        		Long.valueOf(entrada.idEvento), Long.valueOf(entrada.idUsuario), 
        		dataEvento, entrada.observacao);
        this.log.info("Registro [" + novaGamificacao.getId() + "] incluido!");
      } catch (NegocioException e) {
        this.log.log(Level.SEVERE, "Erro ao tentar efetuar a publicacao do evento", e);
      }
    } else {
      this.log.info("Existe algum parametro nulo. Verifique os valores");
    }
  }
  
  @POST
  @Path("exclusao")
  @Consumes({"text/plain"})
  public void excluirEvento(String idGamificacao)
    throws URISyntaxException {   	
    	Long idEvento = Long.valueOf(idGamificacao);
        this.gamificacaoBC.excluir(idEvento);
  }
  
  private List<GamificacaoJSON> criarDadosListagemGameficacao(List<Gamificacao> listaGameficacao)
  {
    List<GamificacaoJSON> listaDadosGameficacao = new ArrayList<GamificacaoJSON>();
    for (Gamificacao game : listaGameficacao)
    {
      GamificacaoJSON dado = new GamificacaoJSON();
      dado.idGamificacao = String.valueOf(game.getId());
      dado.idPeriodo = String.valueOf(game.getPeriodo().getId());
      dado.nomeEvento = game.getEvento().getDescricao();
      dado.idEvento = String.valueOf(game.getEvento().getId());
      dado.dataRegistro = DateUtil.formatar(game.getDataRegistro());      
      dado.apelido = game.getUsuario().getApelido();
      dado.idUsuario = String.valueOf(game.getUsuario().getId());
      dado.pontuacao = String.valueOf(game.getEvento().getPontuacao());
      dado.observacao = game.getObservacao();
      listaDadosGameficacao.add(dado);
    }
    return listaDadosGameficacao;
  }
  
  public static class GamificacaoJSON
  {
	public String idGamificacao;
	public String idPeriodo;
    public String nomeEvento;
    public String idEvento;    
    public String apelido;
    public String idUsuario;
    public String dataRegistro;
    public String pontuacao;
    public String observacao;
    // TODO marcar para ignorar essa propriedade
    public String dataCalendario;
  }
  
  public static class PontuacaoJSON {	  
    public String apelido;
    public String idUsuario;
    public String pontuacao;
    public String idRaking;
    public String avatar;
  }
  
  public static class SelecaoEvento {
	    public String idEvento;
	    public String descricao;
	    public BigDecimal pontuacao;
  }
  
  public static class SelecaoPeriodo {
	    public String idPeriodo;
	    public String descricao;
  }
  
  public static class SelecaoUsuario {
	    public String idUsuario;
	    public String apelido;
   }
}
