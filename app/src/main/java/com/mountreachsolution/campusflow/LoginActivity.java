package com.mountreachsolution.campusflow;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {
    TextView tvnewadmission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
       getWindow().setNavigationBarColor(ContextCompat.getColor(LoginActivity.this,R.color.white));
       tvnewadmission=findViewById(R.id.tvnewadmission);

       tvnewadmission.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i = new Intent(LoginActivity.this,AdmissionFrom.class);
               startActivity(i);
           }
       });
    }
}