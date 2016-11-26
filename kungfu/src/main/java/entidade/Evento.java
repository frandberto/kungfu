package entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="evento", schema="kungfu")
@SequenceGenerator(schema="kungfu", allocationSize=1, name="sq_id_evento")
public class Evento
{
  @Id
  @Column(name="id_evento", unique=true, nullable=false)
  @GeneratedValue(generator="sq_id_evento", strategy=GenerationType.IDENTITY)
  private Long id;
  @Column(name="codigo")
  private String codigo;
  @Column(name="descricao")
  private String descricao;
  @Column(name="pontuacao")
  private String pontuacao;
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long id)
  {
    this.id = id;
  }
  
  public String getDescricao()
  {
    return this.descricao;
  }
  
  public void setDescricao(String descricao)
  {
    this.descricao = descricao;
  }
  
  public String getCodigo()
  {
    return this.codigo;
  }
  
  public void setCodigo(String codigo)
  {
    this.codigo = codigo;
  }
  
  public String getPontuacao()
  {
    return this.pontuacao;
  }
  
  public void setPontuacao(String pontuacao)
  {
    this.pontuacao = pontuacao;
  }
}
