package com.example.vacationapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.vacationapp.R;

public class MainActivity extends AppCompatActivity {
    public static int numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Get reference to the button from the layout
        Button button=findViewById(R.id.button2);

        // Set click listener for the button
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                // Create an Intent to navigate from MainActivity to VacationsList
                Intent intent=new Intent(MainActivity.this,VacationsList.class);
                // Attach extra data to the Intent (key = "test", value = "Information sent")
                intent.putExtra("test", "Information sent");
                // Start the VacationsList activity
                startActivity(intent);

            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}