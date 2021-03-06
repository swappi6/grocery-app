package org.grocery.admin.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.grocery.Error.GroceryException;
import org.grocery.Utils.CacheService;
import org.grocery.admin.AdminService;
import org.grocery.admin.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class AuthFilter {
    
    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    @Autowired
    CacheService cacheService;
    @Autowired
    AdminService adminService;
    
    public void filter(ContainerRequestContext context) throws IOException {
        final String accessToken = extractParam(context, ACCESS_TOKEN);
        if (StringUtils.isEmpty(accessToken)) {
            context.abortWith(responseMissingParameter(ACCESS_TOKEN));
            return;
        }
        Long userId = getUserId(accessToken);
        if (userId == null) {
            context.abortWith(responseExpired());
            return;
        }
        Role role = null;
        try {
            role = adminService.getRoleofUser(userId);
        } catch (GroceryException e) {
            context.abortWith(responseExpired());
            e.printStackTrace();
            return;
        }
        if (isValidRole(role))
            return;
        context.abortWith(responseUnauthorized());
    }
    
    public abstract boolean isValidRole(Role role);
    
    private String extractParam(ContainerRequestContext context, String param) {
        return context.getHeaderString(param);
    }
     
    private Response responseMissingParameter(String name) {
        return Response.status(Response.Status.BAD_REQUEST)
            .type(MediaType.TEXT_PLAIN_TYPE)
            .entity("Auth Header Missing")
            .build();
    }
    
    private Long getUserId(String accessToken) {
        String userId = cacheService.getValue(accessToken);
        if (userId == null) return null;
        return Long.valueOf(userId);
    }
    
    private Response responseUnauthorized() {
        return Response.status(Response.Status.UNAUTHORIZED)
            .type(MediaType.TEXT_PLAIN_TYPE)
            .entity("Unauthorized")
            .build();
    }
    
    private Response responseExpired() {
        return Response.status(Response.Status.UNAUTHORIZED)
            .type(MediaType.TEXT_PLAIN_TYPE)
            .entity("Expired")
            .build();
    }

}
