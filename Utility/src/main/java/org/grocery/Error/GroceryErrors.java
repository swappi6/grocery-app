package org.grocery.Error;

public class GroceryErrors {
    
    public static final GroceryError INVALID_OTP = new GroceryError("INVALID_OTP", "Incorrect OTP");
    public static final GroceryError UNKNOWN_ERRROR = new GroceryError("UNKNOWN_ERRROR", "Error while processing the request");
    public static final GroceryError INVALID_REFRESH_TOKEN = new GroceryError("INVALID_REFRESH_TOKEN", "Invalid refresh token");
    public static final GroceryError INVALID_USER = new GroceryError("INVALID_USER", "User Doesn't Exist");
}    
