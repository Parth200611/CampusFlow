package com.mountreachsolution.campusflow;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminHomepage extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_homepage);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
        getWindow().setStatusBarColor(ContextCompat.getColor(AdminHomepage.this,R.color.blue));
        getWindow().setNavigationBarColor(ContextCompat.getColor(AdminHomepage.this,R.color.white));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        bottomNavigationView = findViewById(R.id.bottomnevigatiomuserhome);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        bottomNavigationView.setSelectedItemId(R.id.adminHome);

    }
    Homefragment homefragment = new Homefragment();
    NoticFragment noticFragment = new NoticFragment();
    profilfragmeent profilfragmeent1=new profilfragmeent();


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.adminHome){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutuserhome,homefragment).commit();
        }else if(item.getItemId()==R.id.adminNotice){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutuserhome,noticFragment).commit();
        } else if(item.getItemId()==R.id.adminProfil){
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutuserhome,profilfragmeent1).commit();
        }
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.admiinmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.adminHelp){
            Intent i = new Intent(AdminHomepage.this,Help.class);
            startActivity(i);

        } else if (item.getItemId() == R.id.adminViewworker) {
            Intent i = new Intent(AdminHomepage.this,ViewWorker.class);
            startActivity(i);

        } else if (item.getItemId() == R.id.adminViewleave) {
            Intent i = new Intent(AdminHomepage.this,ViewLeaveRequest.class);
            startActivity(i);

        }

        return true;
    }
}