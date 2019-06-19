package org.grocery.Subscription;

import io.dropwizard.hibernate.UnitOfWork;
import org.grocery.Error.GroceryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.*;

import java.sql.Timestamp;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.ok;

@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Path("/v1/subscription")
@Component
public class SubscriptionController {

    @Autowired
    SubscriptionService subscriptionService;

    @POST
    @UnitOfWork
    @Path("/add")
    public Response addSubscription(@Valid SubscriptionInputData subscriptionInputData) throws GroceryException {
        ResponseBuilder responseBuilder = ok();
        Double payablePrice = subscriptionService.addSubscription(subscriptionInputData);
        return responseBuilder.entity(payablePrice).build();
    }

    @PUT
    @UnitOfWork
    @Path("/cancel")
    public Response cancelSubscription(@QueryParam(value = "id") long id)
            throws GroceryException {
        ResponseBuilder responseBuilder = ok();
        Double refund = subscriptionService.cancelSubscription(id);
        return responseBuilder.entity(refund).build();
    }

    @PUT
    @UnitOfWork
    @Path("/pause/{id}")
    public Response pauseSubscription(@PathParam(value = "id") long id) throws GroceryException {
        ResponseBuilder responseBuilder = ok();
        subscriptionService.pauseSubscription(id);
        return responseBuilder.build();
    }

    @PUT
    @UnitOfWork
    @Path("/resume")
    public Response resumeSubscription(@QueryParam(value = "id") long id,
                                       @QueryParam(value = "resumeDateInMillis") long resumeDateInMillis)
            throws GroceryException {
        ResponseBuilder responseBuilder = ok();
        Timestamp resumeDate = new Timestamp(resumeDateInMillis);
        subscriptionService.resumeSubscription(id, resumeDate);
        return responseBuilder.build();
    }

    @PUT
    @UnitOfWork
    @Path("/mark-delivery")
    public Response markDeliveryByDate(@QueryParam(value = "id") long id,
                                       @QueryParam(value = "dateInMillis") long dateInMillis)
            throws GroceryException {
        ResponseBuilder responseBuilder = ok();
        Timestamp date = new Timestamp(dateInMillis);
        subscriptionService.markDeliveryByDate(id, date);
        return responseBuilder.build();
    }

    @GET
    @UnitOfWork
    @Path("/getSubscriptionsByUser/{userId}")
    public Response getSubscriptionsByUser(@PathParam(value = "userId") long userId) throws GroceryException {
        ResponseBuilder responseBuilder = ok();
        List<SubscriptionData> data = subscriptionService.subscriptionsByUser(userId);
        return responseBuilder.entity(data).build();
    }

    //TODO change this when added in service layer
    @GET
    @UnitOfWork
    @Path("/getSubscriptionsByDate/{date}")
    public Response getSubscriptionsByDate(@PathParam(value = "date") long userId) throws GroceryException {
        ResponseBuilder responseBuilder = ok();
        List<SubscriptionData> data = subscriptionService.subscriptionsByUser(userId);
        return responseBuilder.entity(data).build();
    }
}
