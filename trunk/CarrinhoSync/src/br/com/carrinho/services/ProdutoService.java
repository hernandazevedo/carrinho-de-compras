package br.com.carrinho.services;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import br.com.carrinho.entidades.Produto;
@Named
@RequestScoped
public class ProdutoService {

	
	
	@Inject
	EntityManager em;

	
	public Produto get(String codigoBarras){
		
		return em.find(Produto.class, codigoBarras);
	}
}
