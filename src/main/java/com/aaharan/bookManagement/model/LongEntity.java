package com.aaharan.bookManagement.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class LongEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 7862083365499377482L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

}
