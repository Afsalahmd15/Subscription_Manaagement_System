package com.odoo.brightloop.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.odoo.brightloop.model.UserModel;
import com.odoo.brightloop.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public enum LoginStatus {
        EMAIL_DOES_NOT_EXISTS,
        WRONG_PASSWORD,
        LOGIN_SUCCESS
    }

    public LoginStatus login(String emailId, String password) {

        UserModel user = userRepository.findByEmail(emailId);

        if (user == null) {
            return LoginStatus.EMAIL_DOES_NOT_EXISTS;
        }

        if (!user.getPassword().equals(password)) {
            return LoginStatus.WRONG_PASSWORD;
        }

        return LoginStatus.LOGIN_SUCCESS;
    }
}
