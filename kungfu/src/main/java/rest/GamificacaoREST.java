package rest;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.StringUtils;

import business.GamificacaoBC;
import business.exception.NegocioException;
import entidade.Gamificacao;
import util.DateUtil;

@Path("gamificacao")
public class GamificacaoREST
{
  @Context
  private UriInfo uri;
  @Inject
  private GamificacaoBC gamificacaoBC;
  @Inject
  private Logger log;
  
  @Path("eventos")
  @Produces({"application/json"})
  @GET
  public List<SaidaGamificacao> listar()
  {
    List<Gamificacao> listaGameficacao = this.gamificacaoBC.listar();
    List<SaidaGamificacao> dadosGameficacacao = criarDadosListagemGameficacao(listaGameficacao);
    return dadosGameficacacao;
  }
  
  @Path("listar/{idPeriodo}")
  @Produces({"application/json"})
  @GET
  public List<SaidaGamificacao> listar(Long idPeriodo)
  {
    List<Gamificacao> listaGameficacao = this.gamificacaoBC.listar(idPeriodo);
    List<SaidaGamificacao> dadosGameficacacao = criarDadosListagemGameficacao(listaGameficacao);
    return dadosGameficacacao;
  }
  
  @POST
  @Path("evento")
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
      dado.nomeEvento = game.getEvento().getDescricao();
      dado.dataRegistro = DateUtil.formatar(game.getDataRegistro());
      dado.nomeUsuario = game.getUsuario().getNome();
      dado.apelido = game.getUsuario().getApelido();
      dado.pontuacao = game.getEvento().getPontuacao();
      dado.observacao = game.getObservacao();
      listaDadosGameficacao.add(dado);
    }
    return listaDadosGameficacao;
  }
  
  public static class SaidaGamificacao
  {
    public String nomeEvento;
    public String nomeUsuario;
    public String apelido;
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
}
