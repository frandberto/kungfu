package business;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.stereotype.Controller;
import business.enumeration.Ordenacao;
import entidade.Periodo;
import persistence.PeriodoDAO;

@Controller
public class PeriodoBC
{
	@Inject
	private PeriodoDAO periodoDAO;

	public Periodo obterPeriodo(Date dataReferencia) {
		Periodo periodo = periodoDAO.obterPeriodo(dataReferencia);
		if (periodo != null) {
			return periodo;
		}
		return null;
	}

	public Periodo buscarPorID(Long idPeriodo) {
		return periodoDAO.buscarPorID(idPeriodo);
	}

	public List<Periodo> listarPeriodos(Ordenacao ordenacao) {
		if (ordenacao.equals(Ordenacao.ASCENDENTE)) {
			return periodoDAO.listarAscendente();
		}
		return periodoDAO.listarDescendente();
	}
	
	public List<Periodo> listarPeriodos(Ordenacao ordenacao, String ano) {
		if (ordenacao.equals(Ordenacao.ASCENDENTE)) {
			return periodoDAO.listarAscendente(ano);
		}
		return periodoDAO.listarDescendente(ano);
	}
	
	public List<String> listarExercicios() {
		return periodoDAO.listarExercicios();
	}

	public List<Periodo> listarPeriodos(String exercicioAtual) {
		return periodoDAO.listar(exercicioAtual);
	}
  
}