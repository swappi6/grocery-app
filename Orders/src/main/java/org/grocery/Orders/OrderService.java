package org.grocery.Orders;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.Response;

import org.grocery.Error.GroceryErrors;
import org.grocery.Error.GroceryException;
import org.grocery.Utils.Constants;
import org.grocery.Utils.FileStore;
import org.grocery.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.dataformat.yaml.snakeyaml.scanner.Constant;

@Component
public class OrderService {
	
	@Autowired
	OrderDao orderDao;
	@Autowired
	FileStore store;
	@Autowired
	ItemService itemService;
	
	
	
	public void delete(Long id) throws GroceryException{
		Optional<Order> optionalOrder = orderDao.findById(id);
		if (!optionalOrder.isPresent()) throw new GroceryException(Response.Status.BAD_REQUEST.getStatusCode(),GroceryErrors.INVALID_ORDER_ID);
		Order order = optionalOrder.get();
		
		orderDao.delete(order);
	}
	
	public void createOrder(OrderData orderData) throws GroceryException{
		Order order = new Order();
		order.setUserId(orderData.getUserId());
		order.setAddressId(orderData.getAddressId());
		order.setOfferId(orderData.getOfferId());
		List<OrderItem> items = new LinkedList<OrderItem>();
		for (ItemQuantity itemQauntity : orderData.getItems())
			items.add(mapToOrderItem(itemQauntity, order));
 		order.setItems(items);
		orderDao.create(order);
	}
	
	private OrderItem mapToOrderItem (ItemQuantity itemQauntity, Order order) {
		OrderItem orderItem = new OrderItem();
		orderItem.setItemId(itemQauntity.getItemId());
		orderItem.setQuantity(itemQauntity.getQuantity());
		orderItem.setOrder(order);
		return orderItem;
	}
	
	public void updateOrder(UpdateOrder updateOrder, long orderId) throws GroceryException {
		Optional<Order> optionalOrder = orderDao.findById(orderId);
		if(!optionalOrder.isPresent()) throw new GroceryException(Response.Status.BAD_REQUEST.getStatusCode(),GroceryErrors.INVALID_ORDER_ID) ;
		Order order = optionalOrder.get();
		order.setStatus(updateOrder.getStatus());
		orderDao.update(order);
	}
	public Optional<Order> searchOrderById (long search) throws GroceryException{
		Optional<Order>orderById = orderDao.findById(search);
		return orderById; 
		
	}
	public List<Order> searchOrderByUserId (long search) throws GroceryException{
		List<Order>orderByUserId = orderDao.findByUserId(search);
		return orderByUserId;
	}
	public List<Order> searchOrderByDate(String search) throws GroceryException{
		List<Order>orderByDate = orderDao.findByDate(search);
		return orderByDate;
	}
	

}
