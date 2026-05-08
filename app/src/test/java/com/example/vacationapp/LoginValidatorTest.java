package com.example.vacationapp;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class LoginValidatorTest {

    @Test
    public void loginWithoutPassword_returnsPasswordRequirementMessage(){

        String username = "admin";
        String password = "";

        String result = LoginValidator.validateLogin(username,password);

        assertEquals("Password is required", result);
    }

    @Test
    public  void loginCorrectCredentials_returnsSuccess(){

        String username = "admin";
        String password = "password123";

        String result = LoginValidator.validateLogin(username, password);

        assertEquals("Login successful", result);
    }

}