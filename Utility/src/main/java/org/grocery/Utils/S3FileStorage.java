package org.grocery.Utils;

import java.io.InputStream;

import javax.ws.rs.core.Response;

import org.grocery.Error.GroceryErrors;
import org.grocery.Error.GroceryException;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
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
            throw new GroceryException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), GroceryErrors.S3_UPLOAD_ERROR);
        }
    }
    
    private String gets3Url(String bucketName, String fileName) {
        String url = s3Url;
        return url.replace("{bucket}", bucketName)
        .replace("{fileName}", fileName);
    }
    
    public void delete(String fileName, String bucketName) throws GroceryException {
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new ProfileCredentialsProvider())
                    .withRegion(clientRegion)
                    .build();

            s3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        }
        catch(Exception e) {
            e.printStackTrace();
            throw new GroceryException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), GroceryErrors.S3_DELETE_ERROR);
        }
    }
    
    public String rename(String oldFileName, String bucketName, String newFileName) throws GroceryException {
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new ProfileCredentialsProvider())
                    .withRegion(clientRegion)
                    .build();
            CopyObjectRequest copyRequest = new CopyObjectRequest(bucketName, oldFileName, bucketName, newFileName);
            s3Client.copyObject(copyRequest);
                          
              //Delete the original
            DeleteObjectRequest deleteRequest = new DeleteObjectRequest(bucketName, oldFileName);
            s3Client.deleteObject(deleteRequest);
            return gets3Url(bucketName, newFileName);

        }
        catch(Exception e) {
            e.printStackTrace();
            throw new GroceryException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), GroceryErrors.S3_DELETE_ERROR);
        }
    }

}
