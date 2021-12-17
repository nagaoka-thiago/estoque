package com.desafio.estoque.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desafio.estoque.dto.MensagemDTO;
import com.desafio.estoque.model.Ingrediente;
import com.desafio.estoque.repository.IngredienteRepository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
@Data
@Builder
@AllArgsConstructor(onConstructor = @__({@Autowired}))
@NoArgsConstructor
public class IngredienteService {
	
	private IngredienteRepository repository;
	
	public MensagemDTO criarIngrediente(Ingrediente ingrediente) {
		this.repository.save(ingrediente);
		return getMensagem("Ingrediente " + ingrediente.getId() + " inserido com sucesso!");
	}
	
	public List<Ingrediente> getAll() {
		return this.repository.findAll();
	}
	
	private MensagemDTO getMensagem(String mensagem) {
		return MensagemDTO.builder()
							.mensagem(mensagem)
						  .build();
	}
}
