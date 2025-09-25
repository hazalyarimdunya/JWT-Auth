package com.hazalyarimdunya.auth_jwt_app.services;

import com.hazalyarimdunya.auth_jwt_app.entity.User;
import com.hazalyarimdunya.auth_jwt_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    //kullanici kaydi
    public User registerUser(String username, String password , User.Role role) {
        // Kullanıcı zaten var mı kontrol et
        if(userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        // Şifreyi hashle
        String encodedPassword = passwordEncoder.encode(password);

        //gerçek User nesnesini oluştur
        User user = User.builder()
                .username(username)
                .password(encodedPassword) // daha guvenli
                .role(role).build();
        return userRepository.save(user);


    }

    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
