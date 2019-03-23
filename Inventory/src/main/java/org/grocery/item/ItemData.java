package org.grocery.item;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ItemData {
    @NotNull
    private String name;
    private String description;
    @NotNull
    private String parent;
    private String encodedImage;
    @NotNull
    private Double price;
    private String discountedPrice;

}
