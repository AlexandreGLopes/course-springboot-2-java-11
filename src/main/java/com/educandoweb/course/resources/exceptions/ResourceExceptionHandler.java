package com.educandoweb.course.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.educandoweb.course.services.exceptions.ResourceNotFoundException;

// Annotation @ControllerAdvice vai interceptar as exceções que acontecerem para este objeto possa executar um possível tratamento
@ControllerAdvice
public class ResourceExceptionHandler {
	
	// Tratamento personalizado implementado da ResourceNotFoundException, para quando fizer a resquisição de um recurso que não está no banco
	// Com a annotation @ExceptionHandler estamos falando que o método resourceNotFound() vai interceptar qualquer ResourceNotFoundException lançada
	// Usa a classe StandardError que fizemos que é similiar ao erro padrão do servidor (Internal Server Error) pois montamos ela com os mesmos campos
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		String error = "Resource not found";
		// HttpStatus.NOT_FOUND é o que vai retornar o código 404 que é o padrão para recurso não encontrado
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
}
