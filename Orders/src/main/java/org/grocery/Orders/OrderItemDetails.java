package org.grocery.Orders;

import java.util.List;

import org.grocery.item.Item;

import lombok.Data;

@Data
public class OrderItemDetails extends Order{
	List<Item> itemDetails;
}
