package entidade;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="evento", schema="public")
@SequenceGenerator(schema="public", allocationSize=1, name="sq_id_evento")
public class Evento {
	@Id
	@Column(name="id_evento", unique=true, nullable=false)
	@GeneratedValue(generator="sq_id_evento", strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="codigo")
	private String codigo;

	@Column(name="descricao")
	private String descricao;
	
	@Column(name="legenda")
	private String legenda;

	public String getLegenda() {
		return legenda;
	}

	public void setLegenda(String legenda) {
		this.legenda = legenda;
	}

	@Column(name="pontuacao")
	private BigDecimal pontuacao;
	
	@Column(name="in_situacao")
	private String situacao;

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public BigDecimal getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(BigDecimal pontuacao) {
		this.pontuacao = pontuacao;
	}

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

}
