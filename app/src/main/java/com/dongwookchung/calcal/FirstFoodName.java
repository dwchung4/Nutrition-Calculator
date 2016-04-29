package com.dongwookchung.calcal;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.ArrayList;

public class FirstFoodName extends AsyncTask<Integer, Void, String> {
    //aufstand363@gmail.com
    private FatSecretAPI api = new FatSecretAPI("0c8526a948584cea8671a1976827dcff", "dc1c2a119f074661934b4e42f0c715e4");
    @Override
    protected String doInBackground(Integer... params) {
        try {
            int foodIndex = params[0];
            JSONObject search = api.getFoodItem(foodIndex);
            JSONObject result = search.getJSONObject("result");
            //System.out.println(result);
            JSONObject food = result.getJSONObject("food");
            String brand = "";
            if (food.has("brand_name")) {
                brand = food.getString("brand_name");
            }
            String foodName = food.getString("food_name");
            if (brand.length() > 0) {
                foodName = foodName+" ("+brand+")";
            }
            return foodName;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}