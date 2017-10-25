package persistence;

import br.gov.frameworkdemoiselle.pagination.Pagination;
import br.gov.frameworkdemoiselle.pagination.PaginationContext;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import business.exception.InfraEstruturaException;
import java.util.List;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@PersistenceController
public abstract class GenericoDAO<T>
  extends JPACrud<T, Long>
{
  private static final long serialVersionUID = 2372699932808558145L;
  @Inject
  private PaginationContext paginationContext;
  @Inject
  private Logger log;
  
  public void clear()
  {
    getEntityManager().clear();
  }
  
  protected EntityManager getEntityManager()
  {
    EntityManager em = super.getEntityManager();
    
    em.setFlushMode(FlushModeType.COMMIT);
    
    return em;
  }
  
  public PaginationContext getPaginationContext()
  {
    return this.paginationContext;
  }
  
  public abstract Class<T> getClassePaginacao();
  
  public Class<? extends Object> getClasseObjetoPaginacao()
  {
    return new Object().getClass();
  }
  
  public List<T> retornaConsultaPaginada(Query query, Query queryCount)
  {
    Pagination pagination = this.paginationContext.getPagination(getClassePaginacao());
    if (pagination != null)
    {
      if (pagination.getTotalPages() == 0)
      {
        Long total = (Long)queryCount.getSingleResult();
        pagination.setTotalResults(Integer.parseInt(total.toString()));
      }
      query.setFirstResult(pagination.getFirstResult());
      query.setMaxResults(pagination.getPageSize());
    }
    return query.getResultList();
  }
  
  public List<? extends Object> consultaPaginada(Query query, Query queryCount)
  {
    Pagination pagination = this.paginationContext.getPagination(getClasseObjetoPaginacao());
    if (pagination != null)
    {
      if (pagination.getTotalPages() == 0)
      {
        Long total = (Long)queryCount.getSingleResult();
        pagination.setTotalResults(Integer.parseInt(total.toString()));
      }
      query.setFirstResult(pagination.getFirstResult());
      query.setMaxResults(pagination.getPageSize());
    }
    return query.getResultList();
  }
  
  public List<T> listarPorExemplo(T example)
  {
    return super.findByExample(example);
  }
  
  public void close()
  {
    super.getEntityManager().close();
  }
  
  @Transactional
  public void delete(Long id)
  {
    this.log.info("Realizando Exclusao Fisica.......");
    T entity = load(id);
    getEntityManager().remove(entity);
  }
  
  public void flush()
  {
    try
    {
      getEntityManager().flush();
    }
    catch (PersistenceException erro)
    {
      throw new InfraEstruturaException("Erro ao tentar atualizar a base de dados.", erro);
    }
  }
  
  public boolean contemNaSessao(Object obj)
  {
    try
    {
      return getEntityManager().contains(obj);
    }
    catch (PersistenceException erro)
    {
      throw new InfraEstruturaException("Erro ao tentar verificar um objeto ne sess��o.", erro);
    }
  }
  
  public List<T> listarTodos()
  {
    CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
    CriteriaQuery<T> cq = cb.createQuery(getBeanClass());
    Root<T> element = cq.from(getBeanClass());
    cq.select(element);
    TypedQuery<T> q = getEntityManager().createQuery(cq);
    List<T> all = q.getResultList();
    return all;
  }
  
  public T buscarPorID(Long id)
  {
    EntityManager em = getEntityManager();
    Class<T> entidade = getBeanClass();
    String nomeEntidade = entidade.getName();
    
    String jpql = "from " + nomeEntidade + " where id = :id";
    TypedQuery<T> query = em.createQuery(jpql, entidade);
    query.setParameter("id", id);
    
    List<T> resultados = query.getResultList();
    if (resultados.isEmpty()) {
      return null;
    }
    return (T)resultados.get(0);
  }
  
  public List<T> buscarPorDescricao(String txt)
  {
    Class<T> entidade = getBeanClass();
    String nomeEntidade = entidade.getName();
    
    TypedQuery<T> query = getEntityManager().createQuery("from " + nomeEntidade + " where descricao like :desc", entidade);
    
    query.setParameter("desc", "%" + txt.toUpperCase() + "%");
    List<T> resultList = query.getResultList();
    return resultList;
  }
  
  public void removerDaSessao(T entity)
  {
    try
    {
      getEntityManager().detach(entity);
    }
    catch (PersistenceException erro)
    {
      throw new InfraEstruturaException("Erro ao tentar remover um registro.", erro);
    }
  }
  
  public void recarregar(T entity)
  {
    try
    {
      getEntityManager().refresh(entity);
    }
    catch (PersistenceException erro)
    {
      throw new InfraEstruturaException("Erro ao tentar recarregar um registro do banco.", erro);
    }
  }
  
  @Transactional
  public Object sincronizar(Object obj)
  {
    try
    {
      return getEntityManager().merge(obj);
    }
    catch (PersistenceException erro)
    {
      throw new InfraEstruturaException("Erro ao tentar atualizar/sincronizar um objeto na base de dados.", erro);
    }
  }
}
