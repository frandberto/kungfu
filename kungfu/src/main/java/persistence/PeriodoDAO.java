package persistence;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import entidade.Periodo;

@PersistenceController
public class PeriodoDAO
  extends GenericoDAO<Periodo>
{
  private static final long serialVersionUID = -4252476604207228325L;
  
  public Periodo obterPeriodo(String codigoPeriodo)
  {
    String sql = "select a from Periodo a  where a.codigo = :codigoPeriodo";
    
    TypedQuery<Periodo> query = getEntityManager().createQuery(sql, Periodo.class);
    query.setParameter("codigoPeriodo", codigoPeriodo);
    
    List<Periodo> resultado = query.getResultList();
    if (resultado.size() > 0) {
      return (Periodo)resultado.get(0);
    }
    return null;
  }
  
  public Class<Periodo> getClassePaginacao()
  {
    return Periodo.class;
  }

  /**
   * Obtém o período a partir de uma data de referencia
   * @param dataReferencia
   * @return
   */
  public Periodo obterPeriodo(Date dataReferencia) {
	 String sql = "select a from Periodo a  where :dataReferencia between a.dataInicio and a.dataFim";
	    
	    TypedQuery<Periodo> query = getEntityManager().createQuery(sql, Periodo.class);
	    query.setParameter("dataReferencia", dataReferencia);
	    
	    List<Periodo> resultado = query.getResultList();
	    if (CollectionUtils.isNotEmpty(resultado)) {
	      return (Periodo)resultado.get(0);
	    }
	    return null;
  }
}
