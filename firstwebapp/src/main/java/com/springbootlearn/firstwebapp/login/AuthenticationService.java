package com.springbootlearn.firstwebapp.login;


import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    // authenticate a user

    public boolean authenticateAUser(String name, String password) {
        return name.equalsIgnoreCase("doaa") && password.equalsIgnoreCase("dummy5");
    }
}
