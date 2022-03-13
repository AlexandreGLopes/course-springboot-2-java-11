package com.educandoweb.course.services.exceptions;

// Classe para personalizar a exceção do caso de violação da integrida do banco de dados. Tentativa de excluir um dado que esteja associado a mais de uma tabela 
public class DatabaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DatabaseException(String msg) {
		super(msg);
	}

}
