package com.desafio.estoque.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.desafio.estoque.dto.MensagemDTO;
import com.desafio.estoque.exception.UsuarioNotFoundException;
import com.desafio.estoque.model.Usuario;
import com.desafio.estoque.repository.UsuarioRepository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class UsuarioService {
	
	private UsuarioRepository repository;

	public List<Usuario> getAll() {
		return this.repository.findAll();
	}
	
	public Usuario getByCpf(String cpf) throws UsuarioNotFoundException {
		return verifyExistsUsuario(cpf);
	}
	
	public MensagemDTO criarUsuario(Usuario usuario) {
		this.repository.save(usuario);
		return getMensagem("Usuário " + usuario.getCpf() + " inserido com sucesso!");
	}
	
	public MensagemDTO atualizarUsuario(String cpf, Usuario usuario) throws UsuarioNotFoundException {
		verifyExistsUsuario(cpf);
		this.repository.save(usuario);
		return getMensagem("Usuario " + cpf + " foi atualizado com sucesso!");
	}
	
	public void deletarUsuario(String cpf) throws UsuarioNotFoundException {
		Usuario usuario = verifyExistsUsuario(cpf);
		this.repository.delete(usuario);
	}
	
	private Usuario verifyExistsUsuario(String cpf) throws UsuarioNotFoundException {
		return this.repository.findById(cpf).orElseThrow(() -> new UsuarioNotFoundException(cpf));
	}
	
	private MensagemDTO getMensagem(String mensagem) {
		return MensagemDTO.builder()
							.mensagem(mensagem)
						  .build();
	}
}
