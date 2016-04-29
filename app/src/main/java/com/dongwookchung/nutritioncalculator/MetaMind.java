package com.dongwookchung.nutritioncalculator;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MetaMind extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        URL url;
        String answer;
        Drawable drawable = Drawable.createFromPath(params[0]);
        Bitmap bm = ((BitmapDrawable)drawable).getBitmap();

        try {
            url = new URL("https://www.metamind.io/vision/classify");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Basic vlkUZWREAG05i9ld0hYUEvWW41sUwnS9C53M8VdhEplr6q3ODi");
            conn.setRequestMethod("POST");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 50, stream);
            byte[] arr = stream.toByteArray();
            String base64 = android.util.Base64.encodeToString(arr, android.util.Base64.DEFAULT);
            base64 = base64.replace("\n", "");
            base64 = "data:image/jpeg;base64,"+base64;

            String data = "{\"classifier_id\":\"food-net\", \"image_url\":\""+base64+"\"}";
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            out.write(data);
            out.flush();
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            JSONObject json = new JSONObject(sb.toString());
            JSONArray jsonArray = new JSONArray(json.getString("predictions"));

            for (int i = 0; i < jsonArray.length(); i++) {
                System.out.println("MetaMind: "+jsonArray.getJSONObject(i).getString("class_name"));
            }

            JSONObject match = jsonArray.getJSONObject(0);
            answer = match.getString("class_name");
            return answer;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
