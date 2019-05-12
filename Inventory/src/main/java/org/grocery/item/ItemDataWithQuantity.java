package org.grocery.item;

import lombok.Data;

@Data
public class ItemDataWithQuantity {
    private ItemData itemData;
    private Integer quantity;
}
