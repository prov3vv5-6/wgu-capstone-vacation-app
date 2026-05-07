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

import com.example.vacationapp.R;
import com.example.vacationapp.database.Repository;
import com.example.vacationapp.entities.Excursion;
import com.example.vacationapp.entities.Vacation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExcursionDetails extends AppCompatActivity {

    String name;
    double price;
    int vacationId;
    int excursionId;
    EditText editName;
    EditText editPrice;
    Repository repository;
    Excursion currentExcursion;

    Vacation currentVacation = null;

    // Date Variables
    String date;
    TextView editDate;

    DatePickerDialog.OnDateSetListener dateListener;
    final Calendar myCalendar = Calendar.getInstance();
    String myFormat = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_excursion_details);
        repository = new Repository(getApplication());


        editName = findViewById(R.id.excursionName);
        editPrice = findViewById(R.id.excursionPrice);
        editDate = findViewById(R.id.excursionDate);


        name = getIntent().getStringExtra("name");
        price = getIntent().getDoubleExtra("price",0.0);
        excursionId = getIntent().getIntExtra("id", -1);
        vacationId = getIntent().getIntExtra("vacId", -1);
        date = getIntent().getStringExtra("date");

        editName.setText(name);
        editPrice.setText(Double.toString(price));
        editDate.setText(date);

        // Date Methods

        // Start Date listener
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                // get value from other screen, hard coded for now
                String info = editDate.getText().toString();
                if(info.equals("")) info = "04/22/26";
                try{
                    myCalendar.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(ExcursionDetails.this, dateListener, myCalendar.get(Calendar.YEAR),  myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        dateListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();

            }
        };




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursiondetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        // Check if the Save menu item was selected
        if(item.getItemId() == R.id.excursionsave) {

            // Validation for between vacation start and end dates
            try {
                for(Vacation vac: repository.getAllVacations()){
                    if(vac.getVacationId() == vacationId){
                        currentVacation = vac;
                    }
                }
            } catch (InterruptedException e){
                throw new RuntimeException(e);
            }
            if(currentVacation != null){
                try{
                    Date excursionDate = sdf.parse(editDate.getText().toString());
                    Date vacationStart = sdf.parse(currentVacation.getStartDate());
                    Date vacationEnd = sdf.parse(currentVacation.getEndDate());

                    if(excursionDate.before(vacationStart) || excursionDate.after(vacationEnd)){
                        Toast.makeText(this, "Excursion date must be within vacation dates", Toast.LENGTH_LONG).show();
                        return true;
                    }
                } catch(ParseException e) {
                    throw new RuntimeException(e);
                }
            }

            Excursion excursion;

            // If excursionId is -1, this is a new excursion that needs to be inserted
            if (excursionId == -1) {
                try {
                    // Manually generate a new ID based on the last excursion in the list
                    if (repository.getAllExcursions().size() == 0) excursionId = 1;
                    else
                        excursionId = repository.getAllExcursions().get(repository.getAllExcursions().size() - 1).getExcursionId() + 1;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                // Create a new excursion object using values from the input fields
                excursion = new Excursion(excursionId, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()), vacationId, editDate.getText().toString());

                // Insert the new excursion into the database
                repository.insert(excursion);

                Toast.makeText(ExcursionDetails.this, "Excursion saved successfully", Toast.LENGTH_SHORT).show();

                this.finish();

            } else {
                // If excursion is not -1, this is an existing excursion, so update it
                excursion = new Excursion(excursionId, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()), vacationId, editDate.getText().toString());

                // Update the existing excursion in the database
                repository.update(excursion);

                Toast.makeText(ExcursionDetails.this, "Excursion updated successfully", Toast.LENGTH_SHORT).show();
                this.finish();

            }
        }
        if(item.getItemId() == R.id.excursiondelete) {
            try{
                for(Excursion exc: repository.getAllExcursions()) {
                    if (exc.getExcursionId() == excursionId) {
                        currentExcursion = exc;
                    }
                }
                } catch (InterruptedException e){
                    throw new RuntimeException(e);
                }
            if(currentExcursion != null) {
                repository.delete(currentExcursion);
                Toast.makeText(ExcursionDetails.this, currentExcursion.getExcursionName() + " was deleted", Toast.LENGTH_LONG).show();

                // Close screen and return to Vacation List Screen
                Intent intent = new Intent(ExcursionDetails.this, VacationsList.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }
        // Notify/Alert date
        if(item.getItemId() == R.id.notify){
            String dateFromScreen = editDate.getText().toString();
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate = null;
            try{
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long trigger = myDate.getTime();
            Intent intent = new Intent(ExcursionDetails.this, MyReceiver.class);
            intent.putExtra("key", "Your " +  editName.getText().toString() + " excursion starts today!");
            PendingIntent sender = PendingIntent.getBroadcast(ExcursionDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager)  getSystemService((Context.ALARM_SERVICE));
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            return true;
        }
        return true;
    }

    // Excursion date label update
    private void updateLabel(){
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editDate.setText(sdf.format(myCalendar.getTime()));
    }
}