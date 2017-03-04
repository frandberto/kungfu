package rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.StringUtils;

import util.DateUtil;
import business.GamificacaoBC;
import business.exception.NegocioException;
import entidade.Gamificacao;

@Path("gamificacao")
public class GamificacaoREST
{
  @Context
  private UriInfo uri;
  @Inject
  private GamificacaoBC gamificacaoBC;
  @Inject
  private Logger log;
  
  @Path("listar")
  @Produces({"application/json"})
  @GET
  public List<DadosGameficacao> listar()
  {
    List<Gamificacao> listaGameficacao = this.gamificacaoBC.listar();
    List<DadosGameficacao> dadosGameficacacao = criarDadosListagemGameficacao(listaGameficacao);
    return dadosGameficacacao;
  }
  
  @Path("listar/{idPeriodo}")
  @Produces({"application/json"})
  @GET
  public List<DadosGameficacao> listar(Long idPeriodo)
  {
    List<Gamificacao> listaGameficacao = this.gamificacaoBC.listar(idPeriodo);
    List<DadosGameficacao> dadosGameficacacao = criarDadosListagemGameficacao(listaGameficacao);
    return dadosGameficacacao;
  }
  
  @POST
  @Path("inserirEvento")
  @Produces({"text/plain"})
  public Response publicar(@FormParam("idEvento") String idEvento, @FormParam("idEvento") String idPeriodo, 
		  @FormParam("idUsuario") String idUsuario, @FormParam("dataRegistro") String dataRegistro, @FormParam("observacao") String observacao)
    throws URISyntaxException
  {
    if ((!StringUtils.isEmpty(idEvento)) && 
    		(!StringUtils.isEmpty(dataRegistro)) &&
    		(!StringUtils.isEmpty(idUsuario)) && 
    	    (!StringUtils.isEmpty(idPeriodo)))
    {
      
      this.log.info("Realizando registro de evento");
      Date dataEvento = DateUtil.toDate(dataRegistro);
      try
      {
        this.gamificacaoBC.inserirEvento(idEvento, idUsuario, dataEvento, observacao);
        this.log.info("Registro concluido!");
      }
      catch (NegocioException e)
      {
        this.log.log(Level.SEVERE, "Erro ao tentar efetuar a publicacao", e);
      }
    }
    else
    {
      this.log.info("Existe algum parametro nulo. Verifique os valores");
    }
    String host = this.uri.getBaseUri().getHost();
    String porta = String.valueOf(this.uri.getBaseUri().getPort());
    String url = "http://" + host + ":" + porta + "/build";
    URI location = new URI(url);
    this.log.info("Direcionado para o endereco url: " + location);
    return Response.temporaryRedirect(location).build();
  }
  
  private List<DadosGameficacao> criarDadosListagemGameficacao(List<Gamificacao> listaGameficacao)
  {
    List<DadosGameficacao> listaDadosGameficacao = new ArrayList<DadosGameficacao>();
    for (Gamificacao game : listaGameficacao)
    {
      DadosGameficacao dado = new DadosGameficacao();
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
  
  public static class DadosGameficacao
  {
    public String nomeEvento;
    public String nomeUsuario;
    public String apelido;
    public String dataRegistro;
    public String pontuacao;
    public String observacao;
  }
}
