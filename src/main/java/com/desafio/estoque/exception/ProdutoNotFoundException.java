package com.desafio.estoque.exception;

public class ProdutoNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public ProdutoNotFoundException(Integer id) {
		super("Produto com ID " + " não foi encontrado!");
	}
}
