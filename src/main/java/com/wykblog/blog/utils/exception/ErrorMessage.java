package com.wykblog.blog.utils.exception;

public class ErrorMessage {
  private int httpCode;
  
  private String errorCode;
  
  private String errorMessage;
  
  private String module;
  
  private ErrorMessage(int httpCode, String errorCode, String errorMessage) {
    this.httpCode = httpCode;
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }
  
  private ErrorMessage(String module, String errorCode, String errorMessage) {
    this.module = module;
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }
  
  public static ErrorMessage errorMessage(String module, String errorCode, String errorMessage) {
    return new ErrorMessage(module, errorCode, errorMessage);
  }
  
  public static ErrorMessage errorMessage(int httpCode, String errorCode, String errorMessage) {
    return new ErrorMessage(httpCode, errorCode, errorMessage);
  }
  
  public static ErrorMessage errorMessage(String errorCode, String errorMessage) {
    return errorMessage(500, errorCode, errorMessage);
  }
  
  public static ErrorMessage errorMessage(int httpCode, String errorCode) {
    return errorMessage(500, errorCode, (String)null);
  }
  
  public int getHttpCode() {
    return this.httpCode;
  }
  
  public String getErrorCode() {
    return this.errorCode;
  }
  
  public String getErrorMessage() {
    return this.errorMessage;
  }
  
  public String getModule() {
    return this.module;
  }
}