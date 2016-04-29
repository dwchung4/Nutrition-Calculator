package com.dongwookchung.calcal;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.ArrayList;

public class FoodName extends AsyncTask<ArrayList<Integer>, Void, ArrayList<String>> {
    //271828zz@gmail.com
    private FatSecretAPI api = new FatSecretAPI("f3ca9b63132c474e8cb8c09e8e68032f", "294b9eda67fa4cf3b30ec3f21c229ee4");
    @Override
    protected ArrayList<String> doInBackground(ArrayList<Integer>... params) {
        ArrayList<Integer> indexList = params[0];
        ArrayList<String> foodList = new ArrayList<>();
        JSONObject search;
        try {
            for (int i = 0; i < indexList.size(); i++) {
                int x = indexList.get(i);
                search = api.getFoodItem(x);
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
                foodList.add(foodName);
            }
            return foodList;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}