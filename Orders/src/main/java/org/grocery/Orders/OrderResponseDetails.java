package org.grocery.Orders;

import java.util.List;

import org.grocery.item.QuantizedItem;
import org.grocery.Offers.OfferData;

import lombok.Data;

@Data
public class OrderResponseDetails extends Order{
	List<QuantizedItem> itemDetails;
	OfferData offerData;
}
