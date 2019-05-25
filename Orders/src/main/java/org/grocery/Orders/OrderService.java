package org.grocery.Orders;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.Response;

import org.grocery.Error.GroceryErrors;
import org.grocery.Error.GroceryException;
import org.grocery.User.UserService;
import org.grocery.Utils.FileStore;
import org.grocery.item.Item;
import org.grocery.item.ItemQuantity;
import org.grocery.item.ItemService;
import org.grocery.item.QuantizedItem;
import org.grocery.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderService {
	
	@Autowired
	OrderDao orderDao;
	@Autowired
	FileStore store;
	@Autowired
	ItemService itemService;
	@Autowired
	UserService userService;
	@Autowired
	Mapper mapper;
	
	
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
		//order.setActualPrice(itemService.getCartPrice(orderData.getItems()));
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
//	//public Item itemDetails (Long itemIds, Order order) throws Exception, GroceryException {
//		
//	}
	public Optional<Order> searchOrderById (long id) throws GroceryException, Exception{
		Optional<Order> orderById = orderDao.findById(id);
//		List<OrderItemDetails> orderDetailList = new LinkedList<>();
//		for(Order order : orderById) {
//			OrderItemDetails orderItemDetails = new OrderItemDetails();
//			mapper.copyProperties(orderItemDetails , orderById);
//			orderItemDetails.setItemDetails(getItemdetails(orderById));
//			orderDetailList.add(orderItemDetails);
//		}
		return orderById; 
		
	}
	public void updateOrder(UpdateOrder updateOrder, long orderId) throws GroceryException {
		Optional<Order> optionalOrder = orderDao.findById(orderId);
		if(!optionalOrder.isPresent()) throw new GroceryException(Response.Status.BAD_REQUEST.getStatusCode(),GroceryErrors.INVALID_ORDER_ID) ;
		Order order = optionalOrder.get();
		if (updateOrder.getStatus() != null)
			order.setStatus(updateOrder.getStatus());
		orderDao.update(order);
	}
	
	public List<OrderItemDetails> searchOrderByUserId (long search) throws GroceryException{
		List<Order> orderByUserId = orderDao.findByUserId(search);
		List<OrderItemDetails> orderDetailsList = new LinkedList<>();
		for (Order order : orderByUserId) {
			OrderItemDetails orderDetails = new OrderItemDetails();
			mapper.copyProperties(orderDetails, order);
			orderDetails.setItemDetails(getItemdetails(order));
			orderDetailsList.add(orderDetails);
		}
		return orderDetailsList;
	}
	public List<OrderItemDetails> searchOrderByDate(Long timestamp) throws GroceryException{
		List<Order> orderByDate = orderDao.findByCreatedDate(timestamp);
		List<OrderItemDetails> orderDetailsList = new LinkedList<>();
		for (Order order : orderByDate) {
			OrderItemDetails orderDetails = new OrderItemDetails();
			mapper.copyProperties(orderDetails, order);
			orderDetails.setItemDetails(getItemdetails(order));
			orderDetailsList.add(orderDetails);
		}
		//return orderDao.findByCreatedDate(orderByDate);
		return orderDetailsList;
	}
	public List<OrderItemDetails> searchActiveOrder(OrderStatus status) throws GroceryException{
		List<Order> activeOrder = orderDao.findActiveOrder(status);
		List<OrderItemDetails> orderDetailsList = new LinkedList<>();
		for (Order order : activeOrder) {
			OrderItemDetails orderDetails = new OrderItemDetails();
			mapper.copyProperties(orderDetails, order);
			orderDetails.setItemDetails(getItemdetails(order));
			orderDetailsList.add(orderDetails);
		}
		return orderDetailsList;
	}
	

	private List<QuantizedItem> getItemdetails(Order order) throws GroceryException{
		List<Long> itemIds = new LinkedList<Long>();
		for (OrderItem orderItem: order.getItems()) {
			itemIds.add(orderItem.getItemId());
		}
		List<Item> items = itemService.getItems(itemIds);
		List<QuantizedItem> quantizedItemList = new LinkedList<>();
		for (Item item : items) {
		    QuantizedItem quantizedItem = new QuantizedItem();
		    mapper.copyProperties(quantizedItem, item);
		    Long quantity = null;
		    for (OrderItem orderItem: order.getItems()) {
	            if (orderItem.getItemId() == item.getId()) {
	                quantity = orderItem.getQuantity();
	                break;
	            }
	        }
		    quantizedItem.setQuantity(quantity);
		    quantizedItemList.add(quantizedItem);
		}
		return quantizedItemList;
	}
	
}
