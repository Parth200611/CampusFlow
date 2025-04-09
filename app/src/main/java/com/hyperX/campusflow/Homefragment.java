package com.hyperX.campusflow;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Homefragment extends Fragment {
    CardView cvopen,cvobc,cvsc,cvst,cvsbc,cvvj,cvntb,cvntc,cvntd;
    String cast;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_homefragment, container, false);
        cvopen=view.findViewById(R.id.cvopen);
        cvobc=view.findViewById(R.id.cvobc);
        cvsc=view.findViewById(R.id.cvSC);
        cvst=view.findViewById(R.id.cvST);
        cvsbc=view.findViewById(R.id.cvSBC);
        cvvj=view.findViewById(R.id.cvVJ);
        cvntb=view.findViewById(R.id.cvNTB);
        cvntc=view.findViewById(R.id.cvNTC);
        cvntd=view.findViewById(R.id.cvNTD);



        cvopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cast="OPEN";
                MovetoNext(cast);
            }
        });
        cvobc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cast="OBC";
                MovetoNext(cast);
            }
        });

        cvsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cast="SC";
                MovetoNext(cast);
            }
        });
         cvst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cast="ST";
                MovetoNext(cast);
            }
        });

        cvsbc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cast="SBC";
                MovetoNext(cast);
            }
        });

        cvvj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cast="VJ";
                MovetoNext(cast);
            }
        });

        cvntb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cast="NT-B";
                MovetoNext(cast);
            }
        });cvntc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cast="NT-C";
                MovetoNext(cast);
            }
        });cvntd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cast="NT-D";
                MovetoNext(cast);
            }
        });


        return view;
    }

    private void MovetoNext(String cast) {
        Intent i = new Intent(getActivity(),Admissionlist.class);
        i.putExtra("cast",cast);
        startActivity(i);
    }
}