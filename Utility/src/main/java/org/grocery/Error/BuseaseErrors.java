package org.grocery.Error;

public class BuseaseErrors {
    
    public static final BuseaseError INVALID_OTP = new BuseaseError("INVALID_OTP", "Incorrect OTP");
    public static final BuseaseError UNKNOWN_ERRROR = new BuseaseError("UNKNOWN_ERRROR", "Error while processing the request");
    public static final BuseaseError INVALID_REFRESH_TOKEN = new BuseaseError("INVALID_REFRESH_TOKEN", "Invalid refresh token");
    public static final BuseaseError INVALID_USER = new BuseaseError("INVALID_USER", "User Doesn't Exist");
}    
