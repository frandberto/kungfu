package persistence;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import entidade.Periodo;
import util.DateUtil;

@PersistenceController
public class PeriodoDAO extends GenericoDAO<Periodo> {
	private static final long serialVersionUID = -4252476604207228325L;

	public Periodo obterPeriodo(String codigoPeriodo) {
		String sql = "select a from Periodo a  where a.codigo = :codigoPeriodo";

		TypedQuery<Periodo> query = getEntityManager().createQuery(sql, Periodo.class);
		query.setParameter("codigoPeriodo", codigoPeriodo);

		List<Periodo> resultado = query.getResultList();
		if (resultado.size() > 0) {
			return (Periodo) resultado.get(0);
		}
		return null;
	}

	public Class<Periodo> getClassePaginacao() {
		return Periodo.class;
	}

	/**
	 * Obtém o período a partir de uma data de referencia
	 * 
	 * @param dataReferencia
	 * @return
	 */
	public Periodo obterPeriodo(Date dataReferencia) {
		String sql = "select a from Periodo a  " + " where :dataReferencia between a.dataInicio and a.dataFim";

		TypedQuery<Periodo> query = getEntityManager().createQuery(sql, Periodo.class);
		query.setParameter("dataReferencia", dataReferencia);

		List<Periodo> resultado = query.getResultList();
		if (CollectionUtils.isNotEmpty(resultado)) {
			return (Periodo) resultado.get(0);
		}
		return null;
	}

	public List<Periodo> listarDescendenter() {
		String sql = "select a from Periodo a order by a.dataInicio desc";

		TypedQuery<Periodo> query = getEntityManager().createQuery(sql, Periodo.class);
		List<Periodo> resultado = query.getResultList();
		return resultado;
	}
	
	public List<Periodo> listarAscendente() {
		String sql = "select a from Periodo a order by a.dataInicio asc";

		TypedQuery<Periodo> query = getEntityManager().createQuery(sql, Periodo.class);
		List<Periodo> resultado = query.getResultList();
		return resultado;
	}

	public List<Periodo> listar(String exercicioAtual) {
		String sql = "select a from Periodo a " + " where a.dataInicio between :dataInicio and :dataFim "
				+ " order by a.dataInicio asc";
		Date dataInicioExercicio = DateUtil.toDate("01/01/" + exercicioAtual);
		Date dataFimExercicio = DateUtil.toDate("31/12/" + exercicioAtual);

		TypedQuery<Periodo> query = getEntityManager().createQuery(sql, Periodo.class);
		query.setParameter("dataInicio", dataInicioExercicio);
		query.setParameter("dataFim", dataFimExercicio);
		List<Periodo> resultado = query.getResultList();
		return resultado;
	}
}
