package business.enumeration;

public enum Avatar {
	
	FAIXA_BRANCA (1, "faixaBranca"),
	FAIXA_AZUL (2, "faixaAzul"),
	FAIXA_ROXA (3, "faixaRoxa"),
	FAIXA_MARROM (4, "faixaMarrom"),
	FAIXA_PRETA(5, "faixaPreta");
	
	private int valor;
	private String nivel;
	
	private Avatar(int valor, String nivel) {
		this.valor = valor;
		this.nivel = nivel;		
	}	
	
	public static Avatar getAvatar(String nivel) {
		for (Avatar avatar: Avatar.values()) {
			if (avatar.nivel.equals(nivel)) {
				return avatar;
			}
		}
		return null;
	}

	public int getValor() {
		return valor;
	}

	public String getNivel() {
		return nivel;
	}
}
