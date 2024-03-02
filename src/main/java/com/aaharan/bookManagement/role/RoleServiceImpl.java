package com.aaharan.bookManagement.role;
import com.aaharan.bookManagement.model.enums.RoleName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public BmRole findByName(RoleName name) {
        BmRole role = roleRepository.findByRoleName(name);
        return role;
    }
}
