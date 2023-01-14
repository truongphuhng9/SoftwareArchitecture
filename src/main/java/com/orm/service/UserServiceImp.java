package com.orm.service;

import com.orm.entity.User;
import com.orm.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;


    public UserServiceImp() {
        this.userRepository = new UserRepository();
    }

    public User loginUser(String username, String password) {
        User user = userRepository.getUserByUsername(username);
        if (user == null) {
            return null;
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return null;
        }

        return user;
    }

    public User registerUser(String username, String password, String fullname) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPass = passwordEncoder.encode(password);

        boolean isSuccess = userRepository.addNewUser(username, hashedPass, fullname);

        if (!isSuccess) {
            return null;
        }
        
        return userRepository.getUserByUsername(username);
    }
}
