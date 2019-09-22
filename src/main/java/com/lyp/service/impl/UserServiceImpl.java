package com.lyp.service.impl;

import com.lyp.dao.UserRepository;
import com.lyp.domain.User;
import com.lyp.service.UserService;
import com.lyp.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username , MD5Utils.code(password));
        System.out.println(MD5Utils.code(password));
        return user;
    }
}
