package com.mountreachsolution.campusflow;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

public class LoginActivity extends AppCompatActivity {
    TextView tvnewadmission;
    EditText etusername, etpassword;
    AppCompatButton btnlogin;
    CheckBox chshgowpassword;
    SharedPreferences sharedPreferences;
    public static final String PREFS_NAME = "LoginPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        getWindow().setNavigationBarColor(ContextCompat.getColor(LoginActivity.this, R.color.white));

        tvnewadmission = findViewById(R.id.tvnewadmission);
        etpassword = findViewById(R.id.etpassword);
        etusername = findViewById(R.id.etusername);
        btnlogin = findViewById(R.id.btnLogin);
        chshgowpassword = findViewById(R.id.cbShowPassword);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Check if already logged in
        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            startActivity(new Intent(LoginActivity.this, AdminHomepage.class));
            finish(); // Close LoginActivity
        }

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etusername.getText().toString().trim();
                String password = etpassword.getText().toString().trim();

                if (username.isEmpty()) {
                    etusername.setError("Enter Username or Enroll Number");
                } else if (password.isEmpty()) {
                    etpassword.setError("Enter Password");
                } else if (username.equals("Admin") && password.equals("Admin")) {
                    // Save login status
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();

                    Intent i = new Intent(LoginActivity.this, AdminHomepage.class);
                    startActivity(i);
                    finish(); // Close LoginActivity
                }
            }
        });

        chshgowpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    etpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                etpassword.setSelection(etpassword.length()); // Move cursor to end
            }
        });

        tvnewadmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, AdmissionFrom.class);
                startActivity(i);
            }
        });
    }
}
