package com.mountreachsolution.campusflow;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowInsetsController;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Studenthomepage extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_studenthomepage);

        getWindow().setNavigationBarColor(ContextCompat.getColor(Studenthomepage.this,R.color.white));  getWindow().setStatusBarColor(ContextCompat.getColor(Studenthomepage.this,R.color.blue));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        bottomNavigationView = findViewById(R.id.bottomnevigatiomuserhome);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.Home);


    }
    studenthome studenthome1=new studenthome();
    addleverequest addleverequest1=new addleverequest();
    profilpage profilpage1=new profilpage();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.Home){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutuserhome,studenthome1).commit();
        }else if(item.getItemId()==R.id.addreequest){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutuserhome,addleverequest1).commit();
        } else if(item.getItemId()==R.id.profil){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutuserhome,profilpage1).commit();
        }
        return true;
    }
}