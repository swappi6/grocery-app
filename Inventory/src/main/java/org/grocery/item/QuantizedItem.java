package org.grocery.item;

import lombok.Data;

@Data
public class QuantizedItem extends Item{
    private long quantity;
}
