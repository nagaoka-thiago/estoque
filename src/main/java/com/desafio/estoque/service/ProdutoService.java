package com.desafio.estoque.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desafio.estoque.dto.MensagemDTO;
import com.desafio.estoque.exception.ProdutoNotFoundException;
import com.desafio.estoque.model.Produto;
import com.desafio.estoque.repository.ComponenteRepository;
import com.desafio.estoque.repository.IngredienteRepository;
import com.desafio.estoque.repository.ProdutoRepository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor(onConstructor = @__({@Autowired}))
@NoArgsConstructor
@Service
public class ProdutoService {
	private ProdutoRepository repository;
	
	public List<Produto> getAll() {
		return this.repository.findAll();
	}

	public Produto getById(Integer id) throws ProdutoNotFoundException{
		Produto produto = verifyExistProduto(id);
		return produto;
	}
	
	public MensagemDTO criarProduto(Produto produto) {
		this.repository.save(produto);
		return getMensagem("Produto " + produto.getId() + " inserido com sucesso!");
		
	}
	
	public MensagemDTO atualizarProduto(Integer id, Produto produto) throws ProdutoNotFoundException{
		verifyExistProduto(id);
		
		this.repository.save(produto);
		return getMensagem("Produto " + produto.getId() + " atualizado com sucesso!");
	}
	
	public void deletarProduto(Integer id) throws ProdutoNotFoundException {
		Produto produto = verifyExistProduto(id);

		this.repository.delete(produto);
	}
	
	private Produto verifyExistProduto(Integer id) throws ProdutoNotFoundException{
		return this.repository.findById(id).orElseThrow(() -> new ProdutoNotFoundException(id));
	}
	
	private MensagemDTO getMensagem(String mensagem) {
		return MensagemDTO.builder()
							.mensagem(mensagem)
						  .build();
	}
}
