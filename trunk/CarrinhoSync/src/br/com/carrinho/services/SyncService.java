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

	
	/**
	 * Metodo que realiza o sincronismo do celular
	 * Exemplo de chamada: http://localhost:8080/CarrinhoSync/rest/sync/350845555897085
	   Exemplo de resposta:
		{
		codigoStatus: 0,
		messageStatus: null,
		listaProduto: [
		{
		codigoBarras: "12345",
		nome: "Produto teste",
		urlImagem: "http://t3.gstatic.com/images?q=tbn:ANd9GcQTbYC6J9Dj1jWFyGCATwb2-3Nbjbyq300ZmE0IajLRobPayBB56A",
		preco: 5,
		parceiroId: 1,
		dataAlteracao: 1374785386000,
		dataCriacao: 1374785386000
		}
		],
		listaParceiro: null
		}
	 * @param imei
	 * @return
	 */
	public RespostaSync retornaSync(String imei){
		System.out.println("Sincronizando o imei: "+imei);
		
		
		//TODO fazer a logica de busca baseado no imei, verificar o ultimo sync deste imei e retornar apenas os dados necessarios
		RespostaSync resposta = new RespostaSync();
		try{
			// TODO implementar a busca por parceiros por imei
			List<Parceiro> listaParceiros = null; 
			List<Produto> listaProdutos = em.createQuery("select p from Produto p order by p.nome",Produto.class).getResultList();
			
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
