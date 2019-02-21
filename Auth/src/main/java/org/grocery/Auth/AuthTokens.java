package org.grocery.Auth;

import lombok.Data;

@Data
public class AuthTokens {
    
    private String refreshToken;
    private String accessToken;
}
