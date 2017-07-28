package persistence;

import java.util.List;

import javax.persistence.TypedQuery;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import business.enumeration.Perfil;
import entidade.Evento;
import entidade.Usuario;

@PersistenceController
public class UsuarioDAO
  extends GenericoDAO<Usuario>
{
  private static final long serialVersionUID = -4252476604207228325L;
  
  public Usuario obterUsuario(String codigoUsuario)
  {
    String sql = "select a from Usuario a  where a.cpf = :codigoUsuario";
    
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

  public List<Usuario> listar() {
	  String sql = "select a from Usuario a "
			  + " order by a.apelido asc";

	  TypedQuery<Usuario> query = getEntityManager().createQuery(sql, Usuario.class);

	  List<Usuario> resultado = query.getResultList();
	  return resultado;
  }

	public List<Usuario> listarSemAdmin() {
		String sql = "select a from Usuario a " +
				" where a.perfil <> :perfilAdmin " + 
				" order by a.apelido asc";

		TypedQuery<Usuario> query = getEntityManager().createQuery(sql, Usuario.class);
		query.setParameter("perfilAdmin", Perfil.ADMIN.getCodigo());

		List<Usuario> resultado = query.getResultList();
		return resultado;
	}

}
