package org.grocery.admin;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.grocery.Error.GroceryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.dropwizard.hibernate.UnitOfWork;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/v1/admin")
@Component
public class AdminController {
	
	@Autowired
    private AdminService adminService;
	
	@POST
    @UnitOfWork
    @Path("/user")
    public Response createUser(@Valid AdminProfile adminProfile) 
            throws GroceryException {
		System.out.println(adminProfile);
        ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok();
        adminService.createUser(adminProfile);
        return responseBuilder.entity(adminProfile)
     		   .build();
    }
	
	@POST
    @UnitOfWork
    @Path("/login")
    public Response getUserProfile(@Valid LoginProfile loginDetails) 
            throws GroceryException, JSONException {
       ResponseBuilder responseBuilder = Response.ok(); 
       AdminProfile adminProfile = adminService.getUser(loginDetails);
       return responseBuilder.entity(adminProfile)
    		   .build();
    }
    
	
}
