package com.hyperX.campusflow;

import static android.app.Activity.RESULT_OK;

import static com.hyperX.campusflow.profilpage.PREFS_NAME;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;


public class addleverequest extends Fragment {
    EditText etname,etnumber,etselectdate,etdiscription,etlevedate,etroom;
    String selectdate,levedate,enrollment;
    ImageView ivimage;
    Button btnaddimage,btnpost;
    Bitmap bitmap;
    Uri filepath;
    private  int pick_image_request=789;

    private static final int PICK_IMAGE_REQUEST_PASS = 1;
    String selectedLeaveDate = "";
    String selectedLeavingDate = "";
    SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sharedPreferences = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Fetch username
        enrollment = sharedPreferences.getString("loggedInUsername", "Guest");
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_addleverequest, container, false);
        etselectdate = view.findViewById(R.id.et_leave_date);
        etlevedate = view.findViewById(R.id.et_leaving_date);
        ivimage = view.findViewById(R.id.iv_uploaded_image);
        btnaddimage = view.findViewById(R.id.btn_add_image);
        btnpost = view.findViewById(R.id.btn_submit);
        etname = view.findViewById(R.id.et_name);
        etnumber = view.findViewById(R.id.et_mobile);
        etdiscription = view.findViewById(R.id.et_description);
        etroom = view.findViewById(R.id.et_room);

        btnpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


        etselectdate.setOnClickListener(v -> openDatePicker(etselectdate, true));

        // Date Picker for Leaving Date
        etlevedate.setOnClickListener(v -> openDatePicker(etlevedate, false));


        btnaddimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectUserProfileimage();
            }
        });
        return view;
    }

    private void postData() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("name",etname.getText().toString());
        params.put("mobileno",etnumber.getText().toString());
        params.put("room",etroom.getText().toString());
        params.put("selectdate",selectdate);
        params.put("dis",etdiscription.getText().toString());
        params.put("ldate",selectedLeaveDate);
        params.put("enroll",enrollment);

        client.post(urls.addleavedata,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status=response.getString("success");
                    if (status.equals("1")){
                        Toast.makeText(getActivity(), "Request send", Toast.LENGTH_SHORT).show();
                        UserImageSaveTodatabase(bitmap,enrollment);
                        clerdata();
                    }else{
                        Toast.makeText(getActivity(), "Fail to send Request", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void clerdata() {
        etnumber.setText("");
        etname.setText("");
        etroom.setText("");
        etdiscription.setText("");
        etselectdate.setText("");
        etlevedate.setText("");
        ivimage.setImageDrawable(null); // Clear ImageView
        selectdate = "";
        levedate = "";

    }

    private void openDatePicker(EditText editText, boolean isLeaveDate) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                (DatePicker view, int selectedYear, int selectedMonth, int selectedDay) -> {
                    String formattedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    editText.setText(formattedDate);

                    // Save the selected date in a string
                    if (isLeaveDate) {
                        selectedLeaveDate = formattedDate;
                    } else {
                        selectedLeavingDate = formattedDate;
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private void SelectUserProfileimage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Image For Profil"),pick_image_request);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==pick_image_request && resultCode==RESULT_OK && data!=null){
            filepath=data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),filepath);
                ivimage.setImageBitmap(bitmap);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void UserImageSaveTodatabase(Bitmap bitmap, String strTitle) {
        VolleyMultipartRequest volleyMultipartRequest =  new VolleyMultipartRequest(Request.Method.POST, urls.leaveimage, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                Toast.makeText(getActivity(), "Image Save as Notice "+strTitle, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                String errorMsg = error.getMessage();
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    errorMsg = new String(error.networkResponse.data);
                }
                Log.e("UploadError", errorMsg);
                Toast.makeText(getActivity(), "Upload Error: " + errorMsg, Toast.LENGTH_LONG).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap<>();
                parms.put("tags", strTitle); // Adjusted to match PHP parameter name
                return parms;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String,VolleyMultipartRequest.DataPart> parms = new HashMap<>();
                long imagename = System.currentTimeMillis();
                parms.put("pic",new VolleyMultipartRequest.DataPart(imagename+".jpeg",getfiledatafromBitmap(bitmap)));

                return parms;

            }

        };
        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
    }
    private byte[] getfiledatafromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream  = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


}