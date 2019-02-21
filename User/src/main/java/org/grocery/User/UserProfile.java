package org.grocery.User;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UserProfile {
    
    @NotNull
    private String firstName;
    
    private String lastName;
    
    @NotNull
    private String email;

}
