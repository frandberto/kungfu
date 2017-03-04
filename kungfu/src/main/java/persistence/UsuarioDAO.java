package persistence;

import java.util.List;

import javax.persistence.TypedQuery;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import entidade.Usuario;

@PersistenceController
public class UsuarioDAO
  extends GenericoDAO<Usuario>
{
  private static final long serialVersionUID = -4252476604207228325L;
  
  public Usuario obterUsuario(String codigoUsuario)
  {
    String sql = "select a from Usuario a  where a.codigo = :codigoUsuario";
    
    TypedQuery<Usuario> query = getEntityManager().createQuery(sql, Usuario.class);
    query.setParameter("codigoUsuario", codigoUsuario);
    
    List<Usuario> resultado = query.getResultList();
    if (resultado.size() > 0) {
      return (Usuario)resultado.get(0);
    }
    return null;
  }
  
  public Class<Usuario> getClassePaginacao()
  {
    return Usuario.class;
  }
}
