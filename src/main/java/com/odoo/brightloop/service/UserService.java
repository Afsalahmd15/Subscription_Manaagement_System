package com.odoo.brightloop.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.odoo.brightloop.model.UserModel;
import com.odoo.brightloop.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserModel login(String emailId, String password) {

        UserModel user = userRepository.findUserByEmail(emailId);

        if (user == null) {
            return null;
        }

        if (!user.getPassword().equals(password)) {
            return null;
        }

        return user;
    }
}
