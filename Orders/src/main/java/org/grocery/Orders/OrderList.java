package org.grocery.Orders;

import java.util.List;

import lombok.Data;

@Data
public class OrderList {
    private List<OrderResponseDetails> orders;
}
