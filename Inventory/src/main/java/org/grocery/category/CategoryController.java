package org.grocery.category;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

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
    @Path("/categories")
    public Response getCategories() throws Exception{
        ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok();
        List<Category> categories = categoryService.getParentCategories();
        return responseBuilder.entity(categories)
                .build();
    }
    

}
