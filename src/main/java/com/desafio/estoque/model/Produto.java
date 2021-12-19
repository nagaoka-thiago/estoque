package com.desafio.estoque.model;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Produto.class)
public class Produto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String nome;
	
	@Column
	private String imagem_url;
	
	@OneToMany
	@JoinColumn(name = "id_produto")
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
