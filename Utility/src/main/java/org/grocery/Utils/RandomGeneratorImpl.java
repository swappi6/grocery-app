package org.grocery.Utils;

import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class RandomGeneratorImpl implements RandomGenerator{
    
    public RandomGeneratorImpl() {
        
    }
    
    private static final Random rand = new Random();

    public String generateOtp() {
        String id = String.format("%04d", rand.nextInt(10000));
        return id;
    }

    public String generateAuthToken() {
        String auth = UUID.randomUUID().toString();
        return auth;
    }
    
}

