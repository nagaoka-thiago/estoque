package com.desafio.estoque.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desafio.estoque.dto.MensagemDTO;
import com.desafio.estoque.exception.ProdutoNotFoundException;
import com.desafio.estoque.model.Componente;
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
		
		List<Componente> componentes = produto.getComponentes();
		List<Ingrediente> ingredientes = componentes.stream()
												.map(c -> c.getId().getIngrediente())
												.collect(Collectors.toList());
		this.ingredienteRepository.saveAll(ingredientes); // Primeiramente salvo os ingredientes do produto.
		produto.setComponentes(null);
		
		Produto newProduto = this.produtoRepository.save(produto); // Depois salvo o próprio produto sem os componentes.
		
		componentes.stream().forEach(c -> c.getId().setProduto(newProduto)); // Seto o produto salvo nos ComponenteId dos Componentes.
		
		this.componenteRepository.saveAll(componentes); // Salvo os componentes.
		
		return getMensagem("Produto " + produto.getId() + " inserido com sucesso!");
		
	}
	
	public MensagemDTO atualizarProduto(Integer id, Produto produto) throws ProdutoNotFoundException{
		verifyExistProduto(id);
		
		this.produtoRepository.save(produto); // Apenas atualizo os campos do Produto, como o nome e imagem_url.
		return getMensagem("Produto " + produto.getId() + " atualizado com sucesso!");
	}
	
	public void deletarProduto(Integer id) throws ProdutoNotFoundException {
		Produto produto = verifyExistProduto(id);
		
		produto.getComponentes().stream()
									.forEach(componenteRepository::delete); // Primeiro deleto os componentes.
		produto.getComponentes().stream()
									.map(c -> c.getId().getIngrediente())
									.forEach(ingredienteRepository::delete); // Depois deleto os ingredientes que compõem o produto a ser deletado.

		this.produtoRepository.delete(produto); // Por último, deleto o produto.
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
