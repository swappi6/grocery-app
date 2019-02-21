package org.grocery.Auth;

import lombok.Data;

@Data
public class RefreshAccessTokenRequest {
    
    private String accessToken;
    private String authToken;

}
