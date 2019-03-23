package org.grocery.Utils;

import java.io.File;
import java.io.InputStream;

import org.grocery.Error.GroceryException;

public interface FileStore {
    
    public String upload (File file, String fileName, String bucketName, InputStream inputStream) throws GroceryException;

}
