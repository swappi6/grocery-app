package org.grocery.Orders;

import java.util.List;

import org.grocery.User.User;
import org.grocery.item.Item;

import lombok.Data;

@Data
public class SearchOrderResponse {
	private List<Order> order;
	private List<Item> items;
	
	
}
