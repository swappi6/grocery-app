package org.grocery.Auth;

import java.util.List;

import javax.ws.rs.core.Response;

import org.grocery.Error.GroceryErrors;
import org.grocery.Error.GroceryException;
import org.grocery.Utils.RandomGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthService {
    
    @Autowired
    AuthTokenDao authDao;
    @Autowired
    RandomGenerator rand;
    private static final Long accessExpiry = 300L;
    private static final Long refreshExpiry = 30000L;
    
    public AuthTokens refreshAccessToken(String refreshToken) throws GroceryException{
        AuthTokens tokens = new AuthTokens();
        AuthToken auth = authDao.findByRefreshToken(refreshToken);
        if (null == auth) throw new GroceryException(Response.Status.BAD_REQUEST.getStatusCode(),GroceryErrors.INVALID_REFRESH_TOKEN);
        String accessToken = rand.generateAuthToken();
        auth.setAccessToken(accessToken);
        Long currentTime = System.currentTimeMillis();
        auth.setAccessTokenExpiry(currentTime + accessExpiry);
        authDao.update(auth);
        tokens.setAccessToken(accessToken);
        return tokens;
    }
    
    public AuthTokens updateRefreshAndAccessToken(Long userId, String deviceId) {
        AuthTokens tokens = new AuthTokens();
        String accessToken = rand.generateAuthToken();
        String refreshToken = rand.generateAuthToken();
        Long currentTime = System.currentTimeMillis();
        AuthToken auth = authDao.findByUserAndDeviceId(userId, deviceId);
        if (null == auth) {
            auth = new AuthToken();
            auth.setUserId(userId);
            auth.setDeviceId(deviceId);
        }
        auth.setAccessToken(accessToken);
        auth.setAccessTokenExpiry(currentTime + accessExpiry);
        auth.setRefreshToken(refreshToken);
        auth.setRefreshTokenExpiry(currentTime + refreshExpiry);
        authDao.update(auth);
        tokens.setAccessToken(accessToken);
        tokens.setRefreshToken(refreshToken);
        return tokens;
    }
    
    public List<AuthToken> findall() {
        return authDao.findAll();
    }
    
}
