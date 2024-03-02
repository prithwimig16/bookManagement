package com.aaharan.bookManagement.auth;

import com.aaharan.bookManagement.user.UserDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

  @JsonProperty("access_token")
  private String accessToken;
  @JsonProperty("refresh_token")
  private String refreshToken;
  @JsonProperty("access_token_validity")
  private long access_token_validity;
  @JsonProperty("refresh_token_validity")
  private long refresh_token_validity;

  private UserDto user;
}
