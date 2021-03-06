package org.grocery.Orders;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.grocery.item.ItemQuantity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderData {
	@NotNull
	private Long addressId;
	private Long offerId;
	private List<ItemQuantity> items;

}
