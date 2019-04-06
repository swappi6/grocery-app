package org.grocery.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.grocery.Offers.Offer;
import org.grocery.Offers.OfferService;
import org.grocery.category.Category;
import org.grocery.category.CategoryService;
import org.grocery.response.ExploreResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.dropwizard.hibernate.UnitOfWork;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/v1/app")
@Component
public class AppController {
    
    @Autowired
    CategoryService categoryService;
    
    @Autowired
    OfferService offerService;
    
    @GET
    @UnitOfWork
    @Path("/explore")
    public Response getCategoryItems(@QueryParam(value = "parent") Long parent) throws Exception{
        ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok();
        ExploreResponse response = new ExploreResponse();
        List<Category> categories = categoryService.getParentCategories();
        List<Offer> offers = offerService.getValidOffers();
        response.setCategories(categories);
        response.setOffers(offers);
        return responseBuilder.entity(response)
                .build();
    }
    
    
}
