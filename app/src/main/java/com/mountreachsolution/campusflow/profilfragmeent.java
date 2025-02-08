package com.mountreachsolution.campusflow;

import static com.mountreachsolution.campusflow.LoginActivity.PREFS_NAME;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class profilfragmeent extends Fragment {
    AppCompatButton btnlogout;

   
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      
        View view = inflater.inflate(R.layout.fragment_profilfragmeent, container, false);
        
        btnlogout = view.findViewById(R.id.btnLogout);
        
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser();
            }
        });
        
        return view;
    }

    private void logoutUser() {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(LoginActivity.PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.clear(); // Clear login data
        editor.apply();

        // Redirect to LoginActivity
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear activity stack
        startActivity(intent);
        getActivity().finish();
    }

}