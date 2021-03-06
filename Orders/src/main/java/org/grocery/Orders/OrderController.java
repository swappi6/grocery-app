package org.grocery.Orders;

import java.text.ParseException;
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
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.grocery.Auth.Auth;
import org.grocery.Error.GroceryException;
import org.grocery.User.UserService;
import org.grocery.admin.filter.ReadAuth;
import org.grocery.admin.filter.WriteAuth;
import org.grocery.item.ItemService;
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
	@Autowired
	ItemService itemService;
	@Autowired
	UserService userService;
	
	@GET
	@UnitOfWork
	@ReadAuth
	@Path("/searchById")
	public Response searchOrderById(@QueryParam(value = "orderId")long orderId)throws GroceryException, Exception{
		ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok();
		OrderResponseDetails order =orderService.searchOrderById(orderId);
		return responseBuilder.entity(order).build();
	}

	@GET
	@UnitOfWork
	@Auth
	@Path("/search-order-by-user-id")
	public Response searchOrderByUserId(@Context ContainerRequestContext context)throws GroceryException{
		ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok();
		Long userId = (Long) context.getProperty("userId");
		List<OrderResponseDetails> orders =orderService.searchOrderByUserId(userId);
		OrderList orderList = new OrderList();
        orderList.setOrders(orders);
		return responseBuilder.entity(orderList).build();
	}
	@GET
	@UnitOfWork
	@ReadAuth
	@Path("/search-order-by-date")
	public Response searchOrderByDate(@QueryParam(value = "date")Long date) throws GroceryException, ParseException{
		ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok();
		List<OrderResponseDetails> orders = orderService.searchOrderByDate(date);
		OrderList orderList = new OrderList();
        orderList.setOrders(orders);
		return responseBuilder.entity(orderList).build();
	}
	
//	@GET
//	@UnitOfWork
//	@ReadAuth
//	@Path("/search-order")
//	public Response searchActiveOrder(@QueryParam(value = "status") @NotNull List<OrderStatus> status) throws GroceryException{
//		ResponseBuilder responseBuilder = javax.ws.rs.core.Response.ok();
//		List<OrderResponseDetails> orders = orderService.searchActiveOrder(status);
//		OrderList orderList = new OrderList();
//		orderList.setOrders(orders);
//		return responseBuilder.entity(orderList).build();
//	}
	
	@POST
	@UnitOfWork
	@Auth
	@Path("/create-order")
	public Response createOrder(@Valid OrderData orderData, @Context ContainerRequestContext context) throws GroceryException, Exception {
		ResponseBuilder responseBuilder = Response.ok();
		Long userId = (Long) context.getProperty("userId");
		OrderResponse response = orderService.createOrder(orderData, userId);
		return responseBuilder.entity(response).build();
	}
	@PUT
	@UnitOfWork
	@WriteAuth
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
