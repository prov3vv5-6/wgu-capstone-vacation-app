package com.example.vacationapp.UI;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vacationapp.R;
import com.example.vacationapp.database.Repository;
import com.example.vacationapp.entities.Vacation;
import com.example.vacationapp.UI.ReportAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReportActivity extends AppCompatActivity {
    TextView reportTitle;
    TextView generatedDateTime;
    EditText searchClientName;
    Button searchButton;
    RecyclerView reportRecyclerView;
    Repository repository;
    ReportAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        reportTitle = findViewById(R.id.reportTitle);
        searchClientName = findViewById(R.id.searchClientName);
        searchButton = findViewById(R.id.searchButton);
        reportRecyclerView = findViewById(R.id.reportRecyclerView);
        generatedDateTime = findViewById(R.id.generatedDateTime);

        repository = new Repository(getApplication());

        // Button click logic
        searchButton.setOnClickListener(view -> {

            String clientName = searchClientName.getText().toString();
            String currentDateTime = new SimpleDateFormat("MM/dd/yyy HH:mm", Locale.getDefault()).format(new Date());
            generatedDateTime.setText("Generated: " + currentDateTime);

            if(clientName.isEmpty()){
                reportTitle.setText("Please enter a client name");
                return;
            }
            try{
                List<Vacation> results = repository.searchVacationByClientName(clientName);

                reportTitle.setText("Report by Client: " + clientName);

                adapter = new ReportAdapter(this);
                reportRecyclerView.setAdapter(adapter);
                reportRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                adapter.setVacations(results);

                if(results.isEmpty()){
                    reportTitle.setText("No results found for: " + clientName);
                } else {
                    reportTitle.setText("Report by Client: " + clientName);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }
}
