package com.sims.common.exception;

public class ServiceException extends Exception {
	private static final long serialVersionUID = 2378887843446159790L;
	
	protected String errorCode;
	protected String errorMessage;	
	
	public ServiceException() {
		super();
	}
	
	public ServiceException(String errorMessage,Throwable e) {
   	 	super(e);
   	 	this.errorMessage = errorMessage;
    }
	
	public ServiceException(String errorMessage) {
		super(errorMessage);
   	 	this.errorMessage = errorMessage;
    }
	
	public ServiceException(String errorCode,String errorMessage,Throwable e) {
   	 	super(e);
   	 	this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
	
	public ServiceException(String errorCode,String errorMessage) {
		super(errorMessage);
   	 	this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
	 
	public ServiceException(int errorCode,String errorMessage) {
		super(errorMessage);
   	 	this.errorCode = String.valueOf(errorCode);
        this.errorMessage = errorMessage;
    }
    
    
    public ServiceException(String errorCode,String errorMessage,Object serviceObj,Throwable e) {
   	 	super(e);
   	 	this.errorCode = errorCode;
   	 	this.errorMessage = errorMessage;
   }
    
    public ServiceException(String errorCode,String errorMessage,Object serviceObj) {
    	super(errorMessage);
   	 	this.errorCode = errorCode;
   	 	this.errorMessage = errorMessage;
   }
 
    public String toString() {
         return errorMessage;
    }
 
    public String getMessage() {
         return errorMessage;
    }

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
