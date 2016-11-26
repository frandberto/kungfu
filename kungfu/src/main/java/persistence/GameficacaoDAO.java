package persistence;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import entidade.Evento;
import entidade.Gameficacao;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@PersistenceController
public class GameficacaoDAO
  extends GenericoDAO<Gameficacao>
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
  
  public Class<Gameficacao> getClassePaginacao()
  {
    return Gameficacao.class;
  }
  
  public List<Gameficacao> listar()
  {
    Date dataAtual = new Date();
    String jpql = "select a from Gameficacao a  where a.periodo.dataInicio >= :dataAtual  and a.periodo.dataFim <= :dataAtual  order by a.dataRegistro";
    
    TypedQuery<Gameficacao> query = getEntityManager().createQuery(jpql, Gameficacao.class);
    
    query.setParameter("dataAtual", dataAtual);
    return query.getResultList();
  }
  
  public List<Gameficacao> listar(Long idPeriodo)
  {
    String jpql = "select a from Gameficacao a  where a.periodo.id = :idPeriodo  order by a.dataRegistro";
    
    TypedQuery<Gameficacao> query = getEntityManager().createQuery(jpql, Gameficacao.class);
    query.setParameter("idPeriodo", idPeriodo);
    return query.getResultList();
  }
}
