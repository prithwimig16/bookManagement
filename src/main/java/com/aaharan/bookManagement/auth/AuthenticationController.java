package com.aaharan.bookManagement.auth;

import com.aaharan.bookManagement.auth.login.LoginRequest;
import com.aaharan.bookManagement.deo.Deo;
import com.aaharan.bookManagement.deo.DeoRepository;
import com.aaharan.bookManagement.school.School;
import com.aaharan.bookManagement.school.SchoolRepository;
import com.aaharan.bookManagement.user.Role;
import com.aaharan.bookManagement.user.User;
import com.aaharan.bookManagement.user.UserRepository;
import com.aaharan.bookManagement.utils.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    @Autowired
    UserRepository userRepository;

    @Autowired
    SchoolRepository schoolRepository;

    @Autowired
    DeoRepository deoRepository;


    @PostMapping("/register")
    public GenericResponse<RegisterRequest> register(@RequestBody RegisterRequest user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return GenericResponse.<RegisterRequest>builder()
                    .success(false)
                    .data(null)
                    .message("User already Exist")
                    .build();
        }
//        var newUser = RegisterRequest.builder()
//                .firstname(user.getFirstname())
//                .lastname(user.getLastname())
//                .email(user.getEmail())
//                .password(user.getPassword())
//                .roleName(user.getRole().name())
//                .build();

        service.register(user);
//        if (user.getRole().equals(Role.IS)) {
//            //districtwise multiple
//        }
//        if(user.getRole().equals(Role.SCHOOL)){
//            School school=new School();
//            school.setUser(user);
//            school.setCreatedAt(LocalDateTime.now());
//            school.setUpdatedAt(LocalDateTime.now());
//            schoolRepository.updateApprovalStatus(true,user.getId());
//        }
//
//        if (user.getRole().equals(Role.DEO)) {
//            Deo deo=new Deo();
//            deo.setUser(user);
//            deo.setCreatedAt(LocalDateTime.now());
//            deo.setUpdatedAt(LocalDateTime.now());
//            deoRepository.updateApprovalStatus(true,user.getId());
//
//        }
        return GenericResponse.success(user);

    }

    @PostMapping("/login")
    public GenericResponse<AuthenticationResponse> login(@RequestBody LoginRequest request) throws HttpClientErrorException.Unauthorized {

        return GenericResponse.success(service.login(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }


}
