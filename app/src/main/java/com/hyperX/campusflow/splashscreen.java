package com.hyperX.campusflow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class splashscreen extends AppCompatActivity {
    private static final int SPLASH_TIME = 3000; // 3 seconds
    public static final String PREFS_NAME = "LoginPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splashscreen);

        // Ensure app does not follow system night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Hide the status bar and navigation bar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );

        // Initialize SharedPreferences properly
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        String userType = sharedPreferences.getString("userType", "");

        // Use Handler correctly
        new Handler().postDelayed(() -> {
            Intent intent;
            if (isLoggedIn) {
                if ("Admin".equals(userType)) {
                    intent = new Intent(splashscreen.this, AdminHomepage.class);
                } else if ("Student".equals(userType)) {
                    intent = new Intent(splashscreen.this, Studenthomepage.class);
                } else {
                    intent = new Intent(splashscreen.this, LoginActivity.class);
                }
            } else {
                intent = new Intent(splashscreen.this, LoginActivity.class);
            }

            startActivity(intent);
            finish(); // Close SplashScreen

        }, SPLASH_TIME);
    }
}
