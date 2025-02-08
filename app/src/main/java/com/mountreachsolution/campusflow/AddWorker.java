package com.mountreachsolution.campusflow;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class AddWorker extends AppCompatActivity {
     EditText etFullname, etAdhrno, etMobile, etAddress, etNoticeDate, etJoiningDate, etQualification;
     Spinner spinnerStatus, spinnerGender;
     Button btnAddWorker;
     String selectedStatus, selectedGender, fullname, adharNo, mobileNo, address, dob, joiningDate, qualification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_worker);

        etFullname = findViewById(R.id.etFullname);
        etAdhrno = findViewById(R.id.etAdhrno);
        etMobile = findViewById(R.id.etMobile);
        etAddress = findViewById(R.id.etAddress);
        etNoticeDate = findViewById(R.id.etNoticeDate);
        etJoiningDate = findViewById(R.id.etjoiningdate);
        etQualification = findViewById(R.id.etcolification);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        spinnerGender = findViewById(R.id.spinnerGender);
        btnAddWorker = findViewById(R.id.btnAddworker);

        setupSpinners();

        // Setup Date Pickers
        setupDatePickers();

        // Button Click Listener
        btnAddWorker.setOnClickListener(v -> validateAndSaveData());

    }

    private void setupSpinners() {
        // Status Spinner
        String[] statusOptions = {"Select Status", "Full Time", "Part Time","Temporary","Contract"};
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, statusOptions);
        spinnerStatus.setAdapter(statusAdapter);
        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStatus = statusOptions[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Gender Spinner
        String[] genderOptions = {"Select Gender", "Male", "Female", "Other"};
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genderOptions);
        spinnerGender.setAdapter(genderAdapter);
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedGender = genderOptions[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupDatePickers() {
        etNoticeDate.setOnClickListener(v -> showDatePickerDialog(etNoticeDate));
        etJoiningDate.setOnClickListener(v -> showDatePickerDialog(etJoiningDate));
    }

    private void showDatePickerDialog(EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
            editText.setText(selectedDate);
        }, year, month, day);
        datePickerDialog.show();
    }

    private void validateAndSaveData() {
        // Get values from EditText fields
        fullname = etFullname.getText().toString().trim();
        adharNo = etAdhrno.getText().toString().trim();
        mobileNo = etMobile.getText().toString().trim();
        address = etAddress.getText().toString().trim();
        dob = etNoticeDate.getText().toString().trim();
        joiningDate = etJoiningDate.getText().toString().trim();
        qualification = etQualification.getText().toString().trim();

        // Validation
        if (selectedStatus.equals("Select Status") || selectedGender.equals("Select Gender") ||
                fullname.isEmpty() || adharNo.isEmpty() || mobileNo.isEmpty() || address.isEmpty() ||
                dob.isEmpty() || joiningDate.isEmpty() || qualification.isEmpty()) {

            Toast.makeText(this, "Please fill all the fields correctly!", Toast.LENGTH_LONG).show();
            return;
        }

        // Save data (For now, just show a success message)
        Toast.makeText(this, "Worker Added Successfully!", Toast.LENGTH_SHORT).show();
    }
}