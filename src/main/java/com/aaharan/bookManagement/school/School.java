package com.aaharan.bookManagement.school;

import com.aaharan.bookManagement.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "school")
public class School {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = true,length = 20)
    private String district;
    @Column(unique = true,nullable = true,length = 10)
    private String schoolName;
    @Column(nullable = true,length = 100)
    private String address;
    @Column(nullable = true,length = 6)
    private String pinCode;
    @Column(nullable = true,columnDefinition="tinyint(1) default 1")
    private boolean isGovt;
    @Column(nullable = true,columnDefinition="tinyint(1) default 0")
    private Boolean isUpdated;
    @Column(nullable = true,columnDefinition="tinyint(1) default 0")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        School school = (School) o;
        return id != null && Objects.equals(id, school.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
