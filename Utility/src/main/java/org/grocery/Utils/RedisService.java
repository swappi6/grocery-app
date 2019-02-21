package org.grocery.Utils;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class RedisService implements CacheService{
    
    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);
    private static JedisPool jedisPool;
    
    public RedisService(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
    
    public RedisService() {
    }
    
    public void deleteKeys(String... keys) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(keys);
        } catch(Exception e) {
            logger.error("Delete Keys Redis Exception " + keys + " / " + e.getMessage());
        }  finally {
            if (jedis != null)
                jedis.close();
        }
    }

    public void expire(String key, int expiry) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.expire(key, expiry);
        } catch(Exception e) {
            logger.error("Expire Redis Exception " + key +  " / " + e.getMessage());
        }  finally {
            if (jedis != null)
                jedis.close();
        }
    }

    public String getHashValue(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hget(key, field);
        } catch(Exception e) {
            logger.error("Get Hash Value Redis Exception " + key + " / " + e.getMessage());
        }  finally {
            if (jedis != null)
                jedis.close();
        }
        return null;
    }

    public String getValue(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        } catch(Exception e) {
            logger.error("Get Value Redis Exception " + key + " / " + e.getMessage());
        } finally {
            if (jedis != null)
                jedis.close();
        }
        return null;
    }
    
    public String setValue(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.set(key, value);
        } catch(Exception e) {
            logger.error("Set Key Value Redis Exception " + key + " / " + value + " / "+ e.getMessage());
        } finally {
            if (jedis != null)
                jedis.close();
        }
        return null;
    }
    
    public String setValueWithExpiry(String key, String value, int expiry) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.setex(key, expiry, value);
        } catch(Exception e) {
            logger.error("Set Key Value Redis Exception " + key + " / " + value + " / "+ e.getMessage());
        } finally {
            if (jedis != null)
                jedis.close();
        }
        return null;
    }
    
    public void publish (String channel, String message) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.publish(channel, message);
        } catch(Exception e) {
            logger.error("Publish Value Redis Exception " + channel + " / " + message + " / "+ e.getMessage());
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }
    

}
