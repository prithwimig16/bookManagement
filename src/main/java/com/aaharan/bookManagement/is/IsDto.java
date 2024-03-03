package com.aaharan.bookManagement.is;

import com.aaharan.bookManagement.user.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class IsDto {
    private Integer id;
    private String district;
    private String name;
    private String address;
    private String pinCode;
    private Boolean isUpdated;
    private Boolean isApproved;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserDto user;
}
