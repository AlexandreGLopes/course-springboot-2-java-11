package com.educandoweb.course.services.exceptions;

// Esta classe será uma subclasse da RuntimeException. Que é a exceção que o compilador não te obriga a tratar
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	// Vamos passar o id do objeto que tentamos encontrar e não foi encontrado
	// No construtor da superclasse vamos colocar a mensagem padrão da exceção personalizada
	public ResourceNotFoundException(Object id) {
		super("Resource not found. Id " + id);
	}

}
