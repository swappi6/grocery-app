package org.grocery.Address;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.dropwizard.hibernate.UnitOfWork;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/v1/address")
@Component
public class AddressController {

	@Autowired
	AddressService addressService;
	
	@GET
	@UnitOfWork
	@Path("/hello/{name}")
	public Response sample( @PathParam(value = "name") String name) {
        return Response.ok(name +" ki ki chuut").build();
    }
	
	
}