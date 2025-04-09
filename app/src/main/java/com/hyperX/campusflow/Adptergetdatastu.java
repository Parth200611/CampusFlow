package com.hyperX.campusflow;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adptergetdatastu extends RecyclerView.Adapter<Adptergetdatastu.Viewholder> {
    List<POJOGetDta> pojoGetDtas;
    Activity activity;

    public Adptergetdatastu(List<POJOGetDta> pojoGetDtas, Activity activity) {
        this.pojoGetDtas = pojoGetDtas;
        this.activity = activity;
    }

    @NonNull
    @Override
    public Adptergetdatastu.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.accepetrequest,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adptergetdatastu.Viewholder holder, int position) {
        POJOGetDta obj=pojoGetDtas.get(position);
        holder.tvName.setText(obj.getName());
        holder.tvBranch.setText(obj.getBranch());
        holder.tvSem.setText(obj.getSem());
        holder.tvEnroll.setText(obj.getEnroll());
        holder.tvStart.setText(obj.getStrt());
        holder.tvEnd.setText(obj.getEnd());
        holder.tvTitle.setText(obj.getTitle());
        holder.tvDescription.setText(obj.getDis());
        holder.tvstatus.setText(obj.getStatus());



    }

    @Override
    public int getItemCount() {
        return pojoGetDtas.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView tvName, tvBranch, tvSem, tvEnroll, tvStart, tvEnd, tvTitle, tvDescription,tvstatus;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvname);
            tvBranch = itemView.findViewById(R.id.tvbranch);
            tvSem = itemView.findViewById(R.id.tvsem);
            tvEnroll = itemView.findViewById(R.id.tvenroll);
            tvStart = itemView.findViewById(R.id.tvstart);
            tvEnd = itemView.findViewById(R.id.tvend);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvdiscription);
            tvstatus = itemView.findViewById(R.id.tvstatus);
        }
    }
}
