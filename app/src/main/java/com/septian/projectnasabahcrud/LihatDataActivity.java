package com.septian.projectnasabahcrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LihatDataActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener {
    private ListView list_view;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data);

        getSupportActionBar().setTitle("Data Nasabah");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //penanganan list view
        list_view = findViewById(R.id.list_view);
        list_view.setOnItemClickListener(this);

        //method untuk ambil data JSON
        getJSON();
    }

    //menampilkan options menu pada toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.navigation_item, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }

    private void getJSON() {
        //bantuan dari class AsynTask
        class GetJSON extends AsyncTask<Void, Void, String> { //inner class
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LihatDataActivity.this,
                        "Mengambil Data", "Harap menunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    Thread.sleep(10000);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_GET_ALL);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                JSON_STRING = message;
                Log.d("DATA_JSON", JSON_STRING);

                //menampilkan data dalam bentuk list view
                displayAllData();
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayAllData() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JASON_ARRAY);
            Log.d("DATA_JSON", JSON_STRING);

            for (int i = 0; i < result.length(); i++) {
                JSONObject object = result.getJSONObject(i);
                String id = object.getString(Konfigurasi.TAG_JSON_ID);
                String name = object.getString(Konfigurasi.TAG_JSON_NAMA);
                String no_rekening = object.getString(Konfigurasi.TAG_JSON_REK);

                HashMap<String, String> nasabah = new HashMap<>();
                nasabah.put(Konfigurasi.TAG_JSON_ID, id);
                nasabah.put(Konfigurasi.TAG_JSON_NAMA, name);
                nasabah.put(Konfigurasi.TAG_JSON_REK, no_rekening);

                //ubah format json menjadi array list
                list.add(nasabah);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // adapter untuk meletakan array list kedalam list view
        ListAdapter adapter = new SimpleAdapter(
                getApplicationContext(), list,
                R.layout.activity_list_item,
                new String[]{Konfigurasi.TAG_JSON_ID, Konfigurasi.TAG_JSON_NAMA, Konfigurasi.TAG_JSON_REK},
                new int[]{R.id.txt_id, R.id.txt_name, R.id.txt_norek}
        );
        list_view.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // ketika salah satu list dipilih
       Intent myIntent = new Intent(LihatDataActivity.this,LihatDetailDataActivity.class);
       HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
       String nsbhId = map.get(Konfigurasi.TAG_JSON_ID).toString();
       myIntent.putExtra(Konfigurasi.NSB_ID, nsbhId);
       startActivity(myIntent);
    }


}