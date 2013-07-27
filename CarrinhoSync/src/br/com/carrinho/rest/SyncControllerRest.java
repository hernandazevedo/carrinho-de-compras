package br.com.carrinho.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.carrinho.entidades.Produto;
import br.com.carrinho.rest.bean.RespostaSync;
import br.com.carrinho.services.SyncService;
@Named
@RequestScoped
@Path("/sync")
public class SyncControllerRest {

	@Inject
	SyncService syncService;

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
	@GET
	@Path("/{imei}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response processSync(@PathParam("imei") String imei) {
 
		
		
//		return Response.status(200).entity(syncService.retornaSync(imei)).build();
		
		RespostaSync respostaSync = new RespostaSync();
		
		List<Produto> listaProduto = new ArrayList<Produto>();
		Produto produto = new Produto();
		produto.setCodigoBarras("123456");
		produto.setDataAlteracao(new Date());
		produto.setDataCriacao(new Date());
		produto.setNome("Teste produto");
		produto.setParceiroId(1L);
		produto.setUrlImagem("http://t3.gstatic.com/images?q=tbn:ANd9GcQTbYC6J9Dj1jWFyGCATwb2-3Nbjbyq300ZmE0IajLRobPayBB56A");
		listaProduto.add(produto);
		respostaSync.setListaProduto(listaProduto);
		
		return Response.status(200).entity(respostaSync).build();
 
	}
}
