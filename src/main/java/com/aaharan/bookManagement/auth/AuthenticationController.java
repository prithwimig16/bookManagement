package com.aaharan.bookManagement.auth;

import com.aaharan.bookManagement.auth.login.LoginRequest;
import com.aaharan.bookManagement.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;
  @Autowired
  UserRepository userRepository;

  @PostMapping("/register")
  public ResponseEntity.BodyBuilder register(@RequestBody RegisterRequest request) {
    if(userRepository.findByEmail(request.getEmail()).isPresent()){
      return ResponseEntity.status(HttpStatus.CONFLICT);
    }
   var t=service.register(request);
    return ResponseEntity.ok();
  }
  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) throws HttpClientErrorException.Unauthorized {
    return ResponseEntity.ok(service.login(request));
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
