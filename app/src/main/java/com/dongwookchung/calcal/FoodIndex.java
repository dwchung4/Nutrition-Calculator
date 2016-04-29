package com.dongwookchung.calcal;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class FoodIndex extends AsyncTask<String, Void, ArrayList<Integer>> {
    //dwchung4@naver.com
    private FatSecretAPI api = new FatSecretAPI("44781770676b400caeabc39cbd1a35ff", "72558506e6ae42cda10636a54198c569");
    @Override
    protected ArrayList<Integer> doInBackground(String... params) {
        JSONObject search;
        try {
            String food = params[0];
            ArrayList<Integer> list = new ArrayList<>();
            if (food.equals("macarons")) {
                food = "macaron";
            }
            if (food.equals("hamburger")) {
                food = "burgers";
            }
            search = api.getFoodItemIDs(food);
            JSONObject result = search.getJSONObject("result");
            //System.out.println(result);
            JSONObject foods = result.getJSONObject("foods");
            JSONArray foodArray = foods.getJSONArray("food");
            for (int i = 0; i < foodArray.length(); i++) {
                JSONObject object = foodArray.getJSONObject(i);
                int x = (int) object.getLong("food_id");
                list.add(x);
            }
            return list;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
