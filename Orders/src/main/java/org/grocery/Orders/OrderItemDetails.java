package org.grocery.Orders;

import java.util.List;

import org.grocery.item.QuantizedItem;
import org.grocery.Offers.OfferData;

import lombok.Data;

@Data
public class OrderItemDetails extends Order{
	List<QuantizedItem> itemDetails;
	OfferData offerData;
}
