package business;

import java.math.BigDecimal;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.stereotype.Controller;
import entidade.Ranking;
import persistence.RankingDAO;

@Controller
public class RankingBC {
	
  @Inject
  private RankingDAO rankingDAO;
  
  public Ranking obterRanking(BigDecimal pontuacao) {
	  return rankingDAO.obterRanking(pontuacao);
  }
}