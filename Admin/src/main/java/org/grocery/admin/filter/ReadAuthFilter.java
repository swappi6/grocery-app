package org.grocery.admin.filter;

import javax.ws.rs.container.ContainerRequestFilter;

import org.grocery.admin.Role;
import org.springframework.stereotype.Component;

@Component
@ReadAuth
public class ReadAuthFilter extends AuthFilter implements ContainerRequestFilter {

    @Override
    public boolean isValidRole(Role role) {
        if (Role.READ.equals(role) || Role.WRITE.equals(role) || Role.ADMIN.equals(role))
            return true;
        return false;
    }
}