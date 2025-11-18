package com.example.barberpos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "BarberPOSPrefs";
    private static final String KEY_EMAIL = "key_email";
    private static final String KEY_PASSWORD = "key_password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // After splash (windowBackground) is shown, switch back to the regular app theme
        setTheme(R.style.Theme_BarberPOS);
        setContentView(R.layout.activity_login);

        TextInputLayout tilEmail = findViewById(R.id.til_email);
        TextInputLayout tilPassword = findViewById(R.id.til_password);
        TextInputEditText etEmail = findViewById(R.id.et_email);
        TextInputEditText etPassword = findViewById(R.id.et_password);
        MaterialButton btnLogin = findViewById(R.id.btn_login);
        TextView tvForgot = findViewById(R.id.tv_forgot);
        TextView tvCreate = findViewById(R.id.tv_create_account);

        btnLogin.setOnClickListener(v -> {
            // simple validation
            boolean valid = true;
            String email = etEmail.getText() == null ? "" : etEmail.getText().toString().trim();
            String password = etPassword.getText() == null ? "" : etPassword.getText().toString();

            if (TextUtils.isEmpty(email)) {
                tilEmail.setError(getString(R.string.error_email_required));
                valid = false;
            } else {
                tilEmail.setError(null);
            }

            if (TextUtils.isEmpty(password)) {
                tilPassword.setError(getString(R.string.error_password_required));
                valid = false;
            } else {
                tilPassword.setError(null);
            }

            if (!valid) return;

            // Authenticate against saved credentials in SharedPreferences first
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            String savedEmail = prefs.getString(KEY_EMAIL, null);
            String savedPassword = prefs.getString(KEY_PASSWORD, null);

            boolean authenticated = false;
            if (savedEmail != null && savedPassword != null) {
                if (savedEmail.equalsIgnoreCase(email) && savedPassword.equals(password)) {
                    authenticated = true;
                }
            }

            // fallback to default admin credentials if no saved account matches
            if (!authenticated) {
                if ("admin@barber.local".equalsIgnoreCase(email) && "password".equals(password)) {
                    authenticated = true;
                }
            }

            if (authenticated) {
                // Open the DashboardActivity instead of MainActivity
                startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                finish();
            } else {
                Toast.makeText(this, getString(R.string.error_invalid_credentials), Toast.LENGTH_SHORT).show();
            }
        });

        tvForgot.setOnClickListener(v -> Toast.makeText(this, getString(R.string.forgot_password_toast), Toast.LENGTH_SHORT).show());

        tvCreate.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }
}
