package org.grocery.Error;

public class GroceryErrors {
    
    public static final GroceryError INVALID_OTP = new GroceryError("INVALID_OTP", "Incorrect OTP");
    public static final GroceryError UNKNOWN_ERRROR = new GroceryError("UNKNOWN_ERRROR", "Error while processing the request");
    public static final GroceryError INVALID_REFRESH_TOKEN = new GroceryError("INVALID_REFRESH_TOKEN", "Invalid refresh token");
    public static final GroceryError INVALID_USER = new GroceryError("INVALID_USER", "User Doesn't Exist");
    public static final GroceryError S3_UPLOAD_ERROR = new GroceryError("S3_UPLOAD_ERROR", " Error uploading image");
    public static final GroceryError S3_DELETE_ERROR = new GroceryError("S3_DELETE_ERROR", " Error deleting image");
    public static final GroceryError INVALID_PASSWORD = new GroceryError("INVALID_PASSWORD", "Password Incorrect");
    public static final GroceryError USER_PRESENT = new GroceryError("USER_PRESENT", "User Already Exists with the given EmailId");
    public static final GroceryError INVALID_ITEM_ID = new GroceryError("INVALID_ITEM_ID", "Item doesn't exist");
    public static final GroceryError INVALID_CATEGORY_ID = new GroceryError("INVALID_CATEGORY_ID", "Category doesn't exist");
    public static final GroceryError INVALID_OFFER_ID = new GroceryError("INVALID_OFFER_ID", "Offer Doesn't Exist");
    public static final GroceryError INACTIVE_OFFER = new GroceryError("INACTIVE_OFFER" , "Offer Inactive");
    public static final GroceryError INVALID_MINAMOUNT = new GroceryError("INVALID_MINAMOUNT" , "Amount insufficient");
}    
