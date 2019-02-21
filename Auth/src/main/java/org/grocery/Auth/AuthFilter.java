package org.grocery.Auth;

import java.io.IOException;
 
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Auth
public class AuthFilter implements ContainerRequestFilter {
 
    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    @Autowired
    AuthTokenDao authDao;
    
    public void filter(ContainerRequestContext context) throws IOException {
        final String accessToken = extractParam(context, ACCESS_TOKEN);
        if (StringUtils.isEmpty(accessToken)) {
            context.abortWith(responseMissingParameter(ACCESS_TOKEN));
        }
        else if (!authenticate(context, accessToken)) {
            context.abortWith(responseUnauthorized());
        }
    }
    
    private String extractParam(ContainerRequestContext context, String param) {
        return context.getHeaderString(param);
    }
     
    private Response responseMissingParameter(String name) {
        return Response.status(Response.Status.BAD_REQUEST)
            .type(MediaType.TEXT_PLAIN_TYPE)
            .entity("Auth Header Missing")
            .build();
    }
    
    private boolean authenticate(ContainerRequestContext context, String accessToken) {
        Long currentTime = System.currentTimeMillis();
        Long userId = authDao.findUserByValidAccessToken(accessToken, currentTime);
        if (null == userId) return false;
        context.setProperty("userId",userId);
        return true;
    }
    
    private Response responseUnauthorized() {
        return Response.status(Response.Status.UNAUTHORIZED)
            .type(MediaType.TEXT_PLAIN_TYPE)
            .entity("Unauthorized")
            .build();
    }
}
