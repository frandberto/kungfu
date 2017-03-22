package business;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.stereotype.Controller;
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

	public List<Periodo> listarPeriodos() {
		return periodoDAO.listarTodos();
	}
  
}