package com.aaharan.bookManagement.school;

import com.aaharan.bookManagement.user.UserDto;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class SchoolDto {
    private Integer id;
    private String district;
    private String schoolName;
    private String address;
    private String pinCode;
    private boolean isGovt;
    private Boolean isUpdated;
    private Boolean isApproved;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserDto user;
}
