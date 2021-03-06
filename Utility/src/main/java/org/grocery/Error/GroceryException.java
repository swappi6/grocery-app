package org.grocery.Error;

import lombok.Data;

@Data
public class GroceryException extends Throwable{
    private int code;
    private Object error;
    
    public GroceryException() {
        this(500);
    }
    public GroceryException(int code) {
        this(code,GroceryErrors.UNKNOWN_ERRROR, null);
    }
    public GroceryException(int code, Object error) {
        this(code, error, null);
    }
    public GroceryException(int code, Object error, Throwable throwable) {
        super(throwable);
        this.error = error;
        this.code = code;
    }

}
