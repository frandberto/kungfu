package entidade;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="gameficacao", schema="kungfu")
@SequenceGenerator(schema="kungfu", allocationSize=1, name="sq_id_gameficacao")
public class Gameficacao
{
  @Id
  @Column(name="id_gameficacao", unique=true, nullable=false)
  @GeneratedValue(generator="sq_id_gameficacao", strategy=GenerationType.IDENTITY)
  private Long id;
  @Column(name="codigo")
  @OneToMany(mappedBy="id_usuario", targetEntity=Usuario.class)
  private Usuario usuario;
  @Column(name="evento")
  @OneToMany(mappedBy="id_evento", targetEntity=Evento.class)
  private Evento evento;
  @Column(name="periodo")
  @OneToMany(mappedBy="id_periodo", targetEntity=Periodo.class)
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
