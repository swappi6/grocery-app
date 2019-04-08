package org.grocery.Utils;

import java.io.InputStream;

import org.grocery.Error.GroceryException;

public interface FileStore {
    
    public String upload (String bucketName, InputStream inputStream) throws GroceryException;
    
    public void delete (String bucketName, String imageUrl) throws GroceryException;
    
//    public String rename(String bucketName, String newFileName) throws GroceryException;

}
