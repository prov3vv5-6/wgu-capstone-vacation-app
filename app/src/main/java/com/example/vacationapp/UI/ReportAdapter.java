package com.example.vacationapp.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vacationapp.R;
import com.example.vacationapp.entities.Vacation;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    private List<Vacation> vacations;
    private final Context context;
    private final LayoutInflater inflater;

    public ReportAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }


    public class ReportViewHolder extends RecyclerView.ViewHolder {

        TextView reportVacationTitle;
        TextView reportClientName;
        TextView reportPrice;
        TextView reportHotel;
        TextView reportStartDate;
        TextView reportEndDate;
        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);

            reportVacationTitle = itemView.findViewById(R.id.reportVacationTitle);
            reportClientName = itemView.findViewById(R.id.reportClientName);
            reportPrice = itemView.findViewById(R.id.reportPrice);
            reportHotel = itemView.findViewById(R.id.reportHotel);
            reportStartDate = itemView.findViewById(R.id.reportStartDate);
            reportEndDate = itemView.findViewById(R.id.reportEndDate);
        }
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = inflater.inflate(R.layout.report_list_item, parent, false);
        return new ReportViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.ReportViewHolder holder, int position) {

        Vacation current = vacations.get(position);

        holder.reportVacationTitle.setText("Title: " + current.getVacationName());
        holder.reportClientName.setText("Client Full Name: " + current.getClientFullName());
        holder.reportPrice.setText("Price: $" + current.getPrice());
        holder.reportHotel.setText("Hotel: " + current.getHotel());
        holder.reportStartDate.setText("Start Date: " + current.getStartDate());
        holder.reportEndDate.setText("End Date: " + current.getEndDate());
    }

    @Override
    public int getItemCount() {
        if (vacations == null) {
            return 0;
        }
        return vacations.size();
    }
    public void setVacations(List<Vacation> vacations){
        this.vacations = vacations;
        notifyDataSetChanged();
    }

}
