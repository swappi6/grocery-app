package org.grocery.Orders;

import org.grocery.User.User;

import lombok.Data;

@Data
public class OrderUserDetails extends OrderResponseDetails{
	private User user;
}
