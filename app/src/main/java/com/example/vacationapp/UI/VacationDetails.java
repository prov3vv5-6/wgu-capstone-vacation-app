package com.example.vacationapp.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VacationDetails extends AppCompatActivity {

    String name;
    double price;
    int vacationId;
    EditText editName;
    EditText editPrice;
    EditText editHotel;
    EditText editNote;
    EditText editClientName;
//    TextView editClientName;
    String clientName;



    //Date Variables
    TextView editStartDate;
    TextView editEndDate;
    String startDate;
    String endDate;
    DatePickerDialog.OnDateSetListener startDateListener;
    DatePickerDialog.OnDateSetListener endDateListener;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    String myFormat = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    Repository repository;
    Vacation currentVacation;
    int numExcursions;
    String hotel;

    String note;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_details);

        // Get reference to the floating action button (add excursion button)
        FloatingActionButton fab=findViewById(R.id.floatingActionButton2);

        editName = findViewById(R.id.titletext);
        editPrice = findViewById(R.id.pricetext);
        editHotel = findViewById(R.id.hoteltext);
        editStartDate = findViewById(R.id.startdatetext);
        editEndDate = findViewById(R.id.enddatetext);
        editNote = findViewById(R.id.note);
        editClientName = findViewById(R.id.clientfullnametext);


        vacationId = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra(("name"));
        price = getIntent().getDoubleExtra("price", 0.0);
        hotel = getIntent().getStringExtra("hotel");
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");
        note = getIntent().getStringExtra("editNote");
        clientName = getIntent().getStringExtra("clientName");

        editName.setText(name);
        editPrice.setText(Double.toString(price));
        editHotel.setText(hotel);
        editStartDate.setText(startDate);
        editEndDate.setText(endDate);
        editNote.setText(note);
        editClientName.setText(clientName);

        // Date Methods

        // Start Date listener
        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                // get value from other screen, hard coded for now
                String info = editStartDate.getText().toString();
                if(info.equals("")) info = "04/22/26";
                try{
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(VacationDetails.this, startDateListener, myCalendarStart.get(Calendar.YEAR),  myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        startDateListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();

            }
        };

        // End Date Listener

        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                // get value from other screen, hard coded for now
                String info = editEndDate.getText().toString();
                if(info.equals("")) info = "04/23/26";
                try{
                    myCalendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(VacationDetails.this, endDateListener, myCalendarEnd.get(Calendar.YEAR),  myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDateListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, month);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();

            }
        };





        // Open ExcursionDetails screen when the button is clicked
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                // If vacation hasn't been saved yet
                if(vacationId == -1){
                    Toast.makeText(VacationDetails.this, "Please save the vacation before adding excursions", Toast.LENGTH_LONG).show();
                    return;
                }
                // Create intent to navigate to ExcursionDetails screen
                Intent intent=new Intent(VacationDetails.this, ExcursionDetails.class);

                // Pass the current vacationId to the next screen
                intent.putExtra("vacId", vacationId);

                // Start the ExcursionDetails activity
                startActivity(intent);
            }
        });
        // Get reference to the RecyclerView from the layout
        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);

        // Initialize the repository to access database operations
        repository = new Repository(getApplication());

        // Create a new adapter to manage Excursion data for the RecyclerView
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);

        // Attach the adapter to the RecyclerView
        recyclerView.setAdapter(excursionAdapter);

        // Set the layout manager to control how items are displayed (vertical list)
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create a list to store only the excursions related to the current vacation
        List<Excursion> filteredExcursions = new ArrayList<>();

        try {
            // Loop through all excursions retrieved from the database
            for(Excursion e: repository.getAllExcursions()) {

                // Check if the excursion belongs to the current vacation
                if(e.getVacationId() == vacationId)

                    // If it matches, add it to the filtered list
                    filteredExcursions.add(e);

            }

        // After filtering is complete, pass the list to the adapter to display in the UI
        excursionAdapter.setExcursions(filteredExcursions);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacationdetails, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        // Check if the Save menu item was selected
        if(item.getItemId() == R.id.vacationsave){
            Vacation vacation;

            // If vacationId is -1, this is a new vacation that needs to be inserted
            if(vacationId == -1){
                try {
                    // Manually generate a new ID based on the last vacation in the list
                    if(repository.getAllVacations().size() == 0) vacationId = 1;
                    else vacationId = repository.getAllVacations().get(repository.getAllVacations().size() - 1).getVacationId() + 1;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                // Create a new Vacation object using values from the input fields
                vacation = new Vacation(vacationId, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()), editHotel.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString(), editNote.getText().toString(), editClientName.getText().toString());

                // Checks to make sure end date is after the start date
                if(!myCalendarEnd.after(myCalendarStart)){
                    Toast.makeText(this, "End date must be after start date", Toast.LENGTH_LONG).show();
                    return true;
                }

                // Insert the new vacation into the database
                repository.insert(vacation);

                // Close this screen and return to the previous one
                this.finish();
            }
            else{
                // If vacationId is not -1, this is an existing vacation, so update it
                vacation = new Vacation(vacationId, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()), editHotel.getText().toString(), editStartDate.getText().toString(),editEndDate.getText().toString(), editNote.getText().toString(), editClientName.getText().toString());

                // Checks to make sure end date is after the start date
                if(!myCalendarEnd.after(myCalendarStart)){
                    Toast.makeText(this, "End date must be after start date", Toast.LENGTH_LONG).show();
                    return true;
                }

                // Update the existing vacation in the database
                repository.update(vacation);

                // Close this screen and return to the previous one
                this.finish();
            }
        }
        // Share Note
        if(item.getItemId() == R.id.share){

            String shareText =
                    "Title: " + editName.getText().toString() + "\n" +
                    "Price: " + editPrice.getText().toString() + "\n" +
                    "Hotel: " + editHotel.getText().toString() + "\n" +
                    "Start Date: " + editStartDate.getText().toString() + "\n" +
                    "End Date: " + editEndDate.getText().toString() + "\n" +
                    "Note: " + editNote.getText().toString();
            Intent sentIntent = new Intent();
            sentIntent.setAction(Intent.ACTION_SEND);
            Toast.makeText(this, shareText, Toast.LENGTH_LONG).show();
            sentIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            sentIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sentIntent, null);
            startActivity(shareIntent);
            return true;
        }

        // Notify/Alert start date
        if(item.getItemId() == R.id.startdatenotify){
            String dateFromScreen = editStartDate.getText().toString();
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate = null;
            try{
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long trigger = myDate.getTime();
            Intent intent = new Intent(VacationDetails.this, MyReceiver.class);
            intent.putExtra("key", "Your vacation to " +  editName.getText().toString() + " starts today!");
            PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager)  getSystemService((Context.ALARM_SERVICE));
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            return true;
        }

        // Notify/Alert end date
        if(item.getItemId() == R.id.enddatenotify){
            String dateFromScreen = editEndDate.getText().toString();
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate = null;
            try{
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long trigger = myDate.getTime();
            Intent intent = new Intent(VacationDetails.this, MyReceiver.class);
            intent.putExtra("key", "Your vacation in " +  editName.getText().toString() + " ends today 🥺");
            PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager)  getSystemService((Context.ALARM_SERVICE));
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            return true;
        }

        // Check if the Delete menu item was selected
        if(item.getItemId() == R.id.vacationdelete){
            try {
                // Find the current vacation object that matches the selected vacationId
                for(Vacation vac: repository.getAllVacations()){
                    if(vac.getVacationId() == vacationId) currentVacation = vac;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // Count how many excursions are associated with this vacation
            numExcursions = 0;
            try {
                for(Excursion excursion: repository.getAllExcursions()){
                    if(excursion.getVacationId() == vacationId) ++ numExcursions;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // Only allow deletion if there are no associated excursions
            if(numExcursions == 0) {
                repository.delete(currentVacation);
                // Show confirmation message that the vacation was deleted
                Toast.makeText(VacationDetails.this, currentVacation.getVacationName() + " was deleted", Toast.LENGTH_LONG).show();
                // Close this screen after deleting
                this.finish();
            }
            else{
                // Show message if the vacation cannot be deleted
                Toast.makeText(VacationDetails.this, "Can't delete a vacation with associated excursions", Toast.LENGTH_LONG).show();
            }
        }
        return true;
    }

    // Start Date Label
    private void updateLabelStart(){
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editStartDate.setText(sdf.format(myCalendarStart.getTime()));
    }
    // End Date Label
    private void updateLabelEnd(){
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editEndDate.setText(sdf.format(myCalendarEnd.getTime()));
    }
}