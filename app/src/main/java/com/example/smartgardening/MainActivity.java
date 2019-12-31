package com.example.smartgardening;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText Username;
    private EditText Password;
    private Button Login;
    private TextView LoginStatus;

    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> userName = new ArrayList<>();
    ArrayList<String> password = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Username = (EditText)findViewById(R.id.etUsername);
        Password = (EditText)findViewById(R.id.etPassword);
        Login = (Button)findViewById(R.id.btnLogin);
        LoginStatus = (TextView)findViewById(R.id.info);

        new fetchData().execute();

/*user login*/
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(Username.getText().toString(),Password.getText().toString());

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoginStatus.setText("Login Failed");
                        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(LoginStatus,"alpha", 1f, 0);
                        fadeOut.setDuration(3000);
                        fadeOut.start();
                        Animation animation= AnimationUtils.loadAnimation(MainActivity.this,R.anim.bounce);
                        LoginStatus.startAnimation(animation);
                    }
                }, 1000);

            }
        });


    }

    /*validate user information*/
    private void validate(String inp_userName, String inp_userPassw){

        for (int i = 0; i<6 ; i++) {

            if((inp_userName.equals(userName.get(i)))&&(inp_userPassw.equals(password.get(i)))) {
                Intent intent = new Intent(MainActivity.this, Afterlogin.class);
                intent.putExtra("username", userName.get(i));
                GlobalClass globalClass = (GlobalClass) getApplicationContext();
                globalClass.setApparentUser(inp_userName);
                startActivity(intent);
                }
            }
    }

    /*Acquire user data from database*/
    public class fetchData extends AsyncTask<Void,Void,Void> {
        String data = "";
        String data1 = "";

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("http://50efd1ec.ngrok.io/api/smartGarden/?fbclid=IwAR0jQxbSEmkyamtAhXp2beRSeV7IvQ_d9JTxx6JsELJgqRn-agvGvZPVN_g");

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
            GlobalClass globalClass = (GlobalClass) getApplicationContext();
            JSONObject JO = new JSONObject(data);
            for (int i = 0; i < JO.length(); i++) {
                globalClass.setUser(JO.getString("User"));
                globalClass.setLand(JO.getString("Land"));
                globalClass.setPlant(JO.getString("Plant"));
                Log.d("DATA_link", "data:" + JO.getString("User"));
            }
            httpURLConnection.disconnect();

            URL url1 = new URL(globalClass.getUser());
            HttpURLConnection httpURLConnection1 = (HttpURLConnection) url1.openConnection();
            httpURLConnection1.setReadTimeout(10000);
            httpURLConnection1.setConnectTimeout(15000);
            httpURLConnection1.setDoInput(true);
            httpURLConnection1.setDoOutput(true);
            httpURLConnection1.setRequestMethod("POST");

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("userName", "QiuHui0324")
                    .appendQueryParameter("password", "12345678");
            String query = builder.build().getEncodedQuery();
            Log.d("DATA_post", "conn:" + query);

            OutputStream outputStream = httpURLConnection1.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            outputStream.close();
            httpURLConnection1.disconnect();
            URL url2 = new URL(globalClass.getUser());
            url2.openConnection();
            HttpURLConnection httpURLConnection2 = (HttpURLConnection) url2.openConnection();
            InputStream in = new BufferedInputStream(httpURLConnection2.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line1 = "";
            while (line1 != null) {
                line1 = reader.readLine();
                data1 = data1 + line1;
                Log.d("DATA_raw", data1);
            }

            JSONArray JA1 = new JSONArray(data1);
            JSONObject JO1 = null;
            for (int i = 0; i < JA1.length(); i++) {
                JO1 = (JSONObject) JA1.get(i);
                id.add(JO1.getString("id"));
                userName.add(JO1.getString("userName"));
                password.add(JO1.getString("password"));
                Log.d("DATA_user", "id:" + id.get(i));
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

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        }
    }
}


