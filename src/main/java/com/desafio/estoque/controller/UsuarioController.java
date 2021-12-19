package com.desafio.estoque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.estoque.dto.MensagemDTO;
import com.desafio.estoque.exception.UsuarioNotFoundException;
import com.desafio.estoque.model.Usuario;
import com.desafio.estoque.service.UsuarioService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(onConstructor = @__({@Autowired}))
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
	
	private UsuarioService service;
	
	@GetMapping
	public List<Usuario> getAll() {
		return this.service.getAll(); // Lista todos os usuários.
	}
	
	@GetMapping("/{cpf}")
	public Usuario getByCpf(@PathVariable("cpf") String cpf) throws UsuarioNotFoundException {
		return this.service.getByCpf(cpf); // Retorna o usuário com um determinado CPF.
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public MensagemDTO criarUsuario(@RequestBody Usuario usuario) {
		return this.service.criarUsuario(usuario); // Insere um novo usuário não administrador e criptografa a senha.
	}
	
	@PostMapping("/logar")
	public MensagemDTO logar(@RequestParam("email") String email, @RequestParam("senha") String senha) {
		return this.service.logar(email, senha); // Confere se o email e a senha passada são os registrados no banco de dados.
	}
	
	@PutMapping("/{cpf}")
	public MensagemDTO atualizarUsuario(@PathVariable("cpf") String cpf, @RequestBody Usuario usuario) throws UsuarioNotFoundException {
		return this.service.atualizarUsuario(cpf, usuario); // Atualiza o usuario, retorna erro se não existe.
	}
	
	@DeleteMapping("/{cpf}")
	public void deletarUsuario(@PathVariable("cpf") String cpf) throws UsuarioNotFoundException {
		this.service.deletarUsuario(cpf); // Deleta o usuario, retorna erro se não existe.
	}
}
