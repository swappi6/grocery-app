package org.grocery.Utils;

public interface CacheService {
    
    void deleteKeys(String... keys);

    void expire(String key, int expiry);

    String getHashValue(String key, String field);

    String getValue(String key);
    
    String setValue(String key, String value);
    
    void publish (String channel, String message);
    
    String setValueWithExpiry(String key, String value, int expiry);


}
