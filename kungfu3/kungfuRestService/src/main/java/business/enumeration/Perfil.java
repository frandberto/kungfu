package business.enumeration;

public enum Perfil {
	
	ADMIN ("1", "admin", "Administrador"),
	USUARIO ("2", "user", "Usuário");
	
	private String codigo;
	private String sigla;
	private String descricao;
	
	private Perfil(String codigo, String sigla, String descricao){
		this.codigo = codigo;
		this.setSigla(sigla);
		this.setDescricao(descricao);		
	}	
	
	public static Perfil getPerfil(String codigo) {
		for (Perfil perfil: Perfil.values()) {
			if (perfil.codigo.equals(codigo)) {
				return perfil;
			}
		}
		return null;
	}
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	
}
