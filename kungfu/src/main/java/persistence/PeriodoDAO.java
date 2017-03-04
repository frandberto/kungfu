package persistence;

import java.util.List;

import javax.persistence.TypedQuery;

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
}
