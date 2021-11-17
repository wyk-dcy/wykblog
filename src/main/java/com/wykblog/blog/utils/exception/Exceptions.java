package com.wykblog.blog.utils.exception;

import java.util.HashMap;
import java.util.Map;

public class Exceptions {
  public static OptimusExceptionBase fail(ErrorMessage errorMsg, String msg) throws OptimusExceptionBase {
    return fail(errorMsg.getErrorCode(), msg);
  }
  
  public static OptimusExceptionBase fault(ErrorMessage errorMsg, String msg) throws OptimusExceptionBase {
    return fault(errorMsg.getHttpCode(), errorMsg.getErrorCode(), msg);
  }
  
  public static OptimusExceptionBase fault(ErrorMessage errorMsg, Throwable e) throws OptimusExceptionBase {
    return fault(errorMsg.getHttpCode(), errorMsg.getErrorCode(), errorMsg.getErrorMessage(), e);
  }
  
  public static OptimusExceptionBase fail(ErrorMessage errorMsg) throws OptimusExceptionBase {
    if (errorMsg.getModule() != null)
      fail(errorMsg, (String)null); 
    return fail(errorMsg.getErrorCode(), errorMsg.getErrorMessage());
  }
  
  public static OptimusExceptionBase fault(ErrorMessage errorMsg) throws OptimusExceptionBase {
    if (errorMsg.getModule() != null)
      fault(errorMsg, (String)null); 
    return fault(errorMsg.getHttpCode(), errorMsg.getErrorCode(), errorMsg.getErrorMessage());
  }
  
  @Deprecated
  public static OptimusExceptionBase fail(String errCode, String errMsg) throws OptimusExceptionBase {
    OptimusExceptionBase exception = new OptimusExceptionBase(errCode, errMsg);
    exception.setFault(false);
    exception.setFromExceptionsMethod(true);
    return exception;
  }
  
  @Deprecated
  public static OptimusExceptionBase fault(String errCode, String errMsg) throws OptimusExceptionBase {
    return fault(500, errCode, errMsg);
  }
  
  @Deprecated
  public static OptimusExceptionBase fault(int httpCode, String errCode, String errMsg) throws OptimusExceptionBase {
    return fault(httpCode, errCode, errMsg, null);
  }
  
  @Deprecated
  public static OptimusExceptionBase fault(int httpCode, String errCode, String errMsg, Throwable e) throws OptimusExceptionBase {
    OptimusExceptionBase exception = new OptimusExceptionBase(errMsg, e);
    exception.setErrCode(errCode);
    exception.setHttpCode(httpCode);
    exception.setFault(true);
    exception.setFromExceptionsMethod(true);
    return exception;
  }
  
  public static OptimusExceptionBase fail(Throwable exception) throws OptimusExceptionBase {
    OptimusUnknownException optimusUnknownException = null;
    OptimusExceptionBase e = null;
    if (exception instanceof OptimusExceptionBase) {
      e = (OptimusExceptionBase)exception;
    } else {
      optimusUnknownException = new OptimusUnknownException(exception);
    } 
    optimusUnknownException.setFault(false);
    optimusUnknownException.setFromExceptionsMethod(true);
    return (OptimusExceptionBase)optimusUnknownException;
  }
  
  public static OptimusExceptionBase fault(Throwable exception) throws OptimusExceptionBase {
    OptimusUnknownException optimusUnknownException = null;
    OptimusExceptionBase e = null;
    if (exception instanceof OptimusExceptionBase) {
      e = (OptimusExceptionBase)exception;
    } else {
      optimusUnknownException = new OptimusUnknownException(exception);
    }
    optimusUnknownException.setFault(true);
    optimusUnknownException.setFromExceptionsMethod(true);
    return (OptimusExceptionBase)optimusUnknownException;
  }
  
  public static Map<String, Object> exceptionParams(Map<String, Object> params, OptimusExceptionBase e) {
    if (params == null)
      params = new HashMap<>();
    tryPutExceptionParams(params, "_exp_ErrCode", e.getErrCode());
    tryPutExceptionParams(params, "_exp_Message", e.getMessage());
    return params;
  }
  
  private static void tryPutExceptionParams(Map<String, Object> params, String expParamName, String expParamsValue) {
    if (expParamsValue == null)
      return; 
    if (expParamsValue.length() > 50)
      expParamsValue = expParamsValue.substring(0, 50); 
    params.put(expParamName, expParamsValue);
  }
  
  @Deprecated
  public static OptimusExceptionBase fail(String model, String errorCode, String errorMsg, String... msg) throws OptimusExceptionBase {
    errorMsg = fillMsgInfo(errorMsg, msg);
    OptimusExceptionBase exception = new OptimusExceptionBase(model, errorCode, errorMsg);
    exception.setFault(false);
    exception.setFromExceptionsMethod(true);
    return exception;
  }
  
  public static OptimusExceptionBase fail(ErrorMessage errorMessage, String... msg) throws OptimusExceptionBase {
    String module = errorMessage.getModule();
    String errorCode = errorMessage.getErrorCode();
    String errorMsg = errorMessage.getErrorMessage();
    return fail(module, errorCode, errorMsg, msg);
  }
  
  public static OptimusExceptionBase fault(String module, String errorCode, String errorMsg, Throwable e, String... msg) throws OptimusExceptionBase {
    errorMsg = fillMsgInfo(errorMsg, msg);
    OptimusExceptionBase exception = new OptimusExceptionBase(module, errorCode, errorMsg, e);
    exception.setFault(true);
    exception.setFromExceptionsMethod(true);
    exception.setErrCode(errorCode);
    return exception;
  }
  
  public static OptimusExceptionBase fault(ErrorMessage errorMessage, String... msg) throws OptimusExceptionBase {
    String module = errorMessage.getModule();
    String errorCode = errorMessage.getErrorCode();
    String errorMsg = errorMessage.getErrorMessage();
    return fault(module, errorCode, errorMsg, null, msg);
  }
  
  public static OptimusExceptionBase fault(ErrorMessage errorMessage, Throwable e, String... msg) throws OptimusExceptionBase {
    String module = errorMessage.getModule();
    String errorCode = errorMessage.getErrorCode();
    String errorMsg = errorMessage.getErrorMessage();
    return fault(module, errorCode, errorMsg, e, msg);
  }
  
  private static String fillMsgInfo(String errorMsg, String... msg) {
    if (errorMsg != null && errorMsg.contains("{}"))
      for (String string : msg)
        errorMsg = errorMsg.replaceFirst("\\{}", string);  
    return errorMsg;
  }
}