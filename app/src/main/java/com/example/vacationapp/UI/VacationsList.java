package com.example.vacationapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vacationapp.R;
import com.example.vacationapp.database.Repository;
import com.example.vacationapp.entities.Excursion;
import com.example.vacationapp.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class VacationsList extends AppCompatActivity {

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacations_list);

        // Get reference to the floating action button (add VacationDetails button)
        FloatingActionButton fab=findViewById(R.id.floatingActionButton);

        // Set click listener for the button
        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                // Create intent to navigate to the VacationDetails screen
                Intent intent=new Intent(VacationsList.this, VacationDetails.class);

                // Start the VacationDetails activity
                startActivity(intent);
            }
        });
        // Get reference to the RecyclerView that displays the list of vacations
        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        // Initialize repository to access database operations
        repository = new Repository(getApplication());

        // Retrieve all vacations from the database
        List<Vacation> allVacations;
        try {
            allVacations = repository.getAllVacations();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Create adapter to bind vacation data to RecyclerView
        final VacationAdapter vacationAdapter = new VacationAdapter(this);

        // Attach adapter to RecyclerView
        recyclerView.setAdapter(vacationAdapter);

        // Set layout manager to define how items are displayed (vertical list)
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Pass vacation data to adapter so it can display the list
        vacationAdapter.setVacations(allVacations);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });
    }


    // Inflate (create) the options menu from XML and display it in the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Load menu layout from res/menu_vacation_list.xml
        getMenuInflater().inflate(R.menu.menu_vacation_list, menu);
        return true;
    }
    // Called every time the activity comes back into view
    @Override
    public void onResume(){
        super.onResume();
        // List to hold all vacations retrieved from the database
        List<Vacation> allVacations = null;

        // Fetch all vacations from the repository (database)
        try {
            allVacations = repository.getAllVacations();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Get reference to the RecyclerView that displays the list of vacations
        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        // Create a new adapter to bind vacation data to the RecyclerView
        final VacationAdapter vacationAdapter = new VacationAdapter(this);

        // Attach the adapter to the RecyclerView
        recyclerView.setAdapter(vacationAdapter);

        // Set layout manager to define how items are displayed (vertical list)
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Pass the list of vacations to the adapter so they can be displayed
        vacationAdapter.setVacations((allVacations));
    }

    // Handle user clicks on menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Check if sample menu item was selected
        if(item.getItemId()==R.id.sample){
            repository = new Repository(getApplication());

            // Vacation 1 and 2 Excursions added
            Vacation vacation = new Vacation(0, "Costa Rica", 200, "Hilton", "02/20/26", "03/01/26","hello", "Kathy Blick");
            repository.insert(vacation);
            Excursion excursion = new Excursion(0, "Boat Tour", 300, 1, "Hiking");
            repository.insert(excursion);
            excursion = new Excursion(0, "Dancing", 100, 1, "Bus Tour");
            repository.insert(excursion);

            // Vacation 2
            vacation = new Vacation(0, "Mexico", 100, "Best Western", "02/20/26", "03/01/26", "hello", "Bob Blick");
            repository.insert(vacation);


            return true;
        }
        // Handle back/up button in the toolbar
        if(item.getItemId()==android.R.id.home){
            // Close this activity and return to the previous screen
            this.finish();
            return true;
        }
        // Let Android handle any other menu actions
        return super.onOptionsItemSelected(item);
    }
}