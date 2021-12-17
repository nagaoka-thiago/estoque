package com.desafio.estoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desafio.estoque.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String>{
	Usuario findByEmail(String email);
}
