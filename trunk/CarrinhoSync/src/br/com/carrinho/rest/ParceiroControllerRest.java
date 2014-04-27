package br.com.carrinho.rest;

import java.util.ArrayList;
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

import org.jboss.logging.Logger;

import br.com.carrinho.entidades.Parceiro;
import br.com.carrinho.rest.bean.RespostaSync;
import br.com.carrinho.services.ParceiroService;
@Named
@RequestScoped
@Path("/parceiro")
public class ParceiroControllerRest {

	
	@Inject ParceiroService parceiroService;
	
	Logger logger = Logger.getLogger(ParceiroControllerRest.class);

	
	@GET
	@Path("/{codigo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProduto(@PathParam("codigo") Long codigo) {
 
		
		
//		return Response.status(200).entity(syncService.retornaSync(imei)).build();
		
		logger.info("Buscando o parceiro: "+codigo);
		
		RespostaSync respostaSync = new RespostaSync();
		
		List<Parceiro> lista = new ArrayList<Parceiro>();
		
		lista.add(parceiroService.get(codigo));
		respostaSync.setListaParceiro(lista);
		
		return Response.status(200).entity(respostaSync).build();
 
	}
	
	
	
	
}
