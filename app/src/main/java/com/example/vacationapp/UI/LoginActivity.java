package com.example.vacationapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vacationapp.R;

public class LoginActivity extends AppCompatActivity {
    EditText usernameInput;
    EditText passwordInput;
    Button loginButton;
    TextView loginError;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        loginError = findViewById(R.id.loginError);

        loginButton.setOnClickListener(v ->{
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if(username.isEmpty() || password.isEmpty()){
                loginError.setText("Please enter username and password");
                return;
            }

            // TODO: validate credentials

            // Hard coded credentials
            String correctUsername = "admin";
            String correctPassword = "password123";

            if(username.equals(correctUsername) && password.equals(correctPassword)) {

                // Go to Main Activity
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                loginError.setText("Invalid username or password");
            }

        });
    }

}
