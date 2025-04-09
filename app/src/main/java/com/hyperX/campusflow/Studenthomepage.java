package com.hyperX.campusflow;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

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
    LeaveRequest addleverequest1=new LeaveRequest();
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.studentmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.studentHelp){
            Intent i = new Intent(Studenthomepage.this,Help.class);
            startActivity(i);

        } else if (item.getItemId() == R.id.viewrequest) {
            Intent i = new Intent(Studenthomepage.this,studentViewLeaveRequest.class);
            startActivity(i);

        }else if (item.getItemId() == R.id.paybill) {
            Intent i = new Intent(Studenthomepage.this,PayBill.class);
            startActivity(i);

        } else if (item.getItemId() == R.id.admissiondetails) {
            Intent i = new Intent(Studenthomepage.this,ViewAdmissiondetails.class);
            startActivity(i);

        }

        return true;
    }
}