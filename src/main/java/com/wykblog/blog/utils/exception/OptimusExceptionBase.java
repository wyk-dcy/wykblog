package com.wykblog.blog.utils.exception;

public class OptimusExceptionBase extends RuntimeException {
  private static final long serialVersionUID = 1L;
  
  private String module;
  
  private int httpCode = 500;
  
  private String errCode = "" + ErrorCodes.UNKNOW_EXCEPTION.getHttpCode();
  
  private boolean fault;
  
  private String traceId;
  
  private boolean fromExceptionsMethod;
  
  private String appName;
  
  public OptimusExceptionBase() {
    setupTraceInfo();
  }
  
  public OptimusExceptionBase(int httpCode, String errCode) {
    this.httpCode = httpCode;
    this.errCode = errCode;
    setupTraceInfo();
  }
  
  public OptimusExceptionBase(String errCode, String errorMsg) {
    super(errorMsg);
    this.errCode = errCode;
    setupTraceInfo();
  }
  
  public OptimusExceptionBase(int httpCode, String errCode, String errorMsg, Throwable e) {
    super(errorMsg, e);
    this.httpCode = httpCode;
    this.errCode = errCode;
    setupTraceInfo();
  }
  
  public OptimusExceptionBase(int httpCode, String errCode, String errorMsg) {
    super(errorMsg);
    this.httpCode = httpCode;
    this.errCode = errCode;
    setupTraceInfo();
  }
  
  public OptimusExceptionBase(String errorMsg) {
    super(errorMsg);
    setupTraceInfo();
  }
  
  public OptimusExceptionBase(String errorMsg, Throwable cause) {
    super(errorMsg, cause);
    setupTraceInfo();
  }
  
  public OptimusExceptionBase(Throwable cause) {
    super(cause);
    setupTraceInfo();
  }
  
  public OptimusExceptionBase(String module, String errCode, String errorMsg) {
    super(errorMsg);
    this.errCode = errCode;
    this.module = module;
    setupTraceInfo();
  }
  
  public OptimusExceptionBase(String module, String errCode, String errorMsg, Throwable cause) {
    super(errorMsg, cause);
    this.errCode = errCode;
    this.module = module;
    setupTraceInfo();
  }
  
  protected void setupTraceInfo() {
  }
  
  public int getHttpCode() {
    return this.httpCode;
  }
  
  public void setHttpCode(int httpCode) {
    this.httpCode = httpCode;
  }
  
  public String getErrCode() {
    return this.errCode;
  }
  
  public void setErrCode(String errCode) {
    this.errCode = errCode;
  }
  
  public boolean isFault() {
    return this.fault;
  }
  
  public void setFault(boolean isFault) {
    this.fault = isFault;
  }
  
  public String getTraceId() {
    return this.traceId;
  }
  
  public String getAppName() {
    return this.appName;
  }
  
  public boolean isFromExceptionsMethod() {
    return this.fromExceptionsMethod;
  }
  
  public void setFromExceptionsMethod(boolean fromExceptionsMethod) {
    this.fromExceptionsMethod = fromExceptionsMethod;
  }
  
  public static long getSerialVersionUID() {
    return 1L;
  }
  
  public String getModule() {
    return this.module;
  }
  
  public void setModule(String module) {
    this.module = module;
  }
  
  public void setTraceId(String traceId) {
    this.traceId = traceId;
  }
  
  public void setAppName(String appName) {
    this.appName = appName;
  }
}