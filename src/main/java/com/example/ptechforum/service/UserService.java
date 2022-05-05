package com.example.ptechforum.service;

import com.example.ptechforum.model.Role;
import com.example.ptechforum.model.User;
import com.example.ptechforum.model.enums.Author;
import com.example.ptechforum.repository.RoleRepository;
import com.example.ptechforum.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostConstruct
    public void initialize() {
        User admin = userRepository.findByEmail("admin@admin.com");
        if (Objects.isNull(admin)) {
            admin = User.builder()
                    .email("admin@admin.com")
                    .encryptedPassword(bCryptPasswordEncoder.encode("admin"))
                    .username("admin").build();
            userRepository.save(admin);
            Role adminRole = Role.builder()
                    .author(Author.ADMIN)
                    .user(admin).build();
            roleRepository.save(adminRole);
        }

        User testUser = userRepository.findByEmail("test@test.com");
        if (Objects.isNull(testUser)) {
            testUser = User.builder()
                    .email("test@test.com")
                    .encryptedPassword(bCryptPasswordEncoder.encode("test"))
                    .username("test").build();
            userRepository.save(testUser);
            Role testUserRole = Role.builder()
                    .author(Author.MEMBER)
                    .user(testUser).build();
            roleRepository.save(testUserRole);
        }
    }
}
