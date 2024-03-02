package com.aaharan.bookManagement;

import com.aaharan.bookManagement.model.enums.RoleName;
import com.aaharan.bookManagement.role.BmRole;
import com.aaharan.bookManagement.role.RoleRepository;
import jakarta.annotation.Resource;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class BookManagementApplication implements CommandLineRunner {
    private @Resource RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(BookManagementApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public void run(String... args) {
        try {
            if (roleRepository.findAll().size()==0){
                BmRole admin = new BmRole();
                admin.setRoleName(RoleName.ADMIN);
                BmRole school = new BmRole();
                school.setRoleName(RoleName.SCHOOL);
                BmRole deo = new BmRole();
                deo.setRoleName(RoleName.DEO);
                BmRole is = new BmRole();
                is.setRoleName(RoleName.IS);
                List<BmRole> roles =List.of(admin,school, deo, is);
                this.roleRepository.saveAll(roles);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
