package tn.wevioo.tools.idgenerator.exception;

import nordnet.architecture.exceptions.NNException;
import nordnet.architecture.exceptions.utils.ErrorCode;


















public class AlgorithmException
  extends NNException
{
  private static final long serialVersionUID = 1L;
  
  public AlgorithmException(NNException cause)
  {
    super(cause);
  }
  




















  public AlgorithmException(ErrorCode errorCode, Object[] arguments, Throwable cause)
  {
    super(errorCode, arguments, cause);
  }
  


















  public AlgorithmException(ErrorCode errorCode, Object[] arguments)
  {
    super(errorCode, arguments);
  }
  

















  public AlgorithmException(ErrorCode errorCode, String message, Throwable cause)
  {
    super(errorCode, message, cause);
  }
  















  public AlgorithmException(ErrorCode errorCode, String message)
  {
    super(errorCode, message);
  }
  
















  public AlgorithmException(ErrorCode errorCode)
  {
    super(errorCode);
  }
}
