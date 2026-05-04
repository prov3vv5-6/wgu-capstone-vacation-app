/*
This Class
takes a list of vacations
displays them in a RecyclerView
handles clicking on a vacation
sends that data to VacationDetails
*/

package com.example.vacationapp.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vacationapp.R;
import com.example.vacationapp.entities.Vacation;

import java.util.List;

// Adapter connects data (Vacations) to the RecyclerView UI
public class VacationAdapter extends RecyclerView.Adapter<VacationAdapter.VacationViewHolder> {
    // List of vacations to display
    private List<Vacation> mVacations;

    // Context used for inflating layouts and starting activities
    private final Context context;

    // Inflater converts XML layout into actual View objects
    private final LayoutInflater mInflater;

    // Constructor initializes inflater and context
    public VacationAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    // ViewHolder represents ONE item (row) in the RecyclerView
    public class VacationViewHolder extends RecyclerView.ViewHolder {

        // TextView that displays the vacation name
        private final TextView vacationItemView;
        public VacationViewHolder(@NonNull View itemView) {
            super(itemView);

            // Link TextView from XML
            vacationItemView = itemView.findViewById(R.id.textView2);

            // Set click listener for each item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Get position of clicked item
                    int position = getAbsoluteAdapterPosition();

                    // Get the selected vacation object
                    final Vacation current = mVacations.get(position);

                    // Create intent to open VacationDetails screen
                    Intent intent = new Intent(context,VacationDetails.class);

                    // Pass data to next screen
                    intent.putExtra("id", current.getVacationId());
                    intent.putExtra("name", current.getVacationName());
                    intent.putExtra("price", current.getPrice());
                    intent.putExtra("hotel", current.getHotel());
                    intent.putExtra("startDate", current.getStartDate());
                    intent.putExtra("endDate", current.getEndDate());
                    intent.putExtra("note", current.getNote());
                    intent.putExtra("clientName", current.getClientFullName());

                    // Start the new activity
                    context.startActivity(intent);
                }
            });
        }
    }

    // Creates a new ViewHolder (inflates the layout for each row)
    @NonNull
    @Override
    public VacationAdapter.VacationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Inflate the layout for a single list item
        View itemView = mInflater.inflate(R.layout.vacation_list_item,parent,false);

        return new VacationViewHolder(itemView);
    }

    // Binds data to each ViewHolder (sets text, etc.)
    @Override
    public void onBindViewHolder(@NonNull VacationAdapter.VacationViewHolder holder, int position) {
        if(mVacations!=null){
            Vacation current = mVacations.get(position);
            String name = current.getVacationName();
            holder.vacationItemView.setText(name);

        }
        else{
            holder.vacationItemView.setText("No product name");
        }
    }

    // Returns total number of items in the list
    @Override
    public int getItemCount() {
        if(mVacations!=null){
            return mVacations.size();
        }
        else return 0;
    }

    // Updates the list of vacations refreshes the UI
    public void setVacations(List<Vacation> vacations){
        mVacations = vacations;
        notifyDataSetChanged(); // tells RecyclerView to redraw
    }

}
