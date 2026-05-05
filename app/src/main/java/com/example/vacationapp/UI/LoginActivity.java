package com.example.vacationapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vacationapp.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

            // Hard coded credentials
            String correctUsername = "admin";

            // Hash instead of plain password
            // password: password123
            String correctPasswordHash = "ef92b778bafe771e89245b89ecbc08a44a4e166c06659911881f383d4473e94f";

            // Hash what user typed
            String enteredPasswordHash = hashPassword(password);

            if(username.equals(correctUsername) && enteredPasswordHash.equals(correctPasswordHash)) {

                // Go to Main Activity
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                loginError.setText("Invalid username or password");
            }

        });
    }
    // Hash method
    private String hashPassword(String password){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());

            StringBuilder hexString = new StringBuilder();

            for(byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
