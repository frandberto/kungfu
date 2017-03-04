package entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="gamificacao", schema="public")
@SequenceGenerator(schema="public", allocationSize=1, name="sq_id_gamificacao")
public class Gamificacao
{
  @Id
  @Column(name="id_gamificacao", unique=true, nullable=false)
  @GeneratedValue(generator="sq_id_gamificacao", strategy=GenerationType.IDENTITY)
  private Long id;
  
  @ManyToOne(optional=false)
  @JoinColumn(name="id_usuario",referencedColumnName="id_usuario")
  private Usuario usuario;
  
  @OneToOne(optional=false)
  @JoinColumn(name = "id_evento")
  private Evento evento;
  
  @OneToOne(optional=false)
  @JoinColumn(name = "id_periodo")
  private Periodo periodo;
  
  @Column(name="data")
  private Date dataRegistro;
  
  @Column(name="tx_observacao")
  private String observacao;
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long id)
  {
    this.id = id;
  }
  
  public Usuario getUsuario()
  {
    return this.usuario;
  }
  
  public void setUsuario(Usuario usuario)
  {
    this.usuario = usuario;
  }
  
  public Evento getEvento()
  {
    return this.evento;
  }
  
  public void setEvento(Evento evento)
  {
    this.evento = evento;
  }
  
  public Periodo getPeriodo()
  {
    return this.periodo;
  }
  
  public void setPeriodo(Periodo periodo)
  {
    this.periodo = periodo;
  }
  
  public Date getDataRegistro()
  {
    return this.dataRegistro;
  }
  
  public void setDataRegistro(Date dataRegistro)
  {
    this.dataRegistro = dataRegistro;
  }
  
  public String getObservacao()
  {
    return this.observacao;
  }
  
  public void setObservacao(String observacao)
  {
    this.observacao = observacao;
  }
}
