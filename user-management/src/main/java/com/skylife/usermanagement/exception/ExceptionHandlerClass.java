package com.skylife.usermanagement.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerClass {
	Logger logger = LoggerFactory.getLogger(ExceptionHandlerClass.class);
	
	
	@ExceptionHandler(value = UserNotFoundException.class)
	ResponseEntity<Object> handleException(UserNotFoundException exc){
		 return new ResponseEntity<>(exc.getMessage(), HttpStatus.NOT_FOUND);
		
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public Map<String,String> handleInvalidInput(MethodArgumentNotValidException exc){
		Map<String,String> errorMap = new HashMap<>();
		exc.getBindingResult().getFieldErrors().forEach(error->{errorMap.put(error.getField(), error.getDefaultMessage());});
		return errorMap;
		
	}
	
	@ExceptionHandler(value = AccessDeniedException.class)
	ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException exc){
		 return new ResponseEntity<>(exc.getMessage(),HttpStatus.FORBIDDEN);
		
	}
	
	@ExceptionHandler(value = Exception.class)
	ResponseEntity<Object> handleException(Exception exc){
		logger.error(exc.getMessage());
		 return new ResponseEntity<>("Something went wrong , Please check logs or try again later", HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	

}
