package com.orm.service;

import com.orm.entity.User;

public interface UserService {
    public User loginUser(String username, String password) throws Exception;
    public User registerUser(String username, String password, String fullname) throws Exception;
}
