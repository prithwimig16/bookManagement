package com.aaharan.bookManagement.book;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 200)
    private String bookName;

    @Column(nullable = false, length = 100)
    private String authorName;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(unique = false, nullable = false, length = 2)
    private int className;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 1")
    private Boolean isActive;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime updatedAt;
}
