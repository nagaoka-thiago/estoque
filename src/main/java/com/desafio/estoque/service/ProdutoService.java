package com.desafio.estoque.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desafio.estoque.dto.MensagemDTO;
import com.desafio.estoque.exception.ProdutoNotFoundException;
import com.desafio.estoque.model.Ingrediente;
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
	private ProdutoRepository produtoRepository;
	private ComponenteRepository componenteRepository;
	private IngredienteRepository ingredienteRepository;
	
	public List<Produto> getAll() {
		return this.produtoRepository.findAll();
	}

	public Produto getById(Integer id) throws ProdutoNotFoundException{
		Produto produto = verifyExistProduto(id);
		return produto;
	}
	
	public MensagemDTO criarProduto(Produto produto) {
		
		List<Ingrediente> ingredientes = produto.getComponentes().stream()
												.map(c -> c.getId().getIngrediente())
												.collect(Collectors.toList());
		ingredienteRepository.saveAll(ingredientes);
		
		this.produtoRepository.save(produto);
		
		produto.getComponentes().stream().forEach(c -> c.getId().getProduto().setId(produto.getId()));
		produto.getComponentes().stream()
								.map(componenteRepository::save);
		
		return getMensagem("Produto " + produto.getId() + " inserido com sucesso!");
		
	}
	
	public MensagemDTO atualizarProduto(Integer id, Produto produto) throws ProdutoNotFoundException{
		verifyExistProduto(id);
		
		this.produtoRepository.save(produto);
		return getMensagem("Produto " + produto.getId() + " atualizado com sucesso!");
	}
	
	public void deletarProduto(Integer id) throws ProdutoNotFoundException {
		Produto produto = verifyExistProduto(id);
		
		produto.getComponentes().stream()
									.forEach(componenteRepository::delete);
		produto.getComponentes().stream()
									.map(c -> c.getId().getIngrediente())
									.forEach(ingredienteRepository::delete);

		this.produtoRepository.delete(produto);
	}
	
	private Produto verifyExistProduto(Integer id) throws ProdutoNotFoundException{
		return this.produtoRepository.findById(id).orElseThrow(() -> new ProdutoNotFoundException(id));
	}
	
	private MensagemDTO getMensagem(String mensagem) {
		return MensagemDTO.builder()
							.mensagem(mensagem)
						  .build();
	}
}
