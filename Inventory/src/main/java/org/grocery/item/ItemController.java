package org.grocery.item;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.grocery.Error.GroceryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.dropwizard.hibernate.UnitOfWork;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/v1/item")
@Component
public class ItemController {
    
    @Autowired
    ItemService itemService;
    
    @GET
    @UnitOfWork
    @Path("/categoryItems")
    public Response getCategoryItems(@QueryParam(value = "parent") String parent) throws Exception{
        ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok();
        List<Item> items = itemService.findByCategory(parent);
        return responseBuilder.entity(items)
                .build();
    }
    
    @GET
    @UnitOfWork
    @Path("/items")
    public Response getAllItems() throws Exception{
        ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok();
        List<Item> items = itemService.getAllItems();
        return responseBuilder.entity(items)
                .build();
    }
    
    @POST
    @UnitOfWork
    @Path("/createItem")
    public Response updateUserProfile(@Valid ItemData itemData) throws GroceryException {
        ResponseBuilder responseBuilder = Response.noContent();
        itemService.createCategory(itemData);
        return responseBuilder.build();
    }
}
