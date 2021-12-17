package com.desafio.estoque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.estoque.dto.MensagemDTO;
import com.desafio.estoque.model.Ingrediente;
import com.desafio.estoque.service.IngredienteService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor(onConstructor = @__({@Autowired}))
@NoArgsConstructor
@RestController
@RequestMapping("/api/v1/ingredientes")
public class IngredienteController {
	
	private IngredienteService service;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public MensagemDTO criarIngrediente(@RequestBody Ingrediente ingrediente) {
		return this.service.criarIngrediente(ingrediente);
	}
	
	@GetMapping
	public List<Ingrediente> getAll() {
		return this.service.getAll();
	}
}
