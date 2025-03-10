package com.parkinglot;

import com.parkinglot.models.User;
import com.parkinglot.repositories.UserRepository;
import com.parkinglot.services.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@SpringBootApplication
public class ParkingLotApplication {
    private static final Logger logger = LoggerFactory.getLogger(ParkingLotApplication.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(ParkingLotApplication.class, args);
        logger.info("Parking Lot Application Started!");
    }
    
    @Bean
    public CommandLineRunner resetPassword(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Optional<User> userOptional = userRepository.findByUsername("admin");
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                String newHash = passwordEncoder.encode("admin123");
                user.setPassword(newHash);
                userRepository.save(user);
                logger.info("Updated admin password hash to: {}", newHash);
            } else {
                logger.error("Admin user not found in database during reset");
            }
        };
    }

    @Bean
    public CommandLineRunner testPassword(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Optional<User> user = userRepository.findByUsername("admin");
            if (user.isPresent()) {
                logger.info("Testing password match for admin with raw password 'admin123': {}", 
                            passwordEncoder.matches("admin123", user.get().getPassword()));
            } else {
                logger.error("Admin user not found in database during test");
            }
        };
    }
}