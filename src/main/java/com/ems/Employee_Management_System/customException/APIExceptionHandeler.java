package com.ems.Employee_Management_System.customException;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClient.ResponseSpec;

@ControllerAdvice
public class APIExceptionHandeler {
    @ExceptionHandler(value={EmployeeNotFoundException.class})
    public ResponseEntity<Object> handleApiRequestException(EmployeeNotFoundException ex)
    {
        ApiException apiException=new ApiException(ex.getMessage(),ex.ErrorDetails,"For Help please visit https://www.help.com", HttpStatus.BAD_REQUEST, ZonedDateTime.now(ZoneId.of("Z")));

        return new ResponseEntity<>(apiException,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value={DuplicateDataFoundException.class})
    public ResponseEntity<Object> handleDuplicateDatarequest(DuplicateDataFoundException ex)
    {
        ApiException apiException =new ApiException(ex.getMessage(),"The Data you are Trying to add is Already exist!","for help please Visit https://www.help.com", HttpStatus.CONFLICT, ZonedDateTime.now(ZoneId.of("Z")));
        return  new ResponseEntity<>(apiException,HttpStatus.CONFLICT);
    }
  
    
}
