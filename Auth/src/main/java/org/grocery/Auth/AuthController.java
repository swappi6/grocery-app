package org.grocery.Auth;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
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
@Path("/v1/auth")
@Component
public class AuthController {
    
    @Autowired
    AuthService authService;
    
    @PUT
    @UnitOfWork
    @Path("/refresh_access_token")
    public Response updateCityRegion(@Valid String refreshToken) throws GroceryException{
        ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok();
        AuthTokens tokens = authService.refreshAccessToken(refreshToken);
        return responseBuilder.entity(tokens)
                .build();
    }
    
    @PUT
    @UnitOfWork
    @Path("/tokens")
    public Response getTokens() throws GroceryException{
        ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok();
        List<AuthToken> tokens = authService.findall();
        return responseBuilder.entity(tokens)
                .build();
    }
    
}
