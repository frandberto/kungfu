package entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="ranking", schema="public")
@SequenceGenerator(schema="public", allocationSize=1, name="sq_id_ranking")
public class Ranking {
	
	@Id
	@Column(name="id_ranking", unique=true, nullable=false)
	@GeneratedValue(generator="sq_id_ranking", strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="avatar")
	private String avatar;

	@Column(name="pontuacao_inicial")
	private Integer pontuacaoInicial;
	
	@Column(name="pontuacao_final")
	private Integer pontuacaoFinal;
	
	@Column(name="nivel")
	private String nivel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Integer getPontuacaoInicial() {
		return pontuacaoInicial;
	}

	public void setPontuacaoInicial(Integer pontuacaoInicial) {
		this.pontuacaoInicial = pontuacaoInicial;
	}

	public Integer getPontuacaoFinal() {
		return pontuacaoFinal;
	}

	public void setPontuacaoFinal(Integer pontuacaoFinal) {
		this.pontuacaoFinal = pontuacaoFinal;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

}
