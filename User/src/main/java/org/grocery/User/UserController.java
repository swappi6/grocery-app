package org.grocery.User;

import java.util.HashMap;
import java.util.Map;

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
    private Map<Integer, String> gaali = new HashMap();
    private Map<Integer, String> house = new HashMap();
    private Map<Integer, String> potter = new HashMap();
    private Map<Integer, String> pandav = new HashMap();
    
    
    @GET
    @UnitOfWork
    @Path("/hello/{name}")
    public Response sample( @PathParam(value = "name") String name) {
    	String responseString = null;
//    	gaali.put(0, "studd");
//    	gaali.put(1, "madar chod");
//    	gaali.put(2, "bahin chod");
//    	gaali.put(3, "gone case");
//    	gaali.put(4, "bhosdi waal");
//    	gaali.put(5, "boka choda");
//    	gaali.put(6, "good boy");
//    	gaali.put(7, "bak lund");
    	house.put(1, "Stark");
    	house.put(2, "Targeryen");
    	house.put(3, "Martell");
    	house.put(4, "Lannister");
    	house.put(5, "Tyrell");
    	house.put(6, "Wights");
    	house.put(7, "Northerner");
    	house.put(8, "Nights Watcher");
    	house.put(9, "Children of Forest");
    	house.put(10, "Second sons");
    	house.put(11, "Unsullied");
    	house.put(0, "Iron Born");
    	
    	potter.put(0, "Gryffindor");
    	potter.put(1, "Slytherin");
    	potter.put(2, "Ravenclaw");
    	potter.put(3, "Huffle puff");
    	
    	pandav.put(0, "Yudhisthir");
    	pandav.put(1, "Bhim");
    	pandav.put(2, "Arjun");
    	pandav.put(3, "Nakul");
    	pandav.put(4, "Sahdev");
    	pandav.put(5, "Shakuni");
    	pandav.put(6, "Duryodhan");
    	pandav.put(7, "Karn");
    	pandav.put(8, "Duhshashan");
    	
    	int hash = getHash(name);
    	int mod = hash%9;
    	if (mod <0)
    		mod= mod*-1;
    	responseString = pandav.get(mod);
    	if (name == "shashi")
    		responseString = "studd";
        return Response.ok(name +" You are a "+ responseString).build();
    }
    
    private int getHash(String name) {
    	int sum =0;
    	for (int i = 0; i< name.length(); i++) {
    		sum += name.charAt(i);
    	}
    	return sum;
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
