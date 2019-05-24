package org.grocery.item;

import java.util.List;
import java.util.Set;

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
import org.grocery.category.Category;
import org.grocery.category.CategoryService;
import org.grocery.response.CategoryItems;
import org.grocery.response.SearchResponse;
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
    
    @Autowired
    CategoryService categoryService;
    
    @GET
    @UnitOfWork
    @Path("/category-items")
    public Response getCategoryItems(@QueryParam(value = "parent") Long parent) throws Exception{
        ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok();
        CategoryItems response = new CategoryItems();
        List<Item> items = itemService.findByCategory(parent);
        List<Category> subCategories =categoryService.findByCategory(parent);
        response.setItems(items);
        response.setSubCategories(subCategories);
        return responseBuilder.entity(response)
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
    
    @GET
    @UnitOfWork
    @Path("/search")
    public Response search(@QueryParam(value = "param") String param) throws GroceryException{
        ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok();
        SearchResponse response = new SearchResponse();
        Set<Item> items = itemService.searchItem(param);
        response.setItems(items);
        return responseBuilder.entity(response)
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