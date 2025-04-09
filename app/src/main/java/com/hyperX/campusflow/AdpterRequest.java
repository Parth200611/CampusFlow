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

public class AdpterRequest extends RecyclerView.Adapter<AdpterRequest.ViewHolder> {
    List<POJORequest> pojoRequests;
    Activity activity;
    String id;

    public AdpterRequest(List<POJORequest> pojoRequests, Activity activity) {
        this.pojoRequests = pojoRequests;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdpterRequest.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.leaverequest,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterRequest.ViewHolder holder, int position) {
        POJORequest obj=pojoRequests.get(position);
        holder.tvName.setText(obj.getName());
        holder.tvBranch.setText(obj.getBranch());
        holder.tvSem.setText(obj.getSem());
        holder.tvEnroll.setText(obj.getEnroll());
        holder.tvStart.setText(obj.getStrt());
        holder.tvEnd.setText(obj.getEnd());
        holder.tvTitle.setText(obj.getTitle());
        holder.tvDescription.setText(obj.getDis());

         id=obj.getId();

        holder.btnaccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String accept="Accept";
               String name=obj.getName();
               String branch=obj.getBranch();
               String sem=obj.getSem();
               String enroll=obj.getEnroll();
               String start=obj.getStrt();
               String end=obj.getEnd();
               String title=obj.getTitle();
               String dis=obj.getDis();
               PostData(name,branch,sem,enroll,start,end,title,dis,accept);

            }
        });  holder.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String accept="Reject";
               String name=obj.getName();
               String branch=obj.getBranch();
               String sem=obj.getSem();
               String enroll=obj.getEnroll();
               String start=obj.getStrt();
               String end=obj.getEnd();
               String title=obj.getTitle();
               String dis=obj.getDis();
               PostDat(name,branch,sem,enroll,start,end,title,dis,accept);

            }
        });

    }

    private void PostData(String name, String branch, String sem, String enroll, String start, String end, String title, String dis, String accept) {
        AsyncHttpClient client =  new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("name",name);
        params.put("branch",branch);
        params.put("sem",sem);
        params.put("enroll",enroll);
        params.put("sdate",start);
        params.put("edate",end);
        params.put("title",title);
        params.put("dis",dis);
        params.put("status",accept);

        client.post(urls.Acceptrequest,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status=response.getString("success");
                    if (status.equals("1")){
                        Toast.makeText(activity,"Accpet Request",Toast.LENGTH_SHORT).show();
                      postdatahello(id);

                    }else {
                        Toast.makeText(activity, "Fail to accept", Toast.LENGTH_SHORT).show();
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
    } private void PostDat(String name, String branch, String sem, String enroll, String start, String end, String title, String dis, String accept) {
        AsyncHttpClient client =  new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("name",name);
        params.put("branch",branch);
        params.put("sem",sem);
        params.put("enroll",enroll);
        params.put("sdate",start);
        params.put("edate",end);
        params.put("title",title);
        params.put("dis",dis);
        params.put("status",accept);

        client.post(urls.Acceptrequest,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status=response.getString("success");
                    if (status.equals("1")){
                        Toast.makeText(activity,"Reject Request",Toast.LENGTH_SHORT).show();
                      postdatahello(id);
                    }else {
                        Toast.makeText(activity, "Fail to accept", Toast.LENGTH_SHORT).show();
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

    private void postdatahello(String id) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("id",id);
        client.post(urls.remove,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String stuta=response.getString("status");
                    if (stuta.equals("success")){
                        Toast.makeText(activity, "", Toast.LENGTH_SHORT).show();
                        refreshActivity();  // Restart the activity
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

    private void refreshActivity() {
        activity.finish();  // Close current activity
        activity.startActivity(activity.getIntent());  // Restart it
    }


    private void postdata(String accept, String id) {

    }

    @Override
    public int getItemCount() {
        return pojoRequests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvBranch, tvSem, tvEnroll, tvStart, tvEnd, tvTitle, tvDescription;
        Button btnaccept,btnReject;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvname);
            tvBranch = itemView.findViewById(R.id.tvbranch);
            tvSem = itemView.findViewById(R.id.tvsem);
            tvEnroll = itemView.findViewById(R.id.tvenroll);
            tvStart = itemView.findViewById(R.id.tvstart);
            tvEnd = itemView.findViewById(R.id.tvend);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvdiscription);
            btnaccept = itemView.findViewById(R.id.btnaccept);
            btnReject = itemView.findViewById(R.id.btnreject);
        }
    }
}
