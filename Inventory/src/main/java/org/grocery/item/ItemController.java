package org.grocery.item;

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
    @Path("/category-items")
    public Response getCategoryItems(@QueryParam(value = "parent") Long parent) throws Exception{
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
    @Path("/create-item")
    public Response createItem(@Valid ItemData itemData) throws GroceryException {
        ResponseBuilder responseBuilder = Response.noContent();
        itemService.createItem(itemData);
        return responseBuilder.build();
    }
    
    @PUT
    @UnitOfWork
    @Path("/update-item/{itemId}")
    public Response updateItem(ItemData itemData, @PathParam(value = "itemId") Long itemId) throws GroceryException {
        ResponseBuilder responseBuilder = Response.noContent();
        itemService.updateItem(itemData, itemId);
        return responseBuilder.build();
    }
    
    @DELETE
    @UnitOfWork
    @Path("/delete-item/{itemId}")
    public Response deleteItem(@PathParam(value = "itemId") Long itemId) throws GroceryException {
        ResponseBuilder responseBuilder = Response.noContent();
        itemService.delete(itemId);
        return responseBuilder.build();
    }
}
