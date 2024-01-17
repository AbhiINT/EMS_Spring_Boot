package com.ems.Employee_Management_System.customException;

public class DuplicateDataFoundException extends RuntimeException {

    public DuplicateDataFoundException(String msg)
    {
        super(msg);
    }
    public DuplicateDataFoundException(String msg,Throwable cause)
    {
        super(msg,cause);
    }
    @Override
    public StackTraceElement[] getStackTrace() {
        return new StackTraceElement[0]; 
    }

    
}
