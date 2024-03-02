package com.aaharan.bookManagement.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        repository.save(user);
    }

    public List<UserDto> getAllUsers() {
        List<User> allUsers = repository.findAll();
        List<UserDto> resultDto = new ArrayList<>();
        allUsers.forEach(it -> {
            UserDto userDto = new UserDto();
            userDto.setFirstName(it.getFirstname());
            userDto.setLastName(it.getLastname());
            userDto.setEmail(it.getEmail());
            userDto.setApproved(it.isApproved());
//            userDto.setRole(it.getRole());
            resultDto.add(userDto);
        });


        return resultDto;

    }
}
