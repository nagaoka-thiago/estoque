package com.desafio.estoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desafio.estoque.embedded.ComponenteId;
import com.desafio.estoque.model.Componente;

public interface ComponenteRepository extends JpaRepository<Componente, ComponenteId>{

}
