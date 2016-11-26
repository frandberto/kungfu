package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil
{
  public static String formatar(Date data)
  {
    return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(data);
  }
  
  public static String formatar(Date data, String mascara)
  {
    return new SimpleDateFormat(mascara).format(data);
  }
  
  public static Date toDate(String dataRegistro)
  {
    return null;
  }
}
