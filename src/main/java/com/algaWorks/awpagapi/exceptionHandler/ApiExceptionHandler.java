package com.algaWorks.awpagapi.exceptionHandler;

import java.net.URI;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaWorks.awpagapi.exception.NegocioException;

import lombok.AllArgsConstructor;

@AllArgsConstructor   
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{

	private final MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
	    ProblemDetail problemDetail = ProblemDetail.forStatus(status);
	    problemDetail.setTitle("Um ou mais campos estão inválidos");
	    problemDetail.setType(URI.create("https://localhost://algaworks.com/erros/copos-invalidos"));
	    
	    var fields =  ex.getBindingResult().getAllErrors().stream()
	    .collect(Collectors.toMap(error -> 	((FieldError)error).getField(),
	    	      error -> messageSource.getMessage(error, LocaleContextHolder.getLocale())));
	    
	    problemDetail.setProperty("fields", fields);
	    
		return super.handleExceptionInternal(ex, problemDetail, headers, status, request);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<String> capturar(NegocioException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ProblemDetail handleDataIntegrityVioletion(DataIntegrityViolationException e) {
		 ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
		    problemDetail.setTitle("Recurso em uso");
		    problemDetail.setType(URI.create("https://localhost://algaworks.com/erros/recurso-em-uso"));
		   
		    
			return problemDetail;
	}
}
