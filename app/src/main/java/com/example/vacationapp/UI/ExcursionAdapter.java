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
import com.example.vacationapp.entities.Excursion;
import com.example.vacationapp.entities.Vacation;


import java.util.List;

public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ExcursionViewHolder> {
    // List of excursions to display
    private List<Excursion> mExcursion;

    // Context used for inflating layouts and starting activities
    private final Context context;

    // Inflater converts XML layout into actual View objects
    private final LayoutInflater mInflater;



    // ViewHolder represents ONE item (row) in the RecyclerView
    public class ExcursionViewHolder extends RecyclerView.ViewHolder {

        // TextViews that displays the excursion name
        private final TextView excursionItemView;
        private final TextView excursionItemView2;
        private ExcursionViewHolder(@NonNull View itemView) {
            super(itemView);

            // Link TextView from XML
            excursionItemView = itemView.findViewById(R.id.textView3);
            excursionItemView2 = itemView.findViewById(R.id.textView4);

            // Set click listener for each item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Get position of clicked item
                    int position = getAbsoluteAdapterPosition();

                    // Get the selected excursion object
                    final Excursion current = mExcursion.get(position);

                    // Create intent to open ExcursionDetails screen
                    Intent intent = new Intent(context,ExcursionDetails.class);

                    // Pass data to next screen
                    intent.putExtra("id", current.getExcursionId());
                    intent.putExtra("name", current.getExcursionName());
                    intent.putExtra("price", current.getPrice());
                    intent.putExtra("vacId", current.getVacationId());
                    intent.putExtra("date", current.getDate());

                    // Start the new activity
                    context.startActivity(intent);
                }
            });
        }
    }

    // Creates a new ViewHolder (inflates the layout for each row)
    @NonNull
    @Override
    public ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.excursion_list_item, parent, false);
        return new ExcursionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExcursionViewHolder holder, int position) {
        if(mExcursion!=null){
            Excursion current = mExcursion.get(position);
            String name = current.getExcursionName();
            int vacationId = current.getVacationId();
            holder.excursionItemView.setText(name);
            holder.excursionItemView2.setText(Integer.toString(vacationId));

        }
        else{
            holder.excursionItemView.setText("No excursion name");
            holder.excursionItemView2.setText("No excursion id");
        }

    }
    public void setExcursions(List<Excursion> excursion){
        mExcursion = excursion;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mExcursion != null) {
            return mExcursion.size();
        }
        else return 0;
    }

    // Constructor initializes inflater and context
    public ExcursionAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }
}
