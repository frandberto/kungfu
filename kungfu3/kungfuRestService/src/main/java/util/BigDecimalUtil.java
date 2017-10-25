package util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

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
	
	/**
     * Recebe um valor BigDecimal e formata para o padrão de moeda pt/BR.
     * 
     * @param valor
     *            Valor que será formatado.
     * @param exibirMoeda
     *            Indica se deve ser exibida a moeda no valor.
     * @return Valor formatado.
     */
    public static String formatarMoeda(BigDecimal valor) {
        String mascara;
        if (valor == null) {
            return "";
        }
        
        mascara = "###,###,##0.00";        

        DecimalFormat formatar = new DecimalFormat(mascara,
            new DecimalFormatSymbols(new Locale("pt", "BR")));
        return formatar.format(valor);
    }
	
}
