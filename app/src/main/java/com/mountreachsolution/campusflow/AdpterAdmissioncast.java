package com.mountreachsolution.campusflow;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdpterAdmissioncast extends RecyclerView.Adapter<AdpterAdmissioncast.ViewHolder> {
    List<POJOAdmissioncast> pojoAdmissioncasts;
    Activity activity;

    public AdpterAdmissioncast(List<POJOAdmissioncast> pojoAdmissioncasts, Activity activity) {
        this.pojoAdmissioncasts = pojoAdmissioncasts;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdpterAdmissioncast.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.admissionlist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterAdmissioncast.ViewHolder holder, int position) {
        POJOAdmissioncast obj=pojoAdmissioncasts.get(position);
        holder.tvname.setText(obj.getName());
        holder.tvexam.setText(obj.getLastexamination());
        holder.tvpercentage.setText(obj.getLastexamppersentage());
        holder.tvfromno.setText(obj.getFromno());
        Glide.with(activity)
                .load(urls.address + "images/"+obj.getPassphoto())
                .skipMemoryCache(true)
                .error(R.drawable.baseline_person_24)// Resize the image to 800x800 pixels
                .into(holder.cvimage);
        holder.cvcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity,AdmissionDetails.class);
                i.putExtra("id",obj.getId());
                activity.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return pojoAdmissioncasts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cvcard;
        TextView tvname,tvexam,tvpercentage,tvfromno;
        CircleImageView cvimage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvcard=itemView.findViewById(R.id.cardx);
            tvname=itemView.findViewById(R.id.tvname);
            tvexam=itemView.findViewById(R.id.tvExam);
            tvpercentage=itemView.findViewById(R.id.tvPercentage);
            tvfromno=itemView.findViewById(R.id.tvFromno);
            cvimage=itemView.findViewById(R.id.cvimage);
        }
    }
}
