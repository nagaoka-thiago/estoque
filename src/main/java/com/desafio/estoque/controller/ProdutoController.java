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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.estoque.dto.MensagemDTO;
import com.desafio.estoque.exception.ProdutoNotFoundException;
import com.desafio.estoque.model.Produto;
import com.desafio.estoque.service.ProdutoService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor(onConstructor = @__({@Autowired}))
@NoArgsConstructor
@RestController
@RequestMapping("/api/v1/produtos")
public class ProdutoController {
	private ProdutoService service;
	
	@GetMapping
	public List<Produto> getAll() {
		return this.service.getAll(); // Lista todos os produtos e seus ingredientes.
	}
	
	@GetMapping("/{id}")
	public Produto getById(@PathVariable("id") Integer id) throws ProdutoNotFoundException{
		return this.service.getById(id); // Retorna o produto com esse id, se não encontra retorna um erro.
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public MensagemDTO criarProduto(@RequestBody Produto produto) {
		return this.service.criarProduto(produto); /* Insere um novo produto, mas como havia colocado cascade para que os ingredientes
		 											fossem inseridos automaticamente, não está funcionando. */
	}
	
	@PutMapping("/{id}")
	public MensagemDTO atualizarProduto(@PathVariable("id") Integer id, @RequestBody Produto produto) throws ProdutoNotFoundException {
		return this.service.atualizarProduto(id, produto); /* Atualiza um produto, mas não está funcionando pelo mesmo motivo do anterior. */
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletarProduto(@PathVariable("id") Integer id) throws ProdutoNotFoundException {
		this.service.deletarProduto(id); /* Deleta um produto, mas não está funcionando. */
	}
}
