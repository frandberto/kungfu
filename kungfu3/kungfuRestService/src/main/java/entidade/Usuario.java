package entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import business.enumeration.Perfil;

@Entity
@Table(name="usuario", schema="public")
@SequenceGenerator(schema="public", allocationSize=1, name="sq_id_usuario")
public class Usuario
{
	@Id
	@Column(name="id_usuario", unique=true, nullable=false)
	@GeneratedValue(generator="sq_id_usuario", strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="codigo")
	private String cpf;

	@Column(name="nome_completo")
	private String nome;

	@Column(name="apelido")
	private String apelido;

	@Column(name="email")
	private String email;

	@Column(name="senha")
	private String senha;

	@Column(name="token")
	private String token;
	
	@Column(name="data_token")
	Date dataToken;
	
	@Column(name="in_perfil")
	String perfil;
	
	public Perfil getPerfil() {
		return Perfil.getPerfil(perfil);
	}

	public Date getDataToken() {
		return dataToken;
	}

	public void setDataToken(Date dataToken) {
		this.dataToken = dataToken;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

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

	public String getApelido()
	{
		return this.apelido;
	}

	public void setApelido(String apelido)
	{
		this.apelido = apelido;
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
