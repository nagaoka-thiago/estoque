package com.desafio.estoque.exception;

public class UsuarioNotFoundException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public UsuarioNotFoundException(String cpf) {
		super("Usuario " + cpf + " não foi encontrado!");
	}
}
