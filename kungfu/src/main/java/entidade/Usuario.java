package entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="usuario", schema="kungfu")
@SequenceGenerator(schema="kungfu", allocationSize=1, name="sq_id_usuario")
public class Usuario
{
  @Id
  @Column(name="id_usuario", unique=true, nullable=false)
  @GeneratedValue(generator="sq_id_usuario", strategy=GenerationType.IDENTITY)
  private Long id;
  @Column(name="cpf")
  private String cpf;
  @Column(name="nome")
  private String nome;
  @Column(name="nome_guerra")
  private String nomeGuerra;
  @Column(name="email")
  private String email;
  
  public String getCpf()
  {
    return this.cpf;
  }
  
  public void setCpf(String cpf)
  {
    this.cpf = cpf;
  }
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long id)
  {
    this.id = id;
  }
  
  public String getNome()
  {
    return this.nome;
  }
  
  public void setNome(String nome)
  {
    this.nome = nome;
  }
  
  public String getNomeGuerra()
  {
    return this.nomeGuerra;
  }
  
  public void setNomeGuerra(String nomeGuerra)
  {
    this.nomeGuerra = nomeGuerra;
  }
  
  public String getEmail()
  {
    return this.email;
  }
  
  public void setEmail(String email)
  {
    this.email = email;
  }
}
