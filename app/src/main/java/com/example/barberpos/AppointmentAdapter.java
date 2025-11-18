package com.example.barberpos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.VH> {

    private final List<DashboardActivity.Appointment> items;

    public AppointmentAdapter(List<DashboardActivity.Appointment> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointment, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        DashboardActivity.Appointment appt = items.get(position);
        holder.tvTime.setText(appt.time);
        holder.tvCustomer.setText(appt.customer);
        holder.tvService.setText(appt.service);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvTime, tvCustomer, tvService;

        VH(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvCustomer = itemView.findViewById(R.id.tv_customer);
            tvService = itemView.findViewById(R.id.tv_service);
        }
    }
}

