package com.example.barberpos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "BarberPOSPrefs";
    private static final String KEY_NAME = "key_name";
    private static final String KEY_EMAIL = "key_email";
    private static final String KEY_PASSWORD = "key_password";
    private static final String KEY_ROLE = "key_role";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextInputLayout tilName = findViewById(R.id.til_name);
        TextInputLayout tilEmail = findViewById(R.id.til_email_reg);
        TextInputLayout tilPassword = findViewById(R.id.til_password_reg);
        TextInputLayout tilConfirm = findViewById(R.id.til_confirm_password);

        TextInputEditText etName = findViewById(R.id.et_name);
        TextInputEditText etEmail = findViewById(R.id.et_email_reg);
        TextInputEditText etPassword = findViewById(R.id.et_password_reg);
        TextInputEditText etConfirm = findViewById(R.id.et_confirm_password);



        MaterialButton btnRegister = findViewById(R.id.btn_register);
        TextView tvBack = findViewById(R.id.tv_back_to_login);

        btnRegister.setOnClickListener(v -> {
            boolean valid = true;
            String name = etName.getText() == null ? "" : etName.getText().toString().trim();
            String email = etEmail.getText() == null ? "" : etEmail.getText().toString().trim();
            String password = etPassword.getText() == null ? "" : etPassword.getText().toString();
            String confirm = etConfirm.getText() == null ? "" : etConfirm.getText().toString();

            if (TextUtils.isEmpty(name)) {
                tilName.setError(getString(R.string.error_name_required));
                valid = false;
            } else {
                tilName.setError(null);
            }

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

            if (!password.equals(confirm)) {
                tilConfirm.setError(getString(R.string.error_password_mismatch));
                valid = false;
            } else {
                tilConfirm.setError(null);
            }

            if (!valid) return;



            // Save to SharedPreferences (NOTE: storing plain text password is not secure; replace with proper hashing in production)
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(KEY_NAME, name);
            editor.putString(KEY_EMAIL, email);
            editor.putString(KEY_PASSWORD, password);
            editor.apply();

            Toast.makeText(this, getString(R.string.registration_success_toast), Toast.LENGTH_SHORT).show();

            // Go to login
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });

        tvBack.setOnClickListener(v -> {
            // return to login
            finish();
        });
    }
}
