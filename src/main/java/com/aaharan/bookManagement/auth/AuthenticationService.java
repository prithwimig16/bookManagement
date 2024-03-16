package com.aaharan.bookManagement.auth;

import com.aaharan.bookManagement.auth.login.LoginRequest;
import com.aaharan.bookManagement.config.AppConstraints;
import com.aaharan.bookManagement.config.JwtService;
import com.aaharan.bookManagement.deo.Deo;
import com.aaharan.bookManagement.deo.DeoRepository;
import com.aaharan.bookManagement.is.Is;
import com.aaharan.bookManagement.is.IsRepository;
import com.aaharan.bookManagement.model.enums.RoleName;
import com.aaharan.bookManagement.role.BmRole;
import com.aaharan.bookManagement.role.RoleService;
import com.aaharan.bookManagement.school.School;
import com.aaharan.bookManagement.school.SchoolRepository;
import com.aaharan.bookManagement.token.Token;
import com.aaharan.bookManagement.token.TokenRepository;
import com.aaharan.bookManagement.token.TokenType;
import com.aaharan.bookManagement.user.Role;
import com.aaharan.bookManagement.user.User;
import com.aaharan.bookManagement.user.UserDto;
import com.aaharan.bookManagement.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    @Autowired
    private RoleService roleService;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private DeoRepository deoRepository;

    @Autowired
    private IsRepository isRepository;

    @Autowired
    private ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    public void register(RegisterRequest request) {
        Role userRole = Role.ADMIN;
        if(request.getRoleName().equals(AppConstraints.ROLE_SCHOOL)){
            userRole=Role.SCHOOL;
        }
        if(request.getRoleName().equals(AppConstraints.ROLE_DEO)){
            userRole=Role.DEO;
        }
        if(request.getRoleName().equals(AppConstraints.ROLE_IS)){
            userRole=Role.IS;
        }
        var nUser = User.builder()
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(userRole)
                .build();
        if (userRole.equals(Role.ADMIN)) {
            nUser.setApproved(true);
        }

        BmRole role = Stream.of(AppConstraints.ROLE_ADMIN, AppConstraints.ROLE_DEO, AppConstraints.ROLE_SCHOOL, AppConstraints.ROLE_IS)
                .filter(roleName -> roleName.equals(request.getRoleName()))
                .map(roleName -> roleService.findByName(RoleName.valueOf(roleName.toUpperCase())))
                .findFirst().orElse(null);

        Set<BmRole> roleSet = new HashSet<>();
        roleSet.add(role);

        nUser.setRoles(roleSet);
        var savedUser = repository.save(nUser);
        assert role != null;
        if (role.getRoleName() != null) {
            if (role.getRoleName().equals(RoleName.SCHOOL)) {
                School school = new School();
                school.setUser(savedUser);
                school.setGovt(true);
                school.setIsApproved(false);
                school.setIsUpdated(false);
                school.setCreatedAt(LocalDateTime.now());
                school.setUpdatedAt(LocalDateTime.now());
                schoolRepository.save(school);
            }
            if (role.getRoleName().equals(RoleName.DEO)) {
                Deo deo = new Deo();
                deo.setUser(savedUser);
                deo.setIsApproved(false);
                deo.setIsUpdated(false);
                deo.setCreatedAt(LocalDateTime.now());
                deo.setUpdatedAt(LocalDateTime.now());
                deoRepository.save(deo);
            }

            if (role.getRoleName().equals(RoleName.IS)) {
                Is isObj = new Is();
                isObj.setUser(savedUser);
                isObj.setIsApproved(false);
                isObj.setIsUpdated(false);
                isObj.setCreatedAt(LocalDateTime.now());
                isObj.setUpdatedAt(LocalDateTime.now());
                isRepository.save(isObj);
            }
        }
        var jwtToken = jwtService.generateToken(nUser);
        var refreshToken = jwtService.generateRefreshToken(nUser);
        saveUserToken(savedUser, jwtToken);
        UserDto userDto = this.modelMapper.map(savedUser, UserDto.class);
        AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .user(userDto)
                .build();
    }

    public AuthenticationResponse login(LoginRequest request) throws HttpClientErrorException.Unauthorized {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        UserDto userDto = this.modelMapper.map(user, UserDto.class);

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .user(userDto)
                .access_token_validity(86400000)
                .refresh_token_validity(604800000)
                .build();
        //return GenericResponse.success(response);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
