package org.grocery.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.ws.rs.core.Response;

import org.grocery.Error.GroceryErrors;
import org.grocery.Error.GroceryException;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Component
public class S3FileStorage implements FileStore{
    
    private static final String clientRegion = "ap-south-1";
    private static final BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAJMRZHQH5FIS3XZNA", "UW+nwoUyJdyGZ5icbBui2j/7rXMcsVPsiCTE+EhQ");
    private static final String s3Url = "https://s3.ap-south-1.amazonaws.com/{bucket}/{fileName}";

    public String upload(String fileName, String bucketName, InputStream inputStream) throws GroceryException{
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                    .build();

            PutObjectRequest request = new PutObjectRequest(bucketName, fileName, inputStream, new ObjectMetadata());
            s3Client.putObject(request);
            return gets3Url(bucketName, fileName);
        }
        catch(Exception e) {
            e.printStackTrace();
            throw new GroceryException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), GroceryErrors.INVALID_REFRESH_TOKEN);
        }
    }
    
    private String gets3Url(String bucketName, String fileName) {
        String url = s3Url;
        return url.replace("{bucket}", bucketName)
        .replace("{fileName}", fileName);
    }

}
