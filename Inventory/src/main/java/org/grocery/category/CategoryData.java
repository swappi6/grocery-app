package org.grocery.category;

import java.io.File;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryData {
    
    @NotNull
    private String name;
    
    
    private String description;

    @NotNull
    private String parent;
    
    private File image;
    
}
