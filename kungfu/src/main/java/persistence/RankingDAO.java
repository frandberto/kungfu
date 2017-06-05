package persistence;

import java.math.BigDecimal;

import javax.persistence.TypedQuery;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import entidade.Ranking;

@PersistenceController
public class RankingDAO extends GenericoDAO<Ranking> {


	/**
	 * 
	 */
	private static final long serialVersionUID = -630654971039833156L;

	public Ranking obterRanking(BigDecimal pontuacao) {
		String sql = "select a from Ranking a  "
				+ " where a.pontuacaoInicial <= :pontuacao "
				+ " and ((pontuacaoFinal is null) or "
				+ "(pontuacaoFinal is not null and :pontuacao >= :pontuacao)))";

		TypedQuery<Ranking> query = getEntityManager().createQuery(sql, Ranking.class);
		query.setParameter("pontuacao", pontuacao.intValue());
		return query.getSingleResult();
	}

	public Class<Ranking> getClassePaginacao() {
		return Ranking.class;
	}
}
