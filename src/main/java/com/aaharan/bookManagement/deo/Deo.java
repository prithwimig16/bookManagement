package com.aaharan.bookManagement.deo;

import com.aaharan.bookManagement.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "deo")
public class Deo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true,nullable = true,length = 20)
    private String district;
    @Column(nullable = true,length = 300)
    private String address;
    @Column(nullable = true,length = 6)
    private String pinCode;
    @Column(nullable = true, columnDefinition = "tinyint(1) default 0")
    private Boolean isUpdated;
    @Column(nullable = true, columnDefinition = "tinyint(1) default 0")
    private Boolean isApproved;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime updatedAt;
}
