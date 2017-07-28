package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil
{
  public static String formatar(Date data)
  {
    return new SimpleDateFormat("dd/MM/yyyy").format(data);
  }
  
  public static Date dataAtual() {
       return new Date();
  }
  
  public static String formatar(Date data, String mascara)
  {
    return new SimpleDateFormat(mascara).format(data);
  }
  
  /**
   * Retorna uma data a partir string informada no formato dd/mm/yyyy
   * @param dataRegistro
   * @return data
   */
  public static Date toDate(String dataRegistro) {
	  SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	  dataRegistro = dataRegistro.substring(0, 10);
      try {
		Date dataFormatada = formatter.parse(dataRegistro);
		return dataFormatada;
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}
  }
}
