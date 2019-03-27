package org.grocery.Utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.stereotype.Component;

import com.amazonaws.util.Base64;

@Component
public class EncodedStringHelper {
    
    public InputStream getInputStream(String encodedImage) {
        if (encodedImage == null) return null;
        String [] strList = encodedImage.split(",");
        if (strList.length ==0) return null;
        byte[] abc = Base64.decode(strList[strList.length-1]);
        InputStream inputStream = new ByteArrayInputStream(abc);
        return inputStream;
    }

}
