package com.hyperX.campusflow;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_help);
        getWindow().setStatusBarColor(ContextCompat.getColor(Help.this,R.color.blue));
        getWindow().setNavigationBarColor(ContextCompat.getColor(Help.this,R.color.white));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Button btnCallPolice = findViewById(R.id.btnCallPolice);
        Button btnCallAmbulance = findViewById(R.id.btnCallAmbulance);
        Button btnCallDoctor = findViewById(R.id.btnCallDoctor);
        Button btnCallFireBrigade = findViewById(R.id.btnCallFireBrigade);

        // Set click listeners
        btnCallPolice.setOnClickListener(v -> makeCall("100")); // Police Helpline
        btnCallAmbulance.setOnClickListener(v -> makeCall("102")); // Ambulance Helpline
        btnCallDoctor.setOnClickListener(v -> makeCall("104")); // Medical Helpline
        btnCallFireBrigade.setOnClickListener(v -> makeCall("101")); // Fire Brigade
    }

    private  void makeCall(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }
}