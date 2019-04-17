package org.grocery.admin;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
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
            throws GroceryException {
	    System.out.println("bjhbjb");
       ResponseBuilder responseBuilder = Response.ok(); 
       AdminProfile adminProfile = adminService.login(loginDetails);
       return responseBuilder.entity(adminProfile)
    		   .build();
    }
	
	@DELETE
    @UnitOfWork
    @Path("/logout")
    public Response logout(@Context ContainerRequestContext context) 
            throws GroceryException {
       ResponseBuilder responseBuilder = Response.ok(); 
       String accessToken = context.getHeaderString("ACCESS_TOKEN");
       adminService.logout(accessToken);
       return responseBuilder
               .build();
    }
    
	
}
