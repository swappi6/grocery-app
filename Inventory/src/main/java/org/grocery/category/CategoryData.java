package org.grocery.category;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryData {
    @NotNull
    private String name;
    private String description;
    private Long parent;
    private String encodedImage;
}
