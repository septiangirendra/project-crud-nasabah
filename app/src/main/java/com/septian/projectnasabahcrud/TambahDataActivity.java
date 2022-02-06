package com.septian.projectnasabahcrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroupOverlay;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class TambahDataActivity extends AppCompatActivity implements AdapterView.OnClickListener {
    //Dibawah ini merupakan perintah untuk mendefinikan View
    private EditText editTextName, editTextRek, editTextTab, editTextSal;

    private Button buttonAdd;
    private Button buttonView;
    ActionBar setting_actionbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data);

        //Inisialisasi dari View
        editTextName = findViewById(R.id.editTextName);
        editTextRek = findViewById(R.id.editTextRek);
        editTextTab = findViewById(R.id.editTextTab);
        editTextSal = findViewById(R.id.editTextSal);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonAdd =  findViewById(R.id.buttonAdd);
        buttonView = findViewById(R.id.buttonView);

        setting_actionbar = getSupportActionBar();
        setting_actionbar.setTitle("TAMBAH NASABAH");

        //Setting listeners to button
        buttonAdd.setOnClickListener(this);
        buttonView.setOnClickListener(this);
    }

    //Dibawah ini merupakan perintah untuk Menambahkan Pegawai (CREATE)
    private void addEmployee(){
        final String nama = editTextName.getText().toString().trim();
        final String no_rekening = editTextRek.getText().toString().trim();
        final String tabungan = editTextTab.getText().toString().trim();
        final String saldo = editTextSal.getText().toString().trim();

        class AddEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TambahDataActivity.this,"Menambahkan...",
                        "Tunggu...",false,false);
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_NSB_NAMA,nama);
                params.put(Konfigurasi.KEY_NSB_REK,no_rekening);
                params.put(Konfigurasi.KEY_NSB_TAB,tabungan);
                params.put(Konfigurasi.KEY_NSB_SAL, saldo);

                HttpHandler httpHandler = new HttpHandler();
                String res = httpHandler.sendPostRequest(Konfigurasi.URL_GET_ADD, params);
                return res;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TambahDataActivity.this,s,Toast.LENGTH_LONG).show();
            }

        }

        AddEmployee ae = new AddEmployee();
        ae.execute();
    }


    @Override
    public void onClick(View view) {
        if (view == buttonAdd){
            addEmployee();
        }

        if (view == buttonView){
            startActivity(new Intent(this,LihatDataActivity.class));
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }
}