package rest;

import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.StringUtils;

import business.EventoBC;
import business.GamificacaoBC;
import business.PeriodoBC;
import business.UsuarioBC;
import business.enumeration.Ordenacao;
import business.exception.NegocioException;
import dto.HistoricoRankingUsuarioDTO;
import dto.PontuacaoDTO;
import entidade.Evento;
import entidade.Gamificacao;
import entidade.Periodo;
import entidade.Usuario;
import util.BigDecimalUtil;
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
		List<Gamificacao> listaGamificacao = this.gamificacaoBC.listar();
		List<GamificacaoJSON> dadosGameficacacao = criarDadosListagemGameficacao(listaGamificacao);
		return dadosGameficacacao;
	}

	@Path("gamificacoesPorPeriodo/{idPeriodoJason}")
	@Produces({"application/json"})  
	@GET
	public List<GamificacaoJSON> listarPorPeriodo(@PathParam("idPeriodoJason") String idPeriodoJason)
	{
		Long idPeriodo = 0L;
		if (idPeriodoJason.isEmpty() || idPeriodoJason.equals("undefined")) {
			idPeriodo = periodoBC.obterPeriodo(DateUtil.dataAtual()).getId();
		} else {
			idPeriodo = Long.valueOf(idPeriodoJason);
		}
		List<Gamificacao> listaGameficacao = this.gamificacaoBC.listar(idPeriodo);
		List<GamificacaoJSON> dadosGameficacacao = criarDadosListagemGameficacao(listaGameficacao);
		return dadosGameficacacao;
	}

	@Path("pontuacoes/{idPeriodoJason}")
	@Produces({"application/json"})
	@GET
	public List<PontuacaoJSON> listarPontuacoes(@PathParam("idPeriodoJason") String idPeriodoJason) {
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


	@Path("pontuacoesUsuario/{codUsuarioJason}/{ano}")
	@Produces({"application/json"})
	@GET
	public List<PontuacaoPeriodoJSON> listarPontuacoesUsuario(
			@PathParam("codUsuarioJason") String codUsuarioJason,
			@PathParam("ano") String anoJson) {
		Long idUsuario = usuarioBC.buscarPorCodigo(codUsuarioJason).getId();

		List<Object[]> listaPontuacoes = this.gamificacaoBC.listarPontuacoesUsuario(idUsuario, anoJson);
		List<PontuacaoPeriodoJSON> dadosPontuacao = criarDadosListagemPontuacaoPeriodo(listaPontuacoes);
		return dadosPontuacao;
	}
	
	@Path("pontuacoesUsuario/{codUsuarioJason}")
	@Produces({"application/json"})
	@GET
	public List<PontuacaoPeriodoJSON> listarPontuacoesUsuario(
			@PathParam("codUsuarioJason") String codUsuarioJason) {
		Long idUsuario = usuarioBC.buscarPorCodigo(codUsuarioJason).getId();

		List<Object[]> listaPontuacoes = this.gamificacaoBC.listarPontuacoesUsuario(idUsuario);
		List<PontuacaoPeriodoJSON> dadosPontuacao = criarDadosListagemPontuacaoPeriodo(listaPontuacoes);
		return dadosPontuacao;
	}

	@Path("pontuacoesMedias")
	@Produces({"application/json"})
	@GET
	public List<PontuacaoPeriodoJSON> listarPontuacoesMedias() {
		List<Object[]> listaPontuacoes = this.gamificacaoBC.listarPontuacoesMedias();
		List<PontuacaoPeriodoJSON> dadosPontuacaoMedia = criarDadosListagemPontuacaoPeriodo(listaPontuacoes);
		return dadosPontuacaoMedia;
	}
	
	@Path("pontuacoesMedias/{anoJason}")
	@Produces({"application/json"})
	@GET
	public List<PontuacaoPeriodoJSON> listarPontuacoesMedias(@PathParam("anoJason") String anoJason) {
		List<Object[]> listaPontuacoes = this.gamificacaoBC.listarPontuacoesMedias(anoJason);
		List<PontuacaoPeriodoJSON> dadosPontuacaoMedia = criarDadosListagemPontuacaoPeriodo(listaPontuacoes);
		return dadosPontuacaoMedia;
	}

	@Path("pontuacaoAnual/{idExercicioJason}")
	@Produces({"application/json"})
	@GET
	public List<PontuacaoAnualJSON> listarPontuacaoAnual(@PathParam("idExercicioJason") String idExercicioJason) {	
		List<List<PontuacaoDTO>> listaPontuacoes = this.gamificacaoBC.listarPontuacaoAnual(idExercicioJason);
		List<PontuacaoAnualJSON> dadosPontuacao = criarDadosListagemPontuacaoAnual(listaPontuacoes);
		return dadosPontuacao;
	}

	@Path("historicoRanking")
	@Produces({"application/json"})
	@GET
	public List<HistoricoRankingJSON> listarHistoricoRanking() {
		List<HistoricoRankingUsuarioDTO> listaHistoricoRankingUsuario = this.gamificacaoBC.listarHistoricoRanking();
		List<HistoricoRankingJSON> dadosPontuacao = criarDadosListagemHistoricoRankingl(listaHistoricoRankingUsuario);
		return dadosPontuacao;
	}
	
	@Path("selecaoEventos")
	@Produces({"application/json"})
	@GET
	public List<SelecaoEntidade> listarEvento() {
		List<Evento> lstEvento = eventoBC.listarEventos();
		List<SelecaoEntidade> lstSelecaoEvento = criarListaSelecaoEvento(lstEvento);
		return lstSelecaoEvento;
	}
	
	@Path("selecaoPeriodos")
	@Produces({"application/json"})
	@GET
	public List<SelecaoEntidade> listarPeriodo() {
		List<Periodo> lstPeriodo = periodoBC.listarPeriodos(Ordenacao.DESCENDENTE);
		List<SelecaoEntidade> lstSelecaoPeriodo = criarListaSelecaoPeriodo(lstPeriodo);
		return lstSelecaoPeriodo;
	}
	
	@Path("selecaoAnos")
	@Produces({"application/json"})
	@GET
	public List<SelecaoEntidade> listarExercicios() {
		List<String> lstAnos = periodoBC.listarExercicios();
		List<SelecaoEntidade> lstSelecaoExercicios = criarListaSelecaoExercicio(lstAnos);
		return lstSelecaoExercicios;
	}

	@Path("periodoAtual")
	@Produces({"application/json"})
	@GET
	public SelecaoEntidade obterPeriodoAtual() {
		Periodo periodoAtual = periodoBC.obterPeriodo(new Date());
		SelecaoEntidade selecaoPeriodo = new SelecaoEntidade();
		selecaoPeriodo.id = String.valueOf(periodoAtual.getId());
		selecaoPeriodo.descricao = periodoAtual.getDescricao();
		return selecaoPeriodo;
	}
	
	@Path("selecaoUsuarios")
	@Produces({"application/json"})
	@GET
	public List<SelecaoEntidade> listarUsuario() {
		List<Usuario> lstUsuario = usuarioBC.listarUsuarios();
		List<SelecaoEntidade> lstSelecaoUsuario = criarListaSelecaoUsuario(lstUsuario);
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
			Date dataEvento = DateUtil.toDate(entrada.dataRegistro, "yyyy-MM-dd");
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
	
	@Path("frequenciaEventos/{idPeriodoJason}/{codUsuarioJason}")
	@Produces({"application/json"})  
	@GET
	public List<FrequenciaEventoJSON> obterFrequenciaEventos(
			@PathParam("idPeriodoJason") String idPeriodoJason, 
			@PathParam("codUsuarioJason") String codUsuarioJason)
	{
		Long idPeriodo = 0L;
		if (idPeriodoJason.isEmpty() || idPeriodoJason.equals("undefined")) {
			idPeriodo = periodoBC.obterPeriodo(DateUtil.dataAtual()).getId();
		} else {
			idPeriodo = Long.valueOf(idPeriodoJason);
		}
		if (codUsuarioJason.isEmpty() || codUsuarioJason.equals("undefined")) {
			return new ArrayList<FrequenciaEventoJSON>();
		}

		Long idUsuario = usuarioBC.buscarPorCodigo(codUsuarioJason).getId();

		List<Object[]> listaFrequenciaEventos = 
				this.gamificacaoBC.contabilizarEventos(idPeriodo, idUsuario);
		List<FrequenciaEventoJSON> dadosGameficacacao = criarDadosContabilizacaoEventos(listaFrequenciaEventos);
		return dadosGameficacacao;
	}
	
	private List<SelecaoEntidade> criarListaSelecaoUsuario(List<Usuario> lstUsuario) {
		List<SelecaoEntidade> lstSelecaoUsuario = new ArrayList<SelecaoEntidade>();
		for (Usuario usuario: lstUsuario) {
			SelecaoEntidade selecaoUsuario = new SelecaoEntidade();
			selecaoUsuario.id = String.valueOf(usuario.getId());
			selecaoUsuario.descricao = usuario.getApelido();
			lstSelecaoUsuario.add(selecaoUsuario);
		}
		return lstSelecaoUsuario;
	}
	
	private List<PontuacaoPeriodoJSON> criarDadosListagemPontuacaoPeriodo(List<Object[]> listaPontuacoes) {	
		List<PontuacaoPeriodoJSON> lstPontucacaoJSON = new ArrayList<PontuacaoPeriodoJSON>();
		for (Object[] pontuacao: listaPontuacoes) {
			PontuacaoPeriodoJSON pontuacaoJSON = new PontuacaoPeriodoJSON();
			pontuacaoJSON.id = String.valueOf(pontuacao[0]);
			pontuacaoJSON.descricao = (String)pontuacao[1];
			pontuacaoJSON.pontuacao = String.valueOf(pontuacao[2]);		  
			lstPontucacaoJSON.add(pontuacaoJSON);
		}
		return lstPontucacaoJSON;
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

	private List<HistoricoRankingJSON> criarDadosListagemHistoricoRankingl(
			List<HistoricoRankingUsuarioDTO> lstHistoricoRankingUsuario) {
		List<HistoricoRankingJSON> lstPontuacaoHistoricoRankingJSON = new ArrayList<HistoricoRankingJSON>();
		for (HistoricoRankingUsuarioDTO historicoRanking: lstHistoricoRankingUsuario) {
			HistoricoRankingJSON historicoJSON = new HistoricoRankingJSON();
			historicoJSON.apelido = historicoRanking.getUsuario().getApelido();
			historicoJSON.idUsuario = String.valueOf(historicoRanking.getUsuario().getId());
			historicoJSON.qtdFaixaBranca = String.valueOf(historicoRanking.getQtdFaixaBranca());
			historicoJSON.qtdFaixaAzul = String.valueOf(historicoRanking.getQtdFaixaAzul());
			historicoJSON.qtdFaixaRoxa = String.valueOf(historicoRanking.getQtdFaixaRoxa());
			historicoJSON.qtdFaixaMarrom = String.valueOf(historicoRanking.getQtdFaixaMarom());
			historicoJSON.qtdFaixaPreta = String.valueOf(historicoRanking.getQtdFaixaPreta());
			lstPontuacaoHistoricoRankingJSON.add(historicoJSON);
		}
		return lstPontuacaoHistoricoRankingJSON;
	}

	private List<PontuacaoAnualJSON> criarDadosListagemPontuacaoAnual(List<List<PontuacaoDTO>> listaPontuacoes) {
		List<PontuacaoAnualJSON> lstPontuacaoAnualJSON = new ArrayList<PontuacaoAnualJSON>();
		for (List<PontuacaoDTO> lstPontuacaoUsuario: listaPontuacoes) {
			PontuacaoAnualJSON pontuacaoAnualUsuarioJSON = new PontuacaoAnualJSON();
			PontuacaoDTO pontuacaoUsuario1oPeriodo = lstPontuacaoUsuario.get(0);
			pontuacaoAnualUsuarioJSON.idUsuario = nvl(pontuacaoUsuario1oPeriodo.getIdUsuario());
			pontuacaoAnualUsuarioJSON.apelido = nvl(pontuacaoUsuario1oPeriodo.getApelido());
			pontuacaoAnualUsuarioJSON.pontuacao1oPeriodo = nvl(pontuacaoUsuario1oPeriodo.getPontuacao());
			pontuacaoAnualUsuarioJSON.avatar1oPeriodo = nvl(pontuacaoUsuario1oPeriodo.getAvatar());
			PontuacaoDTO pontuacaoUsuario2oPeriodo = lstPontuacaoUsuario.get(1);		
			pontuacaoAnualUsuarioJSON.pontuacao2oPeriodo = nvl(pontuacaoUsuario2oPeriodo.getPontuacao());
			pontuacaoAnualUsuarioJSON.avatar2oPeriodo = nvl(pontuacaoUsuario2oPeriodo.getAvatar());
			PontuacaoDTO pontuacaoUsuario3oPeriodo = lstPontuacaoUsuario.get(2);		
			pontuacaoAnualUsuarioJSON.pontuacao3oPeriodo = nvl(pontuacaoUsuario3oPeriodo.getPontuacao());
			pontuacaoAnualUsuarioJSON.avatar3oPeriodo = nvl(pontuacaoUsuario3oPeriodo.getAvatar());
			// São 3 ciclos
			//		PontuacaoDTO pontuacaoUsuario4oPeriodo = lstPontuacaoUsuario.get(3);
			//		if (pontuacaoAnualUsuarioJSON.apelido.equals("")) {
			//			pontuacaoAnualUsuarioJSON.idUsuario = nvl(pontuacaoUsuario4oPeriodo.getIdUsuario());
			//			pontuacaoAnualUsuarioJSON.apelido = nvl(pontuacaoUsuario4oPeriodo.getApelido());
			//		}
			//		pontuacaoAnualUsuarioJSON.pontuacao4oPeriodo = nvl(pontuacaoUsuario4oPeriodo.getPontuacao());
			//		pontuacaoAnualUsuarioJSON.avatar4oPeriodo = nvl(pontuacaoUsuario4oPeriodo.getAvatar());
			lstPontuacaoAnualJSON.add(pontuacaoAnualUsuarioJSON);
		}
		return lstPontuacaoAnualJSON;
	}

	private String nvl(String valor) {
		if (valor == null) {
			return "";
		}
		return valor;
	}

	private String nvl(BigDecimal valor) {
		if (valor == null) {
			return "";
		}
		return String.valueOf(valor);
	}

	private String nvl(Long valor) {
		if (valor == null) {
			return "";
		}
		return String.valueOf(valor);
	}

	

	private List<SelecaoEntidade> criarListaSelecaoEvento(List<Evento> lstEvento) {
		List<SelecaoEntidade> lstSelecaoEvento = new ArrayList<SelecaoEntidade>();
		for (Evento evento: lstEvento) {
			SelecaoEntidade selecaoEvento = new SelecaoEntidade();
			selecaoEvento.id = String.valueOf(evento.getId());
			selecaoEvento.descricao = evento.getDescricao();
			selecaoEvento.extraPropriedade = BigDecimalUtil.formatarMoeda(evento.getPontuacao());
			lstSelecaoEvento.add(selecaoEvento);
		}
		return lstSelecaoEvento;
	}
	

	private List<SelecaoEntidade> criarListaSelecaoPeriodo(List<Periodo> lstPeriodo) {
		List<SelecaoEntidade> lstSelecaoPeriodo = new ArrayList<SelecaoEntidade>();
		for (Periodo periodo: lstPeriodo) {
			SelecaoEntidade selecaoPeriodo = new SelecaoEntidade();
			selecaoPeriodo.id = String.valueOf(periodo.getId());
			selecaoPeriodo.descricao = periodo.getDescricao();		
			lstSelecaoPeriodo.add(selecaoPeriodo);
		}
		return lstSelecaoPeriodo;
	}
	
	private List<SelecaoEntidade> criarListaSelecaoExercicio(List<String> lstAnos) {
		List<SelecaoEntidade> lstSelecaoExercicio = new ArrayList<SelecaoEntidade>();
		for (String exercicio: lstAnos) {
			SelecaoEntidade selecaoExecicio = new SelecaoEntidade();
			selecaoExecicio.id = exercicio;
			selecaoExecicio.descricao = 	exercicio;	
			lstSelecaoExercicio.add(selecaoExecicio);
		}
		return lstSelecaoExercicio;
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

	private List<FrequenciaEventoJSON> criarDadosContabilizacaoEventos(List<Object[]> listaContabilizacaoEventos) {
		List<FrequenciaEventoJSON> lstFrequenciaEventoJSON = new ArrayList<FrequenciaEventoJSON>();
		for (Object[] linha: listaContabilizacaoEventos) {
			FrequenciaEventoJSON  frequencia = new FrequenciaEventoJSON();
			if (linha[0] instanceof Integer) {
				Integer idEvento = (Integer) linha[0];
				frequencia.id = String.valueOf(idEvento);
			} else {
				Long idEvento = (Long) linha[0];
				frequencia.id = String.valueOf(idEvento);
			}
			String nomeEvento = (String) linha[1];
			BigInteger qtd = (BigInteger) linha[2];			
			frequencia.nomeEvento = nomeEvento;
			frequencia.quantidade = String.valueOf(qtd);
			lstFrequenciaEventoJSON.add(frequencia);
		}
		return lstFrequenciaEventoJSON;
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

	public static class PontuacaoPeriodoJSON {	  
		public String id;
		public String descricao;
		public String pontuacao;
	}

	public static class PontuacaoAnualJSON {	  
		public String apelido;
		public String idUsuario;
		public String pontuacao1oPeriodo;
		public String avatar1oPeriodo;
		public String pontuacao2oPeriodo;
		public String avatar2oPeriodo;
		public String pontuacao3oPeriodo;
		public String avatar3oPeriodo;
	}

	public static class HistoricoRankingJSON {	  
		public String apelido;
		public String idUsuario;
		public String qtdFaixaBranca;
		public String qtdFaixaAzul;
		public String qtdFaixaRoxa;
		public String qtdFaixaMarrom;
		public String qtdFaixaPreta;
	}

	public static class SelecaoEntidade {
		public String id;
		public String descricao;
		public String extraPropriedade;
	}  

	public static class FrequenciaEventoJSON {
		public String id;
		public String nomeEvento;
		public String quantidade;
	}
}
