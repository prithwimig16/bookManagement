package com.aaharan.bookManagement;

import com.aaharan.bookManagement.auth.AuthenticationService;
import com.aaharan.bookManagement.auth.RegisterRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import static com.aaharan.bookManagement.user.Role.ADMIN;
import static com.aaharan.bookManagement.user.Role.MANAGER;

@SpringBootApplication
public class BookManagementApplication {


    public static void main(String[] args) {
        SpringApplication.run(BookManagementApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner commandLineRunner(
//            AuthenticationService service
//    ) {
//        return args -> {
//            var admin = RegisterRequest.builder()
//                    .firstname("Admin")
//                    .lastname("Admin")
//                    .email("p@gmail.com")
//                    .password("1234")
//                    .role(ADMIN)
//                    .build();
//            System.out.println("Admin token: " + service.register(admin).getAccessToken());
//
//            var manager = RegisterRequest.builder()
//                    .firstname("Admin")
//                    .lastname("Admin")
//                    .email("manager@mail.com")
//                    .password("password")
//                    .role(MANAGER)
//                    .build();
//            System.out.println("Manager token: " + service.register(manager).getAccessToken());
//
//        };
//    }
}
