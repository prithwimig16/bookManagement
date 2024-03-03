package com.aaharan.bookManagement.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private boolean isApproved;



//    public User getUserFromDto() {
//        User user = new User();
//        user.setPassword(password);
//        user.setEmail(email);
//        user.setFirstname(firstName);
//        user.setLastname(lastName);
//        user.setApproved(isApproved);
//        user.setRole(role);
//        return user;
//    }

}