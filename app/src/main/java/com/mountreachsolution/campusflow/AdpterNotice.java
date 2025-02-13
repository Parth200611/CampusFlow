package com.mountreachsolution.campusflow;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AdpterNotice extends RecyclerView.Adapter<AdpterNotice.ViewHolder> {
    List<POJOnotice>pojOnotices;
    Activity activity;

    public AdpterNotice(List<POJOnotice> pojOnotices, Activity activity) {
        this.pojOnotices = pojOnotices;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdpterNotice.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.notice,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterNotice.ViewHolder holder, int position) {
        POJOnotice obj=pojOnotices.get(position);
        holder.tvDate.setText(obj.getDate());
        holder.tvTime.setText(obj.getTime());
        holder.tvTitle.setText(obj.getTitle());
        holder.tvDescription.setText(obj.getDis());
        Glide.with(activity)
                .load(urls.address + "images/"+obj.getImage())
                .skipMemoryCache(true)
                .error(R.drawable.baseline_person_24)// Resize the image to 800x800 pixels
                .into(holder.ivNoticeImage);

    }

    @Override
    public int getItemCount() {
        return pojOnotices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivNoticeImage;
        TextView tvTitle, tvDescription, tvDate, tvTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivNoticeImage = itemView.findViewById(R.id.ivNoticeImage);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
}
