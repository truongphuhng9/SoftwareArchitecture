package com.orm.repository;

import com.orm.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    public User getUserByUsername(String username) {
        if (username.equals("someone")) {
            User user = new User();
            user.setUsername("someone");
            user.setPassword("123456");
            user.setFullname("Some One");
            return user;
        }

        return null;
    }

    public boolean addNewUser(String username, String password, String fullname) {
        return true;
    }
}
