package com.desafio.estoque.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.desafio.estoque.model.Usuario;
import com.desafio.estoque.repository.UsuarioRepository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor(onConstructor = @__({@Autowired}))
@NoArgsConstructor
@Repository
public class UserDetailServiceImpl implements UserDetailsService{

	private UsuarioRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Usuario usuario = this.repository.findByEmail(email);
		
		if(usuario == null) {
			throw new UsernameNotFoundException("Usuário com e-mail " + email + " não encontrado!");
		}
		
		return usuario;
	}
	
}
