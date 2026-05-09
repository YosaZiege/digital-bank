package com.youssef_rahioui.bank;

import com.youssef_rahioui.bank.entities.AppRole;
import com.youssef_rahioui.bank.entities.AppUser;
import com.youssef_rahioui.bank.repositories.AppRoleRepository;
import com.youssef_rahioui.bank.repositories.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
@AllArgsConstructor
@Order(1)
public class BankApplication implements CommandLineRunner {

    private final AppUserRepository appUserRepository;
    private final AppRoleRepository appRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(BankApplication.class, args);
    }

    @Override
    public void run(String... args) {
        AppRole adminRole = appRoleRepository.findByRoleName("ROLE_ADMIN")
                .orElseGet(() -> appRoleRepository.save(new AppRole(null, "ROLE_ADMIN", "Administrator")));
        AppRole userRole = appRoleRepository.findByRoleName("ROLE_USER")
                .orElseGet(() -> appRoleRepository.save(new AppRole(null, "ROLE_USER", "Default user")));

        if (appUserRepository.findByUsername("admin").isEmpty()) {
            AppUser admin = new AppUser();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@bank.com");
            admin.setRoles(List.of(adminRole, userRole));
            appUserRepository.save(admin);
        }
    }
}
