package org.grocery.Error;

import lombok.Data;

@Data
public class BuseaseException extends Throwable{
    private int code;
    private BuseaseError error;
    
    public BuseaseException() {
        this(500);
    }
    public BuseaseException(int code) {
        this(code,BuseaseErrors.UNKNOWN_ERRROR, null);
    }
    public BuseaseException(int code, BuseaseError error) {
        this(code, error, null);
    }
    public BuseaseException(int code, BuseaseError error, Throwable throwable) {
        super(throwable);
        this.error = error;
        this.code = code;
    }

}
