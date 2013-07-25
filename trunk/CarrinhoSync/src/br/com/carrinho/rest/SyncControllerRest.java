package br.com.carrinho.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.carrinho.services.SyncService;
@Named
@RequestScoped
@Path("/sync")
public class SyncControllerRest {

	@Inject
	SyncService syncService;

	@GET
	@Path("/{imei}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response processSync(@PathParam("imei") String imei) {
 
		
		
		return Response.status(200).entity(syncService.retornaSync(imei)).build();
 
	}
}
