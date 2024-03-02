package com.aaharan.bookManagement.role;


import com.aaharan.bookManagement.model.enums.RoleName;

public interface RoleService {
    BmRole findByName(RoleName name);
}
