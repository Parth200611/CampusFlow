package com.mountreachsolution.campusflow;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdpterRequest extends RecyclerView.Adapter<AdpterRequest.ViewHolder> {
    List<POJORequest> pojoRequests;
    Activity activity;

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

    }

    @Override
    public int getItemCount() {
        return pojoRequests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvName, tvBranch, tvSem, tvEnroll, tvStart, tvEnd, tvTitle, tvDescription;
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
        }
    }
}
