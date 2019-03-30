package org.grocery.Offers;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.dropwizard.hibernate.UnitOfWork;

@Path("/v1/offer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Component
public class OfferController {

	@Autowired
	OfferService offerService;
	
	@GET
	@UnitOfWork
	@Path("/hello/{name}")
	 public Response sample( @PathParam(value = "name") String name) {
        return Response.ok(name +" Hello Robin").build();
	}
	

	@GET
	@UnitOfWork
	@Path("/get-valid-offers")
	public Response getAllValid()
	{
		ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok();
		List<Offer> offers = offerService.getValid();
		 
		return responseBuilder.entity(offers)
	                .build();
	}
	
	
	
}
