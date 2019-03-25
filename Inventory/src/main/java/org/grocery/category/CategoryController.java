package org.grocery.category;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
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

import com.amazonaws.util.Base64;

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
    
    @GET
    @UnitOfWork
    @Path("/subCategory")
    public Response getSubCategories(@QueryParam(value = "parent") Long parent) throws Exception{
        ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok();
        List<Category> categories = categoryService.findByCategory(parent);
        return responseBuilder.entity(categories)
                .build();
    }
    
    @POST
    @UnitOfWork
    @Path("/createCategory")
    public Response updateUserProfile(@Valid CategoryData categoryData) throws GroceryException {
        ResponseBuilder responseBuilder = Response.noContent();
        String [] strList = categoryData.getEncodedImage().split(",");
        byte[] abc = Base64.decode(strList[strList.length-1]);
        InputStream inputStream = new ByteArrayInputStream(abc);
        categoryService.createCategory(categoryData, inputStream);
        return responseBuilder.build();
    }

}
