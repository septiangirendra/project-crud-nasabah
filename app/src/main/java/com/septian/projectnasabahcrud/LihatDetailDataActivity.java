package com.septian.projectnasabahcrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LihatDetailDataActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edit_id, edit_nama, edit_norek, edit_tabungan, edit_saldo;
    private String id;
    private Button btn_update, btn_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_detail_data);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Data Pegawai");

        edit_id = findViewById(R.id.edit_id);
        edit_nama = findViewById(R.id.edit_nama);
        edit_norek = findViewById(R.id.edit_norek);
        edit_tabungan = findViewById(R.id.edit_tabungan);
        edit_saldo = findViewById(R.id.edit_saldo);
        btn_delete = findViewById(R.id.btn_delete);
        btn_update = findViewById(R.id.btn_update);

        btn_update.setOnClickListener(this);
        btn_delete.setOnClickListener(this);


        // menerima inten dari class LihatDataActivity
        Intent receiveIntent = getIntent();
        id = receiveIntent.getStringExtra(Konfigurasi.NSB_ID);
        edit_id.setText(id);

        // mengambil data JSON
        getJSON();
    }

    private void getJSON() {
        // MENGAMBIL DATA DARI ANDROID KE SERVER
        // BANTUAN DARI CLASS ASYNCtASK
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            // ctrl + o pilih OnPreExcetue
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LihatDetailDataActivity.this,
                        "Mengambil Data", "Harap Menunggu",
                        false, false);
            }

            // Saat proses ambil data terjadi
            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_GET_DETAIL, id);
                return result;
            }

            // ctrl + o pilih OnPostExcetue
            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                displayDetailData(message);

            }
        }

        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayDetailData(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JASON_ARRAY);
            JSONObject object = result.getJSONObject(0);

            String nama = object.getString(Konfigurasi.TAG_JSON_NAMA);
            String no_rekening = object.getString(Konfigurasi.TAG_JSON_REK);
            String tabungan = object.getString(Konfigurasi.TAG_JSON_TAB);
            String saldo = object.getString(Konfigurasi.TAG_JSON_SAL);

            edit_nama.setText(nama);
            edit_norek.setText(no_rekening);
            edit_tabungan.setText(tabungan);
            edit_saldo.setText(saldo);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }


    private void updateEmployee() {
        final String nama = edit_nama.getText().toString().trim();
        final String no_rekening = edit_norek.getText().toString().trim();
        final String tabungan = edit_tabungan.getText().toString().trim();
        final String saldo = edit_saldo.getText().toString().trim();

        class UpdateEmployee extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LihatDetailDataActivity.this,
                        "Updating...", "Wait...", false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(Konfigurasi.KEY_NSB_ID, id);
                hashMap.put(Konfigurasi.KEY_NSB_NAMA, nama);
                hashMap.put(Konfigurasi.KEY_NSB_REK, no_rekening);
                hashMap.put(Konfigurasi.KEY_NSB_TAB, tabungan);
                hashMap.put(Konfigurasi.KEY_NSB_SAL, saldo);


                HttpHandler handler = new HttpHandler();

                String s = handler.sendPostRequest(Konfigurasi.URL_UPDATE, hashMap);
                return s;

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                startActivity(new Intent(LihatDetailDataActivity.this,
                        MainActivity.class));
            }
        }

        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();
    }

    private void deleteEmployee() {
        class DeleteEmployee extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LihatDetailDataActivity.this,
                        "Updating...", "Tunggu...", false, false);

            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String s = handler.sendGetRequestParam(Konfigurasi.URL_DELETE, id);
                return s;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(LihatDetailDataActivity.this, "Hapus",
                        Toast.LENGTH_SHORT).show();
            }
        }

        DeleteEmployee de = new DeleteEmployee();
        de.execute();
    }

    private void confirmDeleteEmployee() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Yakin Ingin Menghapus Nasabah ini?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteEmployee();
                        startActivity(new Intent(LihatDetailDataActivity.this, MainActivity.class));
                    }
                });

        alertDialogBuilder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View view) {
        if (view == btn_update) {
            updateEmployee();
        }

        if (view == btn_delete) {
            confirmDeleteEmployee();
        }
    }

    //    ctrl + 0
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }


}