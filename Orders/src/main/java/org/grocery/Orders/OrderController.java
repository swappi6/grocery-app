package org.grocery.Orders;

import java.util.List;
import java.util.Optional;

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
@Path("/v1/order")
@Component
public class OrderController {

	@Autowired
	OrderService orderService;
	
	@GET
	@UnitOfWork
	@Path("/searchById")
	public Response searchOrderById(@QueryParam(value = "orderId")long orderId)throws GroceryException{
		ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok();
		//SearchOrderResponse response = new SearchOrderResponse();
		Optional<Order> order=orderService.searchOrderById(orderId);
		//response.setOrder(order);
		
		return responseBuilder.entity(order.get()).build();
	}
	@GET
	@UnitOfWork
	@Path("/search-order-by-user-id")
	public Response searchOrderByUserId(@QueryParam(value = "userId")long userId)throws GroceryException{
		ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok();
		SearchOrderResponse response = new SearchOrderResponse();
		List<Order> order =orderService.searchOrderByUserId(userId);
		response.setOrder(order);
		return responseBuilder.entity(response).build();
	}
	@GET
	@UnitOfWork
	@Path("/search-order-by-date")
	public Response searchOrderByDate(@QueryParam(value = "date")String date) throws GroceryException{
		ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok();
		SearchOrderResponse response = new SearchOrderResponse();
		List<Order> order = orderService.searchOrderByDate(date);
		response.setOrder(order);
		return responseBuilder.entity(response).build();
	}
	@POST
	@UnitOfWork
	@Path("/create-order")
	public Response createOrder(@Valid OrderData orderData) throws GroceryException {
		ResponseBuilder responseBuilder = Response.noContent();
		orderService.createOrder(orderData);
		return responseBuilder.build();
	}
	@PUT
	@UnitOfWork
	@Path("/update/{orderId}")
	public Response updateOrder(@PathParam(value= "orderId") Long orderId, UpdateOrder updateOrder) throws GroceryException{
		ResponseBuilder responseBuilder = Response.noContent();
		orderService.updateOrder(updateOrder, orderId);
		return responseBuilder.build();
	}
		@DELETE
		@UnitOfWork
		@Path("/delete-order/{orderId}")
		public Response deleteOrder(@PathParam(value = "orderId") Long orderId) throws GroceryException {
			ResponseBuilder responseBuilder = Response.noContent();
			orderService.delete(orderId);
			return responseBuilder.build();
		}

}
