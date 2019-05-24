package org.grocery.Utils;

import java.io.InputStream;

import javax.ws.rs.core.Response;

import org.grocery.Error.GroceryErrors;
import org.grocery.Error.GroceryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
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
    @Autowired
    RandomGenerator randomGenerator;
    

    public String upload(String bucketName, InputStream inputStream) throws GroceryException{
        String fileName = randomGenerator.generateUUID();
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
    
    public void delete(String bucketName, String imageUrl) throws GroceryException {
        String fileName = getImageNameFromUrl(imageUrl);
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                    .withRegion(clientRegion)
                    .build();

            s3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        }
        catch(Exception e) {
            e.printStackTrace();
            throw new GroceryException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), GroceryErrors.S3_DELETE_ERROR);
        }
    }
    
//    public String rename(String oldFileName, String bucketName, String newFileName) throws GroceryException {
//        oldFileName = getEncodedString(oldFileName);
//        newFileName = getEncodedString(newFileName);
//        try {
//            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
//                    .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
//                    .withRegion(clientRegion)
//                    .build();
//            CopyObjectRequest copyRequest = new CopyObjectRequest(bucketName, oldFileName, bucketName, newFileName);
//            s3Client.copyObject(copyRequest);
//                          
//              //Delete the original
//            DeleteObjectRequest deleteRequest = new DeleteObjectRequest(bucketName, oldFileName);
//            s3Client.deleteObject(deleteRequest);
//            return gets3Url(bucketName, newFileName);
//
//        }
//        catch(Exception e) {
//            e.printStackTrace();
//            throw new GroceryException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), GroceryErrors.S3_DELETE_ERROR);
//        }
//    }
    
    private String gets3Url(String bucketName, String fileName) {
        String url = s3Url;
        return url.replace("{bucket}", bucketName)
        .replace("{fileName}", fileName);
    }
    
    private String getImageNameFromUrl(String imageUrl) {
        String [] parsedUrl = imageUrl.split("/");
        return parsedUrl[parsedUrl.length-1];
    }

}
