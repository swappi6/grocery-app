package org.grocery.category;

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
import org.grocery.admin.filter.ReadAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.dropwizard.hibernate.UnitOfWork;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/v1/category")
@Component
public class CategoryController {
    
    @Autowired
    CategoryService categoryService;
    
    @GET
    @UnitOfWork
    @Path("/root-categories")
    public Response getRootCategories() throws Exception{
        ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok();
        List<Category> categories = categoryService.getParentCategories();
        return responseBuilder.entity(categories)
                .build();
    }
    

    @GET
    @UnitOfWork
    @Path("/categories")
    public Response getCategories() throws Exception{
        ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok();
        List<Category> categories = categoryService.getAllCategories();
        return responseBuilder.entity(categories)
                .build();
    }
    
    @GET
    @UnitOfWork
//    @ReadAuth
    @Path("/sub-category")
    public Response getSubCategories(@QueryParam(value = "parent") Long parent) throws Exception{
        ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok();
        List<Category> categories = categoryService.findByCategory(parent);
        return responseBuilder.entity(categories)
                .build();
    }
    
    @POST
    @UnitOfWork
    @Path("/create-category")
    public Response updateUserProfile(@Valid CategoryData categoryData) throws GroceryException {
    	 ResponseBuilder responseBuilder = Response.noContent();
         categoryService.createCategory(categoryData);
         return responseBuilder.build();
    }
    
    @PUT
    @UnitOfWork
    @Path("/update-category/{categoryId}")
    public Response updateItem(@PathParam(value = "categoryId") Long categoryId, CategoryData categoryData) throws GroceryException {
        ResponseBuilder responseBuilder = Response.noContent();
        categoryService.updateCategory(categoryData, categoryId);
        return responseBuilder.build();
    }
    
    @DELETE
    @UnitOfWork
    @Path("/delete-category/{categoryId}")
    public Response deleteItem(@PathParam(value = "categoryId") Long categoryId) throws GroceryException {
        ResponseBuilder responseBuilder = Response.noContent();
        categoryService.delete(categoryId);
        return responseBuilder.build();
    }

}
