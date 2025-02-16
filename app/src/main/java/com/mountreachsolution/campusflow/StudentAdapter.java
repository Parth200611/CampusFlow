package com.mountreachsolution.campusflow;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    List<POJOALLStudent> pojoallStudents;
    Activity activity;

    public StudentAdapter(List<POJOALLStudent> pojoallStudents, Activity activity) {
        this.pojoallStudents = pojoallStudents;
        this.activity = activity;
    }

    @NonNull
    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity).inflate(R.layout.all,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.ViewHolder holder, int position) {
        POJOALLStudent student = pojoallStudents.get(position);

        holder.tvName.setText(student.getName());

        holder.tvContact.setText(student.getSem());
        holder.tvBranch.setText(student.getBarnch());
        holder.tvParents.setText(student.getEnroll());

    }

    @Override
    public int getItemCount() {
        return pojoallStudents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvRoomNO, tvContact, tvBranch, tvParents;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvContact = itemView.findViewById(R.id.tvContact);
            tvBranch = itemView.findViewById(R.id.tvBranch);
            tvParents = itemView.findViewById(R.id.tvParents);
        }
    }
}
