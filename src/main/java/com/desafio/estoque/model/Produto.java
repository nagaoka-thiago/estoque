package com.desafio.estoque.model;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "produtos")
public class Produto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String nome;
	
	@Column
	private String imagem_url;
	
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}) // Coloquei em cascata as operações de inserir, atualizar e deletar componentes,
																						// mas não funciona.
	@JoinColumn(name = "id_produto")
	@JsonManagedReference
	private List<Componente> componentes;
	
	// Calcula o preço do produto a partir dos ingredientes que o compõem.
	public Float getPreco() {
		Float sum = 0f;
		for(Float precoByIngrediente : this.componentes.stream().map(i -> i.getQuantidade() * i.getId().getIngrediente().getPreco_unitario()).collect(Collectors.toList())) {
			sum += precoByIngrediente;
		}
		
		return sum;
	}
	
}
