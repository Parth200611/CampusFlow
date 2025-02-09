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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

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
        etNoticeDate.setOnClickListener(v -> showNoticeDatePickerDialog());
        etJoiningDate.setOnClickListener(v -> showJoiningDatePickerDialog());
    }

    private void showNoticeDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            dob = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
            etNoticeDate.setText(dob);
        }, year, month, day);
        datePickerDialog.show();
    }

    private void showJoiningDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            joiningDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
            etJoiningDate.setText(joiningDate);
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
        worker();
    }

    private void worker() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("name",fullname);
        params.put("gender",selectedGender);
        params.put("address",address);
        params.put("mobileno",mobileNo);
        params.put("adhr",adharNo);
        params.put("status",selectedStatus);
        params.put("dob",dob);
        params.put("jdate",joiningDate);
        params.put("qulification",qualification);

        client.post(urls.addworker,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status=response.getString("success");
                    if (status.equals("1")){
                        Toast.makeText(AddWorker.this, "Data For Worker Is Added!", Toast.LENGTH_SHORT).show();
                        dataclear();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(AddWorker.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void dataclear() {
        etAddress.setText(""); etFullname.setText(""); etAdhrno.setText(""); etMobile.setText(""); etJoiningDate.setText(""); etNoticeDate.setText(""); etQualification.setText("");
    }
}