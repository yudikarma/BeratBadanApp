package com.example.lina.beratbadanapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lina.beratbadanapp.model.BMImodel;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class LihatRiwayat extends AppCompatActivity {
    private BMImodel bmImodel;
    private ArrayList<BMImodel> arrayAdapter;
    private RecyclerView recyclerView;

    private DatabaseReference mDatabaseReference;
    //private FirebaseListAdapter adapter;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseRecyclerAdapter<BMImodel, ListDaftarHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_riwayat);
        /*databaseHelper = new DatabaseHelper(this);*/





        recyclerView = findViewById(R.id.listriwayat);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        Query query = FirebaseDatabase.getInstance().getReference().child("BMI").limitToLast(50);

        FirebaseRecyclerOptions<BMImodel> options  = new FirebaseRecyclerOptions.Builder<BMImodel>()
                .setQuery(query, BMImodel.class)
                .setLifecycleOwner(this)
                .build();

        adapter = new FirebaseRecyclerAdapter<BMImodel, ListDaftarHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ListDaftarHolder holder, int position, @NonNull BMImodel model) {
                final String getIdRiwayat = getRef(position).getKey();
                holder.setNama(model.getNama());
                holder.setBerat_badan(model.getBerat_badan());
                holder.setTinggibadan(model.getTinggibadan());
                holder.setNilai_BMI(model.getBmi());
                holder.setHasil(model.getHasil());

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LihatRiwayat.this,HasilActivity.class);
                        intent.putExtra("id_riwayat", getIdRiwayat);
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public ListDaftarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holderriwayat, parent,false);
                return new ListDaftarHolder(view);
            }
        };


        recyclerView.setAdapter(adapter);
    }

    public static class ListDaftarHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView nama,berat_badan,tinggibadan,hasil,nilai_BMI;
        ImageView url;
        public ListDaftarHolder(View itemView) {
            super(itemView);
            mView = itemView;

            //isnisialisasi
            nama = mView.findViewById(R.id.nama);
            berat_badan = mView.findViewById(R.id.bb);
            tinggibadan = mView.findViewById(R.id.tb);
            nilai_BMI = mView.findViewById(R.id.BMI);
            hasil = mView.findViewById(R.id.hasil);



        }

        public void setNama(String snama) {
            nama.setText(snama);
        }
        public void setBerat_badan(String snama) {
            berat_badan.setText(snama);
        }
        public void setTinggibadan(String snama) {
            tinggibadan.setText(snama);
        }

        public void setNilai_BMI(String snilai_BMI) {
            nilai_BMI.setText(snilai_BMI);
        }

        public void setHasil(String shasil) {
            hasil.setText(shasil);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
