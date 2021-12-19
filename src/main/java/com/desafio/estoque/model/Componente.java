package com.desafio.estoque.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.desafio.estoque.embedded.ComponenteId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "componentes")
public class Componente {
	@EmbeddedId
	private ComponenteId id;
	
	@Column
	private Integer quantidade;
}
