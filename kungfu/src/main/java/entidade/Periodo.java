package entidade;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="periodo", schema="public")
@SequenceGenerator(schema="public", allocationSize=1, name="sq_id_periodo")
public class Periodo
{
  @Id
  @Column(name="id_periodo", unique=true, nullable=false)
  @GeneratedValue(generator="sq_id_periodo", strategy=GenerationType.IDENTITY)
  private Long id;
  
  @Column(name="descricao")
  private String descricao;
  
  @Column(name="dt_inicio")
  private Date dataInicio;
  
  @Column(name="dt_fim")
  private Date dataFim;
  
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
  
  public Date getDataInicio()
  {
    return this.dataInicio;
  }
  
  public void setDataInicio(Date dataInicio)
  {
    this.dataInicio = dataInicio;
  }
  
  public Date getDataFim()
  {
    return this.dataFim;
  }
  
  public void setDataFim(Date dataFim)
  {
    this.dataFim = dataFim;
  }
}
