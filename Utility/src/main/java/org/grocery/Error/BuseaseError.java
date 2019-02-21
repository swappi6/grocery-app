package org.grocery.Error;

import lombok.Data;

@Data
public class BuseaseError {
    
    private final String code;
    private final String message;

    BuseaseError(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
