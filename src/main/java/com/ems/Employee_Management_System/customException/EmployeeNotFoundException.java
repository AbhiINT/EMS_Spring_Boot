package com.ems.Employee_Management_System.customException;

public class EmployeeNotFoundException extends RuntimeException {
    
    String ErrorDetails;
    public String getErrorDetails() {
        return ErrorDetails;
    }
    public EmployeeNotFoundException(String msg,String errorDetails)
    {
        super(msg);
        this.ErrorDetails=errorDetails;
    }
    public EmployeeNotFoundException(String msg,Throwable cause)
    {
        super(msg,cause);
    }
    @Override
    public StackTraceElement[] getStackTrace() {
        return new StackTraceElement[0]; 
    }

    
}
