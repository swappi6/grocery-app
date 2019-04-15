package org.grocery.admin;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AdminProfile {
    
    @NotNull
    private String firstName;
    private String lastName;
    @NotNull
    private String mobileNo;
    @NotNull
    private String email;
    private String countryCode;
    private String token;
    @NotNull
    private String password;
    @NotNull
    private Role role;
}