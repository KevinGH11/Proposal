package com.example.smartgardening;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class listview extends AppCompatActivity {

    ArrayList<String> land_id = new ArrayList<>();
    ArrayList<String> currentSoilMoisture = new ArrayList<>();
    ArrayList<String> currentLightIntensity = new ArrayList<>();
    int landspace;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);


        Bundle bundle = getIntent().getExtras();
        landspace = bundle.getInt("landspace");

        new fetchData().execute();

    }

/*Acquire data from database*/
    public class fetchData extends AsyncTask<Void,Void,Void> {
        String data = "";

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                GlobalClass globalClass = (GlobalClass) getApplicationContext();
                URL url = new URL(globalClass.getLand());

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while (line != null) {
                    line = bufferedReader.readLine();
                    data = data + line;
                    Log.d("DATA_raw", data);
                }

                JSONArray JA = new JSONArray(data);
                JSONObject JO = null;
                for (int i = 0; i < JA.length(); i++) {
                    JO = (JSONObject) JA.get(i);
                    land_id.add(JO.getString("LandID"));
                    currentSoilMoisture.add(JO.getString("CurrentSoilMoisturelvl"));
                    currentLightIntensity.add(JO.getString("CurrentLightIntensitylvl"));
                    Log.d("DATA_user", "id:" + land_id.get(i));
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

/*Save data into array in GlobalClass*/
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArrayAdapter adapter = new ArrayAdapter(listview.this,android.R.layout.simple_list_item_1, land_id);

            GlobalClass globalClass = (GlobalClass) getApplicationContext();
            ListView listView = findViewById(R.id.listview);
            listView.setAdapter(adapter);

            globalClass.setCurrentSoilMoisture(currentSoilMoisture);
            globalClass.setCurrentLightIntensity(currentLightIntensity);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String selectedItem = (String) parent.getItemAtPosition(position);

                        Intent intent = new Intent(listview.this, Afterlogin.class);
                        intent.putExtra("landID", selectedItem);

                        if(landspace==0){
                            globalClass.setLandspace1Name(selectedItem);
                        }
                        if(landspace==1){
                            globalClass.setLandspace2Name(selectedItem);
                        }
                        startActivity(intent);
                    }
            });
        }
    }
}
