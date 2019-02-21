package org.grocery.Auth;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "auth_token")
@NamedQueries(
    {
        @NamedQuery(
            name = "AuthToken.findAll",
            query = "SELECT m FROM AuthToken m"
        ),
        @NamedQuery(
                name = "AuthToken.findUserByAccessToken",
                query = "select userId from AuthToken e "
                        + "where e.accessToken = :accessToken "
        ),
        @NamedQuery(
                name = "AuthToken.findUserByRefreshToken",
                query = "select e.userId from AuthToken e "
                        + "where e.refreshToken = :refreshToken "
        ),
        @NamedQuery(
                name = "AuthToken.findUserByValidAccessToken",
                query = "select e.userId from AuthToken e "
                        + "where e.accessToken = :accessToken and e.accessTokenExpiry > accessTokenExpiry"
        ),
        @NamedQuery(
                name = "AuthToken.findByUserAndDeviceId",
                query = "select e from AuthToken e "
                        + "where e.userId = :userId and e.deviceId = :deviceId"
        ),
        @NamedQuery(
                name = "AuthToken.findByAccessAndRefreshToken",
                query = "select e from AuthToken e "
                        + "where e.accessToken = :accessToken and e.refreshToken = :refreshToken"
        ),
        @NamedQuery(
                name = "AuthToken.findByRefreshToken",
                query = "select e from AuthToken e "
                        + "where e.refreshToken = :refreshToken"
        )
    }
)
public class AuthToken {
    
    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "device_id", nullable = false)
    private String deviceId;
    
    @Column(name = "access_token", nullable = true)
    private String accessToken;

    @Column(name = "refresh_token", nullable = true)
    private String refreshToken;
    
    @Column(name = "access_token_expiry", nullable = true)
    private Long accessTokenExpiry;
    
    @Column(name = "refresh_token_expiry", nullable = true)
    private Long refreshTokenExpiry;
    
    @Version
    @Column(name = "lock_version", nullable = false)
    private Integer lockVersion;
    
    @Column(name = "created_at", nullable = true)
    private Timestamp createdAt;
    
    @Column(name = "updated_at", nullable = true)
    private Timestamp updatedAt;

    public AuthToken() {
    }
    
    public AuthToken(Long userId, String deviceId, String accessToken, String refreshToken, Long accessTokenExpiry, Long refreshTokenExpiry) {
        super();
        this.userId = userId;
        this.deviceId = deviceId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpiry = accessTokenExpiry;
        this.refreshTokenExpiry = refreshTokenExpiry;
    }

}