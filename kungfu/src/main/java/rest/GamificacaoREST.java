package rest;

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
  public List<SaidaGamificacao> listar()
  {
    List<Gamificacao> listaGameficacao = this.gamificacaoBC.listar();
    List<SaidaGamificacao> dadosGameficacacao = criarDadosListagemGameficacao(listaGameficacao);
    return dadosGameficacacao;
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
  public void registrarEvento(EntradaGamificacao entrada)
    throws URISyntaxException
  {
    if ((!StringUtils.isEmpty(entrada.idEvento)) && 
    		(!StringUtils.isEmpty(entrada.dataRegistro)) &&
    		(!StringUtils.isEmpty(entrada.idUsuario)) && 
    	    (!StringUtils.isEmpty(entrada.idPeriodo)))
    {
      
      this.log.info("Realizando registro de evento");
      Date dataEvento = DateUtil.toDate(entrada.dataRegistro);
      try
      {
        Gamificacao novaGamificacao = this.gamificacaoBC.inserirEvento(Long.valueOf(entrada.idPeriodo), 
        		Long.valueOf(entrada.idEvento), Long.valueOf(entrada.idUsuario), 
        		dataEvento, entrada.observacao);
        this.log.info("Registro [" + novaGamificacao.getId() + "] incluido!");
      }
      catch (NegocioException e)
      {
        this.log.log(Level.SEVERE, "Erro ao tentar efetuar a publicacao do evento", e);
      }
    }
    else
    {
      this.log.info("Existe algum parametro nulo. Verifique os valores");
    }
  }
  
  private List<SaidaGamificacao> criarDadosListagemGameficacao(List<Gamificacao> listaGameficacao)
  {
    List<SaidaGamificacao> listaDadosGameficacao = new ArrayList<SaidaGamificacao>();
    for (Gamificacao game : listaGameficacao)
    {
      SaidaGamificacao dado = new SaidaGamificacao();
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
  
  public static class SaidaGamificacao
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
  }
  
  public static class EntradaGamificacao
  {
    public String idPeriodo;
    public String idEvento;
    public String idUsuario;
    public String dataRegistro;
    public String observacao;
  }
  
  public static class SelecaoEvento {
	    public String idEvento;
	    public String descricao;
	    public double pontuacao;
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
