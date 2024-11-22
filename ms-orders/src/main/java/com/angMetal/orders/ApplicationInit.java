package com.angMetal.orders;

import com.angMetal.orders.entity.Role;
import com.angMetal.orders.entity.User;
import com.angMetal.orders.enums.ERole;
import com.angMetal.orders.repositories.RoleRepository;
import com.angMetal.orders.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;


@Component
public class ApplicationInit {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationInit.class);

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${application.admin.username}")
    String adminUsername;

    @Value("${application.admin.email}")
    String adminEmail;

    @Value("${application.admin.password}")
    String adminPassword;

    @Value("${application.admin.create}")
    boolean createAdmin;

    @Bean
    InitializingBean init() {
        return () -> {
            // --------------------- ROLES ----------------------
            if (!roleRepository.existsByName(ERole.ROLE_ADMIN)) {
                logger.info("Saving role: {}", ERole.ROLE_ADMIN);
                roleRepository.save(new Role(ERole.ROLE_ADMIN));
            }
            if (!roleRepository.existsByName(ERole.ROLE_USER)) {
                logger.info("Saving role: {}", ERole.ROLE_USER);
                roleRepository.save(new Role(ERole.ROLE_USER));
            }
            if (createAdmin && !userRepository.existsByUsername(adminUsername)) {
                logger.info("Creating Admin Account");
                logger.info("Username: {}", adminUsername);
                logger.info("Email: {}", adminEmail);
                logger.info("Password: {}", adminPassword);

                Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN);
                User adminUser = new User();

                adminUser.setUsername(adminUsername);
                adminUser.setEmail(adminEmail);
                adminUser.setPassword(passwordEncoder.encode(adminPassword));

                adminUser.setRoles(Collections.singleton(adminRole));

                userRepository.save(adminUser);
            }
        };
    }
}