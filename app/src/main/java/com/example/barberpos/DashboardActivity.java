package com.example.barberpos;

import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "BarberPOSPrefs";
    private static final String KEY_NAME = "key_name";
    private static final String KEY_ROLE = "key_role";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        TextView tvGreeting = findViewById(R.id.tv_greeting);
        TextView tvTodayLabel = findViewById(R.id.tv_today_label);
        LinearLayout llAppointments = findViewById(R.id.ll_appointments_container);

        ImageButton btnProfile = findViewById(R.id.btn_profile);
        ImageButton btnLogout = findViewById(R.id.btn_logout);
        View cardAppointments = findViewById(R.id.card_appointments);
        View cardCustomers = findViewById(R.id.card_customers);
        View cardServices = findViewById(R.id.card_services);
        View cardReports = findViewById(R.id.card_reports);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String name = prefs.getString(KEY_NAME, "User");
        String role = prefs.getString(KEY_ROLE, getString(R.string.role_employee));

        tvGreeting.setText(getString(R.string.dashboard_welcome, name));

        // role-based UI
        if (getString(R.string.role_employee).equalsIgnoreCase(role) || "Employee".equalsIgnoreCase(role)) {
            tvTodayLabel.setVisibility(View.VISIBLE);
            llAppointments.setVisibility(View.VISIBLE);
            setupAppointmentsList(llAppointments);
        } else {
            tvTodayLabel.setVisibility(View.GONE);
            llAppointments.setVisibility(View.GONE);
        }

        btnProfile.setOnClickListener(v -> Toast.makeText(this, "Profile tapped", Toast.LENGTH_SHORT).show());

        // Logout handler: clear saved session and navigate back to LoginActivity
        btnLogout.setOnClickListener(v -> {
            // Clear only session-related preferences (keep saved credentials intact)
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove(KEY_NAME);
            editor.remove(KEY_ROLE);
            editor.apply();

            Toast.makeText(DashboardActivity.this, getString(R.string.logout_toast), Toast.LENGTH_SHORT).show();

            // Start LoginActivity and clear activity stack so user cannot navigate back
            Intent i = new Intent(DashboardActivity.this, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        });

        cardAppointments.setOnClickListener(v -> Toast.makeText(this, "Open Appointments", Toast.LENGTH_SHORT).show());
        cardCustomers.setOnClickListener(v -> Toast.makeText(this, "Open Customers", Toast.LENGTH_SHORT).show());
        cardServices.setOnClickListener(v -> Toast.makeText(this, "Open Services", Toast.LENGTH_SHORT).show());
        cardReports.setOnClickListener(v -> Toast.makeText(this, "Open Reports", Toast.LENGTH_SHORT).show());
    }

    private void setupAppointmentsList(LinearLayout container) {
        // For demo purposes create sample appointments. In real app load from DB or API.
        List<Appointment> items = new ArrayList<>();
        items.add(new Appointment("09:00", "John Doe", "Haircut"));
        items.add(new Appointment("10:30", "Jane Smith", "Shave"));
        items.add(new Appointment("13:00", "Alex Johnson", "Cut & Style"));

        LayoutInflater inflater = LayoutInflater.from(this);
        container.removeAllViews();
        for (Appointment appt : items) {
            View v = inflater.inflate(R.layout.item_appointment, container, false);
            TextView tvTime = v.findViewById(R.id.tv_time);
            TextView tvCustomer = v.findViewById(R.id.tv_customer);
            TextView tvService = v.findViewById(R.id.tv_service);
            tvTime.setText(appt.time);
            tvCustomer.setText(appt.customer);
            tvService.setText(appt.service);
            container.addView(v);
        }
    }

    // Simple model
    public static class Appointment {
        public final String time;
        public final String customer;
        public final String service;

        public Appointment(String time, String customer, String service) {
            this.time = time;
            this.customer = customer;
            this.service = service;
        }
    }
}
