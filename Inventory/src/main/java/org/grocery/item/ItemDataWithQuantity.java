package org.grocery.item;

import lombok.Data;

@Data
public class ItemDataWithQuantity {
    private Item item;
    private Integer quantity;
}
