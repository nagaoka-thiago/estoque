package com.desafio.estoque.embedded;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.desafio.estoque.model.Ingrediente;
import com.desafio.estoque.model.Produto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable

// Classe criada por causa de dois campos pertencerem a chave primária.
public class ComponenteId implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name = "id_produto")
	private Produto produto;
	
	@OneToOne
	@JoinColumn(name = "id_ingrediente")
	private Ingrediente ingrediente;
}
