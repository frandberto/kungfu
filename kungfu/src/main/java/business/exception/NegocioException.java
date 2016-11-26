package business.exception;

public class NegocioException
  extends Exception
{
  private static final long serialVersionUID = -170268052224930226L;
  
  public NegocioException() {}
  
  public NegocioException(String message)
  {
    super(message);
  }
  
  public NegocioException(Throwable cause)
  {
    super(cause);
  }
  
  public NegocioException(String message, Throwable cause)
  {
    super(message, cause);
  }
  
  public NegocioException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
  {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
