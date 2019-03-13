package org.grocery.Error;

import lombok.Data;

@Data
public class GroceryError {
    
    private final String code;
    private final String message;

    GroceryError(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
