package com.dongwookchung.calcal;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Analyze extends Activity {
    static String food;
    static String result = "";
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analyze);

        Bundle word = getIntent().getExtras();
        if (word == null) {
            return;
        }
        food = word.getString("foodname");

        new MyTask().execute();
        new NewTask().execute();
    }

    private class MyTask extends AsyncTask<Void, Void, String> {
        TextView text = (TextView)findViewById(R.id.textView);

        @Override
        protected String doInBackground(Void... params) {
            URL url;
            try {
                url = new URL("http://api.nal.usda.gov/ndb/search/?format=json&q="+food+"&sort=r&max=5&fg=2100&offset=0&api_key=ugCICsXbOSco8eob3iYd9Dmr6UPL9xcXvw5hTQCk");
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.connect();
                String line;
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                JSONObject json = new JSONObject(sb.toString());
                JSONObject jsonOne = new JSONObject(json.getString("list"));

                result = jsonOne.toString();

                return result;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            text.setText("Match Result: "+result);
        }
    }
    
    private class NewTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            FatSecretAPI api = new FatSecretAPI("b71932ec433c41a291fcc60628519a7a", "e9a355cec6b64a09a6b4875c9693bd45");
            JSONObject foods;
            try {
                foods = api.getFoodItems("Burgers");
                System.out.println(foods);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}