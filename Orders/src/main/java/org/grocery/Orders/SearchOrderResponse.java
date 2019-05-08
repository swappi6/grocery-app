package org.grocery.Orders;

import java.util.List;
import java.util.Optional;

import lombok.Data;

@Data
public class SearchOrderResponse {
	private List<Order> order;
}
