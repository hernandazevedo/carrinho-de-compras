package br.com.carrinho.services;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import br.com.carrinho.entidades.Parceiro;
@Named
@RequestScoped
public class ParceiroService {

	
	
	@Inject
	EntityManager em;

	
	public Parceiro get(Long parceiroId){
		
		return em.find(Parceiro.class, parceiroId);
	}
}
