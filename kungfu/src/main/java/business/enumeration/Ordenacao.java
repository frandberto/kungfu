package business.enumeration;

public enum Ordenacao {
	
	ASCENDENTE ("1"),
	DESCENDENTE ("2");
	
	private String codigo;
		
	private Ordenacao(String codigo) {
		this.codigo = codigo;
	}	
	
	public static Ordenacao getPerfil(String codigo) {
		for (Ordenacao ordenacao: Ordenacao.values()) {
			if (ordenacao.codigo.equals(codigo)) {
				return ordenacao;
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
}
