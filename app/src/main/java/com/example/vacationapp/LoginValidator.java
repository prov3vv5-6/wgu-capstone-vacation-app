package com.example.vacationapp;

public class LoginValidator {
    public static String validateLogin(String username, String password){
        if (username == null || username.isEmpty()){
           return "Username is required";
        }
        if (password == null || password.isEmpty()){
            return "Password is required";
        }
        if (username.equals("admin") && password.equals("password123")){
            return "Login successful";
        }
        return "Invalid username or password";
    }
}