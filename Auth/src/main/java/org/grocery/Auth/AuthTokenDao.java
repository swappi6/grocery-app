package org.grocery.Auth;

import java.util.List;
import java.util.Optional;

import javax.inject.Singleton;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import io.dropwizard.hibernate.AbstractDAO;

@Component
public class AuthTokenDao extends AbstractDAO<AuthToken> {

    private static SessionFactory factory;
    public AuthTokenDao(SessionFactory factory) {
        super(factory);
        this.factory = factory;
    }

    public AuthTokenDao() {
        super(factory);
    }

    public Optional<AuthToken> findById(Long id) {
        return Optional.ofNullable(get(id));
    }

    public AuthToken create(AuthToken auth) {
        return persist(auth);
    }
    
    public AuthToken update(AuthToken auth) {
        return persist(auth);
    }

    public List<AuthToken> findAll() {
        return list(namedQuery("AuthToken.findAll"));
    }

    public Long findUserByAccessToken(String accessToken) {
        return (Long) namedQuery("AuthToken.findUserByAccessToken")
                .setParameter("accessToken", accessToken).uniqueResult();
    }
    
    public Long findUserByValidAccessToken(String accessToken, Long millis) {
        return (Long) namedQuery("AuthToken.findUserByValidAccessToken")
                .setParameter("accessToken", accessToken)
                .setParameter("accessTokenExpiry", millis)
                .uniqueResult();
    }
    
    public Long findUserByRefreshToken(String refreshToken) {
        return (Long) namedQuery("AuthToken.findUserByRefreshToken")
               .setParameter("refreshToken", refreshToken)
               .uniqueResult();
    }
    
    public AuthToken findByUserAndDeviceId(Long userId, String deviceId) {
        return (AuthToken) namedQuery("AuthToken.findByUserAndDeviceId")
               .setParameter("userId", userId)
               .setParameter("deviceId", deviceId)
               .uniqueResult();
    }
    
    public AuthToken findByAccessAndRefreshToken(String accessToken, String refreshToken) {
        return (AuthToken) namedQuery("AuthToken.findByAccessAndRefreshToken")
               .setParameter("accessToken", accessToken)
               .setParameter("refreshToken", refreshToken)
               .uniqueResult();
    }
    
    public AuthToken findByRefreshToken(String refreshToken) {
        return (AuthToken) namedQuery("AuthToken.findByRefreshToken")
               .setParameter("refreshToken", refreshToken)
               .uniqueResult();
    }
}
