package com.desafio.estoque.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IngredienteNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public IngredienteNotFoundException(Integer id) {
		super("Ingrediente com ID " + id + " não foi encontrado!");
	}

}
