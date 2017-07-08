package util;

import java.math.BigDecimal;

public class BigDecimalUtil {

	/**
	 * Verifica se um valor é null, se for retorna o valor substituto.
	 * @param valorOriginal valor original
	 * @param valorSubstituto valor substituto caso o valor original seja nulo.
	 * @return valor após verificação.
	 */
	public static BigDecimal nvl(BigDecimal valorOriginal, BigDecimal valorSubstituto) {
		if (valorOriginal == null) {
			return valorSubstituto;
		} else {
			return valorOriginal;
		}
	}
	
	/**
	 * Verifica se um valor é null, se for retorna BigDecimal.ZERO.
	 * @param valorOriginal valor a ser verificado
	 * @return valor após verificação
	 */
	public static BigDecimal nvl(BigDecimal valorOriginal) {
		return nvl(valorOriginal, BigDecimal.ZERO);
	}

}
