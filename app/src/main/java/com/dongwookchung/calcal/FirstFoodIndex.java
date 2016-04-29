package com.dongwookchung.calcal;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

public class FirstFoodIndex extends AsyncTask<String, Void, Integer> {
    //aufstand363@gmail.com
    private FatSecretAPI api = new FatSecretAPI("0c8526a948584cea8671a1976827dcff", "dc1c2a119f074661934b4e42f0c715e4");
    @Override
    protected Integer doInBackground(String... params) {
        try {
            String food = params[0];
            if (food.equals("macarons")) {
                food = "macaron";
            }
            if (food.equals("hamburger")) {
                food = "burgers";
            }
            JSONObject search = api.getFoodItemID(food);
            JSONObject result = search.getJSONObject("result");
            //System.out.println(result);
            JSONObject foods = result.getJSONObject("foods");
            JSONArray foodArray = foods.getJSONArray("food");
            JSONObject object = foodArray.getJSONObject(0);
            return (int) object.getLong("food_id");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
