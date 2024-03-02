package com.aaharan.bookManagement.role;

import com.aaharan.bookManagement.model.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<BmRole,Integer> {
    BmRole findByRoleName(RoleName name);
}
