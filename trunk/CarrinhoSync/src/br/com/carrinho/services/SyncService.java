package br.com.carrinho.services;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import br.com.carrinho.entidades.Parceiro;
import br.com.carrinho.entidades.Produto;
import br.com.carrinho.rest.bean.RespostaSync;
@Named
@RequestScoped
public class SyncService {
	
	public static final int STATUS_OK = 0;
	public static final int STATUS_ERROR = -1;
	
	
	@Inject
	EntityManager em;

	
	public RespostaSync retornaSync(String imei){
		System.out.println("Sincronizando o imei: "+imei);
		
		
		//TODO fazer a logica de busca baseado no imei, verificar o ultimo sync deste imei e retornar apenas os dados necessarios
		RespostaSync resposta = new RespostaSync();
		try{
			// TODO implementar a busca por parceiros por imei
			List<Parceiro> listaParceiros = null; 
			List<Produto> listaProdutos = em.createQuery("select p from Produto p order by p.nome",Produto.class).getResultList();
			
			//TODO pensar em uma forma de cada objeto ter uma propriedade dizendo se eh operacao: insert, update ou delete.
			
			resposta.setListaProduto(listaProdutos);
			resposta.setListaParceiro(listaParceiros);
			resposta.setCodigoStatus(SyncService.STATUS_OK);
		}catch (Exception e) {
			resposta.setCodigoStatus(SyncService.STATUS_ERROR);
			resposta.setMessageStatus(e.getMessage());
		}
		
		return resposta;
	}
}
