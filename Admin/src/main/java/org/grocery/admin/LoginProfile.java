package org.grocery.admin;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class LoginProfile {
    
    @NotNull
    private String username;
    
    @NotNull
    private String password;
}

