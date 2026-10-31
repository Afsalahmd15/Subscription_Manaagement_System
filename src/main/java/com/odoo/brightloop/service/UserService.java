package com.odoo.brightloop.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.odoo.brightloop.model.UserModel;
import com.odoo.brightloop.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public enum RegisterStatus {
        PASSWORD_MISSMATCH,
        EMAIL_EXISTS,
        REGISTER_SUCCESS
    }

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

    public RegisterStatus regUser(
            String email,
            String password,
            String confirmPassword) {

        if (!password.equals(confirmPassword)) {
            return RegisterStatus.PASSWORD_MISSMATCH;
        }

        UserModel existingUser = userRepository.findUserByEmail(email);

        if (existingUser != null) {
            return RegisterStatus.EMAIL_EXISTS;
        }

        userRepository.regUser(email, password);

        return RegisterStatus.REGISTER_SUCCESS;
    }
}
