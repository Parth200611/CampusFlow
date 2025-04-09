package com.hyperX.campusflow;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class Adpterwork extends RecyclerView.Adapter<Adpterwork.ViewHolder> {
    List<POJOwork> pojOworkList;
    Activity activity;

    public Adpterwork(List<POJOwork> pojOworkList, Activity activity) {
        this.pojOworkList = pojOworkList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public Adpterwork.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.workdata,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adpterwork.ViewHolder holder, int position) {
        POJOwork obj = pojOworkList.get(position);
        holder.tvAddress.setText(obj.getAddress());
        holder.tvName.setText(obj.getFullname());
        holder.tvAdhr.setText(obj.getAdharNo());
        holder.tvMobile.setText(obj.getMobileNo());
        holder.tvDob.setText(obj.getDob());
        holder.tvJDate.setText(obj.getJoiningDate());
        holder.tvGender.setText(obj.getSelectedGender());
        holder.tvStatus.setText(obj.getSelectedStatus());
        holder.tvQualification.setText(obj.getQualification());

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.put("id",obj.getId());
                client.post(urls.removeworker,params,new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            String status = response.getString("status");
                            if (status.equals("success")){
                                pojOworkList.remove(holder.getAdapterPosition()); // Remove from list
                                notifyItemRemoved(holder.getAdapterPosition()); // Notify adapter
                                notifyItemRangeChanged(holder.getAdapterPosition(), pojOworkList.size()); // Refresh list
                                Toast.makeText(activity, "Worker Removed", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show();
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
        });

    }

    @Override
    public int getItemCount() {
        return pojOworkList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvMobile, tvAdhr, tvAddress, tvStatus, tvGender, tvQualification, tvDob, tvJDate;
        Button btnRemove;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvname);
            tvMobile = itemView.findViewById(R.id.tvmobile);
            tvAdhr = itemView.findViewById(R.id.tvadhr);
            tvAddress = itemView.findViewById(R.id.tvaddress);
            tvStatus = itemView.findViewById(R.id.tvstatus);
            tvGender = itemView.findViewById(R.id.tvgender);
            tvQualification = itemView.findViewById(R.id.tvqulification);
            tvDob = itemView.findViewById(R.id.tvdob);
            tvJDate = itemView.findViewById(R.id.tvjdate);
            btnRemove = itemView.findViewById(R.id.btnremove);
        }
    }
}
