package business.exception;

public class InfraEstruturaException
  extends RuntimeException
{
  private static final long serialVersionUID = -3829118947371582775L;
  
  public InfraEstruturaException(String message, Throwable throwable)
  {
    super(message, throwable);
  }
}
