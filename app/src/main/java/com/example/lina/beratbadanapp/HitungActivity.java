package com.example.lina.beratbadanapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import android.view.View.OnClickListener;

import com.example.lina.beratbadanapp.model.BMImodel;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class HitungActivity extends AppCompatActivity {
    EditText edtNama, edtBB, edtTB;
    RadioButton radioButtonL;
    RadioButton radioButtonP;
    String jeniskelamin;
    String hasil;
    String ket;
    String ideal;
    protected Cursor cursor;
    private DatabaseReference mRootDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitung);
        getSupportActionBar().setTitle("Hitung BMI Ideal");
        edtNama = (EditText)findViewById(R.id.edit_text_nama);
        edtBB = (EditText)findViewById(R.id.edit_text_bb);
        edtTB = (EditText)findViewById(R.id.edit_text_tb);
        radioButtonL = (RadioButton)findViewById(R.id.radio_btn_lk);
        radioButtonP=(RadioButton)findViewById(R.id.radio_btn_p);

        mRootDatabaseReference = FirebaseDatabase.getInstance().getReference();

    }

    public void cekHasil(View view) {
        final String nama = edtNama.getText().toString().trim();
        String sBeratBadan = edtBB.getText().toString().trim();
        String sTinggiBadan = edtTB.getText().toString().trim();
        final String hasil;
        final String ket;
        String ideal;


        if(edtNama.getText().toString().equals("")||edtBB.getText().toString().equals("")||edtTB.getText().toString().equals("")||(!radioButtonP.isChecked()&&!radioButtonL.isChecked())){
            Toast.makeText(getApplicationContext(),"Mohon untuk melengkapi data",Toast.LENGTH_SHORT).show();
        }
        else{
            final double beratBadan = Double.parseDouble(sBeratBadan);
            final double tinggiBadan = Double.parseDouble(sTinggiBadan);

            //rumus
            //BMI = Berat Badan kg/ (Tinggi Badan m * Tinggi Badan m)
            final double bmi = beratBadan/(tinggiBadan*tinggiBadan*0.0001);

            Log.d("tag","Nama = "+nama+"\nbmi = "+bmi+"\n jenis kelamin : "+jeniskelamin);
            if(jeniskelamin.equals("Perempuan")){
                if (bmi<18){
                    //Log.d("keterangan Perempuan", "Under Weight/Kurus – Sebaiknya mulai menambah berat badan dan mengkonsumsi makanan berkarbohidrat di imbangi dengan olah raga");
                    hasil="Under Weight/Kurus";
                    ket = "Sebaiknya mulai menambah berat badan dengan menkonsumsi makanan berkarbohidrat di imbangi dengan olah raga";


            }
                else if(bmi>=18&&bmi<=25){
                    //Log.d("keterangan Perempuan","Normal Weight/Normal – Bagus, berat badan anda termasuk kategori ideal");
                    hasil="Normal Weight/Normal";
                    ket = "Bagus, berat badan anda termasuk kategori ideal";
                }
                else if (bmi>25&&bmi<=27){
                    //Log.d("keterangan Perempuan","Over Weight/Kegemukan – anda sudah masuk kategori gemuk. sebaiknya hindari makanan berlemak dan mulailah meningkatkan olahraga seminggu minimal 2 kali");
                    hasil = "Over Weight/Kegemukan";
                    ket = "Anda sudah masuk kategori gemuk. sebaiknya hindari makanan berlemak dan mulailah meningkatkan olahraga seminggu minimal 2 kali";

                }
                else{
                    //Log.d("keterangan Perempuan","\tObesitas – Sebaiknya segera membuat program menurunkan berat badan karena anda termasuk kategori obesitas/ terlalu gemuk dan tidak baik bagi kesehatan");
                    hasil="Obesitas";
                    ket = "Sebaiknya segera membuat program menurunkan berat badan karena anda termasuk kategori obesitas/ terlalu gemuk dan tidak baik bagi kesehatan";

                }
            }
            else{
                if (bmi<17){
                    //Log.d("keterangan Pria", "Under Weight/Kurus – Tambah konsumsi makanan berkalori");
                    hasil="Under Weight/Kurus";
                    ket="Tambah konsumsi makanan berkalori";
                }
                else if(bmi>=17&&bmi<=23){
                    //Log.d("keterangan Pria","Normal Weight/Normal – Selamat berat badan anda termasuk ideal");
                    hasil="Normal Weight/Normal";
                    ket="Selamat berat badan anda termasuk ideal";
                }
                else if (bmi>23&&bmi<=27){
                    //Log.d("keterangan Pria","\tOver Weight/Kegemukan – Harus waspada");
                    hasil="Over Weight/Kegemukan";
                    ket = "Harus waspada";
                }
                else{
                    Log.d("keterangan Pria","Obesitas – Warning, sebaiknya memulai program menurunan berat badan agar lebih ideal.");
                    hasil="Obesitas – Warning";
                    ket ="Sebaiknya memulai program menurunan berat badan agar lebih ideal.";
                }
            }
            //convert
            String sberatbadan = String.valueOf(beratBadan);

            String stinggibadan = String.valueOf(tinggiBadan);

            String sbmi = String.valueOf(bmi);
            BMImodel bmImodel = new BMImodel();
            bmImodel.setNama(nama);
            bmImodel.setJeniskelamin(jeniskelamin);
            bmImodel.setBerat_badan(sberatbadan);
            bmImodel.setTinggibadan(stinggibadan);
            bmImodel.setBmi(sbmi);
            bmImodel.setHasil(hasil);
            bmImodel.setKeterangan(ket);

            DatabaseReference newNotificationRef = mRootDatabaseReference.child("BMI").push();
            final String id = newNotificationRef.getKey();
            HashMap<String, String> mapdata = new HashMap<>();
            mapdata.put("nama", ""+nama);
            mapdata.put("berat_badan",""+sberatbadan);
            mapdata.put("tinggibadan",""+stinggibadan);
            mapdata.put("jeniskelamin",""+jeniskelamin);
            mapdata.put("bmi",""+sbmi);
            mapdata.put("hasil",""+hasil);
            mapdata.put("keterangan",""+ket);

            Map simpanrekam = new HashMap();
            simpanrekam.put("BMI/"+id,mapdata);

            mRootDatabaseReference.updateChildren(simpanrekam, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError == null){
                        Intent intent = new Intent(HitungActivity.this,HasilActivity.class);
                       /* intent.putExtra("EXTRA_NAMA", nama);
                        intent.putExtra("EXTRA_BB",beratBadan);
                        intent.putExtra("EXTRA_TB",tinggiBadan);
                        intent.putExtra("EXTRA_JK",jeniskelamin);
                        intent.putExtra("EXTRA_BMI",bmi);
                        intent.putExtra("EXTRA_HASIL",hasil);
                        intent.putExtra("EXTRA_KET",ket);*/

                        intent.putExtra("id_riwayat",id);
                        startActivity(intent);


                        Log.d("jeniskelamin",jeniskelamin);

                    }
                    else {
                        String error = databaseError.getMessage();
                        Toast.makeText(HitungActivity.this, ""+error, Toast.LENGTH_LONG).show();
                    }
                }
            });





        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_btn_lk:
                if (checked)
                    jeniskelamin = radioButtonL.getText().toString().trim();
                break;
            case R.id.radio_btn_p:
                if (checked)
                    // Ninjas rule
                    jeniskelamin = radioButtonP.getText().toString().trim();
                break;
        }
    }

}