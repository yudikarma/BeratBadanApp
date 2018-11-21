package com.example.lina.beratbadanapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HasilActivity extends AppCompatActivity {
    TextView txtNama, txtJk, txtBb, txtTb, txtBmi, txtHasil, txtKet;
    String nama, jk, hasil, ket;
    double bb, tb, bmi;
    double defaultValue = 0;
    Button lihatriwayat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);
        txtJk = (TextView) findViewById(R.id.text_view_hasil_jk);
        txtNama = (TextView) findViewById(R.id.text_view_hasil_nama);
        txtBb = (TextView) findViewById(R.id.text_view_hasil_bb);
        txtTb = (TextView) findViewById(R.id.text_view_hasil_tb);
        txtBmi = (TextView) findViewById(R.id.text_view_hasil_bmi);
        txtHasil = (TextView) findViewById(R.id.text_view_hasil);
        txtKet = (TextView) findViewById(R.id.text_view_hasil_ket);
        lihatriwayat = (Button) findViewById(R.id.lihatstory);
        lihatriwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HasilActivity.this, LihatRiwayat.class);
                startActivity(intent);
            }
        });


       /* //mengambil variabel dari activity lain
        Intent intent = getIntent();
        //String pesan = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        nama = intent.getStringExtra("EXTRA_NAMA");
        jk = intent.getStringExtra("EXTRA_JK");
        bb = intent.getDoubleExtra("EXTRA_BB", defaultValue);
        tb = intent.getDoubleExtra("EXTRA_TB", defaultValue);
        bmi = intent.getDoubleExtra("EXTRA_BMI", defaultValue);
        hasil = intent.getStringExtra("EXTRA_HASIL");
        ket = intent.getStringExtra("EXTRA_KET");*/
        /*String tampung = intent.getStringExtra("fromfirebase");
         */

        Intent intent = getIntent();
        String id = intent.getStringExtra("id_riwayat");
        FirebaseDatabase.getInstance().getReference().child("BMI").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nama = dataSnapshot.child("nama").getValue().toString();
                jk = dataSnapshot.child("jeniskelamin").getValue().toString();
                String sbb = dataSnapshot.child("berat_badan").getValue().toString();
                String stb = dataSnapshot.child("tinggibadan").getValue().toString();
                String sbmi = dataSnapshot.child("bmi").getValue().toString();
                hasil = dataSnapshot.child("hasil").getValue().toString();
                ket = dataSnapshot.child("keterangan").getValue().toString();

                txtNama.setText("Nama : " + nama);
                txtJk.setText("Jenis Kelamin: " + jk);
                txtBb.setText("Berat Badan : " + sbb);
                txtTb.setText("Tinggi Badan : " + stb);
                txtBmi.setText("BMI : " + sbmi);
                txtHasil.setText("Hasil : " + hasil);
                txtKet.setText("Keterangan : " + ket);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

        /*txtNama.setText("Nama : " + nama);
        txtJk.setText("Jenis Kelamin: " + jk);
        txtBb.setText("Berat Badan : " + bb);
        txtTb.setText("Tinggi Badan : " + tb);
        txtBmi.setText("BMI : " + bmi);
        txtHasil.setText("Hasil : " + hasil);
        txtKet.setText("Keterangan : " + ket);*/



    public void bagikanKeEmail(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:")); // only email apps should handle this
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Laporan Berat Badan " + nama);
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Nama : " + nama + "\nBerat Badan : " + bb + "\nTinggi Badan : " + tb + "\nBMI : "
                + bmi + "\nHasil : " + hasil + "\nKeterangan : " + ket);
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        }
    }
}