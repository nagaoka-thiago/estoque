package com.desafio.estoque.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
@Service
public class UsuarioService {
	
	private UsuarioRepository repository;
	private PasswordEncoder encoder;

	public List<Usuario> getAll() {
		return this.repository.findAll();
	}
	
	public Usuario getByCpf(String cpf) throws UsuarioNotFoundException {
		return verifyExistsUsuario(cpf);
	}
	
	public MensagemDTO criarUsuario(Usuario usuario) {
		usuario.setSenha(encoder.encode(usuario.getSenha())); // Criptografa a senha passada pelo usuário
		this.repository.save(usuario);
		return getMensagem("Usuário " + usuario.getCpf() + " inserido com sucesso!");
	}
	
	// Tomar cuidado ao atualizar o usuário para não mudar a senha.
	public MensagemDTO atualizarUsuario(String cpf, Usuario usuario) throws UsuarioNotFoundException {
		verifyExistsUsuario(cpf);
		this.repository.save(usuario);
		return getMensagem("Usuario " + cpf + " foi atualizado com sucesso!");
	}
	
	public void deletarUsuario(String cpf) throws UsuarioNotFoundException {
		Usuario usuario = verifyExistsUsuario(cpf);
		this.repository.delete(usuario);
	}
	
	public MensagemDTO logar(String email, String senha) {
		Usuario usuario = this.repository.findByEmail(email);
		if(usuario != null) {
			Boolean valido = encoder.matches(senha, usuario.getSenha()); // Verifica se a senha passada através do login bate com a senha criptografada no banco de dados.
			if(!valido) return getMensagem("Senha inválida!");
			else return getMensagem("Usuario com e-mail " + email + " logado!");
		}
		else return getMensagem("Usuario com e-mail " + email + " não existe!");
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
