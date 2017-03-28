package persistence;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import entidade.Evento;
import entidade.Gamificacao;

@PersistenceController
public class GamificacaoDAO
  extends GenericoDAO<Gamificacao>
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
  
  public Class<Gamificacao> getClassePaginacao()
  {
    return Gamificacao.class;
  }
  
  public List<Gamificacao> listar()
  {
    Date dataAtual = new Date();
    String jpql = "select a from Gamificacao a  where :dataAtual between a.periodo.dataInicio and a.periodo.dataFim order by a.dataRegistro";
    
    TypedQuery<Gamificacao> query = getEntityManager().createQuery(jpql, Gamificacao.class);
    
    query.setParameter("dataAtual", dataAtual);
    return query.getResultList();
  }
  
  public List<Gamificacao> listar(Long idPeriodo)
  {
    String jpql = "select a from Gamificacao a  "
    		+ " join a.usuario as usuario "
    		+ " where a.periodo.id = :idPeriodo  order by a.dataRegistro desc, usuario.nome asc";
    
    TypedQuery<Gamificacao> query = getEntityManager().createQuery(jpql, Gamificacao.class);
    query.setParameter("idPeriodo", idPeriodo);
    return query.getResultList();
  }
}
