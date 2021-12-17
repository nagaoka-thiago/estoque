package com.desafio.estoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desafio.estoque.model.Ingrediente;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Integer>{
}
