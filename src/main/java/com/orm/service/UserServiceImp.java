package com.orm.service;

import com.orm.entity.User;
import com.orm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {
    private UserRepository userRepository;

    public UserServiceImp() {
        this.userRepository = new UserRepository();
    }

    public User loginUser(String username, String password) throws Exception {
        User user = userRepository.getUserByUsername(username);
        if (user == null) {
            return null;
        }

//        TODO: compare hashed password
        if (!password.equals(user.getPassword())) {
            return null;
        }

        return user;
    }

    public User registerUser(String username, String password, String fullname) throws Exception {
        boolean isSuccess = userRepository.addNewUser(username, password, fullname);

        if (!isSuccess) {
            return null;
        }

        User user = userRepository.getUserByUsername(username);
        return user;
    }
}
