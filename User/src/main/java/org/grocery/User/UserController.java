package org.grocery.User;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.grocery.Auth.Auth;
import org.grocery.Auth.AuthTokens;
import org.grocery.Error.GroceryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.dropwizard.hibernate.UnitOfWork;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/v1/user")
@Component
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GET
    @UnitOfWork
    @Path("/hello/{name}")
    public Response sample( @PathParam(value = "name") String name) {
        return Response.ok(name +" ki ki chuut").build();
    }
    
    @POST
    @UnitOfWork
    @Path("/request_otp/{mobileNo}")
    public Response requestOtp( @PathParam(value = "mobileNo") String mobileNo) {
        ResponseBuilder responseBuilder = javax.ws.rs.core.Response.noContent();
        userService.sendOtp(mobileNo);
        return responseBuilder.build();
    }
    
    @PUT
    @UnitOfWork
    @Path("/validate_otp/{mobileNo}")
    public Response validateOtp(@PathParam(value = "mobileNo") String mobileNo,
            @NotNull @QueryParam(value = "otp") String otp, 
            @NotNull @QueryParam(value = "deviceId") String deviceId) throws GroceryException{
        ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok();
        AuthTokens tokens = userService.validateOtp(mobileNo, otp, deviceId);
        return responseBuilder.entity(tokens)
                .build();
    }
    
    @PUT
    @UnitOfWork
    @Auth
    @Path("/update_user_profile")
    public Response updateUserProfile(@Valid UserProfile userProfile, @Context ContainerRequestContext context) 
            throws GroceryException {
        ResponseBuilder responseBuilder = javax.ws.rs.core.Response.noContent();
        Long userId = (Long) context.getProperty("userId");
        userService.updateUser(userId, userProfile);
        return responseBuilder.build();
    }
    
    @GET
    @UnitOfWork
    @Auth
    @Path("/get_user_profile")
    public Response getUserProfile(@Context ContainerRequestContext context) 
            throws GroceryException {
        ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok();
        Long userId = (Long) context.getProperty("userId");
        UserProfile user = userService.getUser(userId);
        return responseBuilder.entity(user)
                .build();
    }

}
