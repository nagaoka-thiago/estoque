package com.desafio.estoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desafio.estoque.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer>{

}
