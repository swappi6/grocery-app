package org.grocery.Address;

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

@Path("/v1/address")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Component
public class AddressController {

	@Autowired
	AddressService addressService;
	
	@GET
	@UnitOfWork
	@Path("/hello/{name}")
	public Response sample( @PathParam(value = "name") String name) {
        return Response.ok(name +" hello ").build();
    }
	
	@GET
    @UnitOfWork
    @Path("/get-all-address")
    public Response getAllAddress() throws Exception{
        ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok();
        List<Address> address = addressService.getAllAddress();
        return responseBuilder.entity(address)
                .build();
    }
	
	@POST
	@UnitOfWork
	@Path("/create-address")
	public Response createAddress(@Valid AddressData addressData) throws GroceryException {
	        ResponseBuilder responseBuilder = Response.noContent();
	        addressService.createAddress(addressData);
	        return responseBuilder.build();
	}
	
	@DELETE
	@UnitOfWork
	@Path("/delete-address/{addressId}")
	public Response deleteAddress(@PathParam(value = "addressId") Long addressId) throws GroceryException {
	        ResponseBuilder responseBuilder = Response.noContent();
	        addressService.delete(addressId);
	        return responseBuilder.build();
    }
	
	@GET
	@UnitOfWork
	@Path("/get-by-userid/{userId}")
	public Response getBYUserId(@PathParam(value = "userId") Long userId) throws GroceryException, Exception {
		  ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok();
		  List<Address> address = addressService.getByUserId(userId);
		  return responseBuilder.entity(address)
	                .build();
	}
	
	@PUT
	@UnitOfWork
	@Path("/update-address/{addressId}")
	public Response updateAddress(AddressData addressData, @PathParam(value = "addressId") Long addressId) throws GroceryException {
	        ResponseBuilder responseBuilder = Response.noContent();
	        addressService.updateAddress(addressData, addressId);
	        return responseBuilder.build();
	}
	
}