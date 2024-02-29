package com.aaharan.bookManagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
@Table(name = "book")
public class Book extends LongEntity{
    private String bookName;
    private String authorName;
    private int className ;
    @Column(columnDefinition = "boolean default false")
    private Boolean isActive;
}
