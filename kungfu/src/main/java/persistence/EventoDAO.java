package persistence;

import java.util.List;

import javax.persistence.TypedQuery;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import entidade.Evento;

@PersistenceController
public class EventoDAO
  extends GenericoDAO<Evento>
{
  private static final long serialVersionUID = -4252476604207228325L;
  
  public Evento obterEvento(String codigoEvento)
  {
    String sql = "select a from Evento a  where a.codigo = :codigoEvento";
    
    TypedQuery<Evento> query = getEntityManager().createQuery(sql, Evento.class);
    query.setParameter("codigoEvento", codigoEvento);
    
    List<Evento> resultado = query.getResultList();
    if (resultado.size() > 0) {
      return (Evento)resultado.get(0);
    }
    return null;
  }
  
  public Class<Evento> getClassePaginacao()
  {
    return Evento.class;
  }
}
