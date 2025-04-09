package com.hyperX.campusflow;

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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {
    TextView tvnewadmission;
    EditText etusername, etpassword;
    AppCompatButton btnlogin;
    CheckBox chshgowpassword;
    SharedPreferences sharedPreferences;
    String username, password;
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

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = etusername.getText().toString().trim();
                password = etpassword.getText().toString().trim();

                if (username.isEmpty()) {
                    etusername.setError("Enter Username or Enroll Number");
                } else if (password.isEmpty()) {
                    etpassword.setError("Enter Password");
                } else if (username.equals("Admin") && password.equals("Admin")) {
                    // Save login status for Admin
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.putString("userType", "Admin");
                    editor.apply();

                    // Redirect to Admin Homepage
                    Intent i = new Intent(LoginActivity.this, AdminHomepage.class);
                    startActivity(i);
                    finish();
                } else {
                    // Perform Student Login
                    Login();
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

    private void Login() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("enrollment", username);
        params.put("password", password);

        client.post(urls.studentlogin, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("success");
                    if (status.equals("1")) {
                        // Save login status for Student
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isLoggedIn", true);
                        editor.putString("userType", "Student");
                        editor.putString("loggedInUsername", username);
                        editor.apply();

                        // Redirect to Student Homepage
                        Intent i = new Intent(LoginActivity.this, Studenthomepage.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Enter Proper password and Enrollment No", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(LoginActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
