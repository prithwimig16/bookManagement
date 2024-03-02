package com.aaharan.bookManagement.role;

import com.aaharan.bookManagement.model.LongEntity;
import com.aaharan.bookManagement.model.enums.RoleName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class BmRole extends LongEntity {

    @Serial
    private static final long serialVersionUID = 7862083365499377482L;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    public BmRole(RoleName roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BmRole role = (BmRole) o;
        return getId() != null && Objects.equals(getId(), role.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
