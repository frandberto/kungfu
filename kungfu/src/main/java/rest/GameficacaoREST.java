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
import business.GameficacaoBC;
import business.exception.NegocioException;
import entidade.Gameficacao;

@Path("gameficacao")
public class GameficacaoREST
{
  @Context
  private UriInfo uri;
  @Inject
  private GameficacaoBC gameficacaoBC;
  @Inject
  private Logger log;
  
  @Path("listar")
  @Produces({"application/json"})
  @GET
  public List<DadosGameficacao> listar()
  {
    List<Gameficacao> listaGameficacao = this.gameficacaoBC.listar();
    List<DadosGameficacao> dadosGameficacacao = criarDadosListagemGameficacao(listaGameficacao);
    return dadosGameficacacao;
  }
  
  @Path("listar/{idPeriodo}")
  @Produces({"application/json"})
  @GET
  public List<DadosGameficacao> listar(Long idPeriodo)
  {
    List<Gameficacao> listaGameficacao = this.gameficacaoBC.listar(idPeriodo);
    List<DadosGameficacao> dadosGameficacacao = criarDadosListagemGameficacao(listaGameficacao);
    return dadosGameficacacao;
  }
  
  @POST
  @Path("inserirEvento")
  @Produces({"text/plain"})
  public Response publicar(@FormParam("idEvento") String idEvento, @FormParam("idUsuario") String idUsuario, @FormParam("dataRegistro") String dataRegistro, @FormParam("observacao") String observacao)
    throws URISyntaxException
  {
    if ((!StringUtils.isEmpty(idEvento)) && (!StringUtils.isEmpty(dataRegistro)))
    {
      this.log.info("Ambiente : " + idEvento);
      this.log.info("Build: " + dataRegistro);
      this.log.info("Realizando publicacao");
      Date dataEvento = DateUtil.toDate(dataRegistro);
      try
      {
        this.gameficacaoBC.inserirEvento(idEvento, idUsuario, dataEvento, observacao);
        this.log.info("Publicacao concluida");
      }
      catch (NegocioException e)
      {
        this.log.log(Level.SEVERE, "Erro ao tentar efetuar a publicacao", e);
      }
    }
    else
    {
      this.log.info("Existe algum parametro nulo. Verifique os valores de ambiente e build");
    }
    String host = this.uri.getBaseUri().getHost();
    String porta = String.valueOf(this.uri.getBaseUri().getPort());
    String url = "http://" + host + ":" + porta + "/build";
    URI location = new URI(url);
    this.log.info("Direcionado para o endereco url: " + location);
    return Response.temporaryRedirect(location).build();
  }
  
  private List<DadosGameficacao> criarDadosListagemGameficacao(List<Gameficacao> listaGameficacao)
  {
    List<DadosGameficacao> listaDadosGameficacao = new ArrayList();
    for (Gameficacao game : listaGameficacao)
    {
      DadosGameficacao dado = new DadosGameficacao();
      dado.nomeEvento = game.getEvento().getDescricao();
      dado.dataRegistro = DateUtil.formatar(game.getDataRegistro());
      dado.nomeUsuario = game.getUsuario().getNome();
      dado.nomeUsuarioResumido = game.getUsuario().getNomeGuerra();
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
    public String nomeUsuarioResumido;
    public String dataRegistro;
    public String pontuacao;
    public String observacao;
  }
}
