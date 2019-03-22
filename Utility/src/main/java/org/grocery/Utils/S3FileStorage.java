package org.grocery.Utils;

import java.io.File;

import javax.ws.rs.core.Response;

import org.grocery.Error.GroceryErrors;
import org.grocery.Error.GroceryException;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Component
public class S3FileStorage implements FileStore{
    
    private static final String clientRegion = "ap-south-1";
    private static final BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAJMRZHQH5FIS3XZNA", "UW+nwoUyJdyGZ5icbBui2j/7rXMcsVPsiCTE+EhQ");

    public String upload(File file, String fileName, String bucketName) throws GroceryException{
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                    .build();
            
            // Upload a file as a new object with ContentType and title specified.
            PutObjectRequest request = new PutObjectRequest(bucketName, fileName, file);
            s3Client.putObject(request);
        }
        catch(Exception e) {
            e.printStackTrace();
            throw new GroceryException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), GroceryErrors.INVALID_REFRESH_TOKEN);
        }
        return null;
    }

}
