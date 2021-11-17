package com.wykblog.blog.utils.exception;

public class OptimusUnknownException extends OptimusExceptionBase {
  private static final long serialVersionUID = 8872666177309487666L;
  
  private static final int HTTP_CODE = 500;
  
  private static final String ERR_CODE = ErrorCodes.UNKNOW_EXCEPTION.getErrorCode();
  
  public OptimusUnknownException() {
    setupErrorCode();
  }
  
  public OptimusUnknownException(String errorMsg) {
    super(errorMsg);
    setupErrorCode();
  }
  
  public OptimusUnknownException(String errorMsg, Throwable cause) {
    super(errorMsg, cause);
    setupErrorCode();
  }
  
  public OptimusUnknownException(Throwable cause) {
    super(cause);
    setupErrorCode();
  }
  
  private void setupErrorCode() {
    setHttpCode(500);
    setErrCode("500");
  }
}
