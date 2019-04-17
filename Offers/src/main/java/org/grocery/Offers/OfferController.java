package org.grocery.Offers;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.grocery.Error.GroceryException;
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
    @Path("/get-all-offers")
    public Response getAllOffers() throws Exception{
        ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok();
        List<Offer> offers = offerService.getAllOffers();
        return responseBuilder.entity(offers)
                .build();
    }
	
	@GET
	@UnitOfWork
	@Path("/get-valid-offers")
	public Response getAllValid()
	{
		ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok();
		List<Offer> offers = offerService.getValidOffers();
		 
		return responseBuilder.entity(offers)
	                .build();
	}
	
	
	@POST
	@UnitOfWork
	@Path("/create-offer")
	public Response createOffer(@Valid OfferData offerData) throws GroceryException {
	        ResponseBuilder responseBuilder = Response.noContent();
	        offerService.createOffer(offerData);
	        return responseBuilder.build();
	    }
	
	 @PUT
	 @UnitOfWork
	 @Path("/update-offer/{offerId}")
	    public Response updateOffer(OfferData offerData, @PathParam(value = "offerId") Long offerId) throws GroceryException {
	        ResponseBuilder responseBuilder = Response.noContent();
	        offerService.updateOffer(offerData, offerId);
	        return responseBuilder.build();
	    }
	    
	 @DELETE
	 @UnitOfWork
	 @Path("/delete-offer/{offerId}")
	    public Response deleteOffer(@PathParam(value = "offerId") Long offerId) throws GroceryException {
	        ResponseBuilder responseBuilder = Response.noContent();
	        offerService.delete(offerId);
	        return responseBuilder.build();
	    }
	
}
