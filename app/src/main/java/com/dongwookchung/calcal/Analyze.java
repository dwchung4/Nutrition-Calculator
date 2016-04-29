package com.dongwookchung.calcal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class Analyze extends Activity {
    private static String foodCategory = "";
    private static String currentAPI;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analyze);

        final Bundle bundle = getIntent().getExtras();

        if (bundle.containsKey("foodCategoryOne")) {
            foodCategory = bundle.getString("foodCategoryOne");
        }
        else if (bundle.containsKey("foodCategoryTwo")) {
            foodCategory = bundle.getString("foodCategoryTwo");
        }
        else {
            foodCategory = bundle.getString("foodCategory");
        }

        currentAPI = bundle.getString("currentAPI");

        ArrayList<Integer> indexList = new ArrayList<>();
        try {
            indexList = new FoodIndex().execute(foodCategory).get();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<String> foodList = new ArrayList<>();
        try {
            foodList = new FoodName().execute(indexList).get();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        RelativeLayout layout = new RelativeLayout(this);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;

        int horizontalsofar = 0;
        int verticalsofar = 0;
        int buttonWidth = 280;
        int buttonHeight = 120;

        for (int i = 0; i < foodList.size(); i++) {
            final int index = indexList.get(i);
            final String foodInList = foodList.get(i);
            Button button = new Button(this);
            button.setText(foodInList);
            if (foodInList.length() > 60) {
                button.setTextSize(8);
            }
            else if (foodInList.length() < 20) {
                button.setTextSize(12);
            }
            else {
                button.setTextSize(10);
            }
            button.setHeight(buttonHeight);
            button.setWidth(buttonWidth);
            RelativeLayout.LayoutParams buttonParams =
                    new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
            if (horizontalsofar+buttonWidth >= screenWidth) {
                horizontalsofar = 0;
                verticalsofar += buttonHeight;
            }
            buttonParams.setMargins(horizontalsofar, verticalsofar, 0, 0);
            horizontalsofar += buttonWidth;
            layout.addView(button, buttonParams);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bundle.containsKey("foodCategoryOne")) {
                        Intent intent = new Intent(getApplicationContext(), IdentifyTwo.class);
                        intent.putExtra("foodNameOne", foodInList);
                        intent.putExtra("foodCategoryOne", foodCategory);
                        intent.putExtra("foodIndexOne", index);
                        intent.putExtra("currentAPI", currentAPI);
                        startActivity(intent);
                    }
                    else if (bundle.containsKey("foodCategoryTwo")) {
                        Intent intent = new Intent(getApplicationContext(), IdentifyTwo.class);
                        intent.putExtra("foodNameTwo", foodInList);
                        intent.putExtra("foodCategoryTwo", foodCategory);
                        intent.putExtra("foodIndexTwo", index);
                        intent.putExtra("currentAPI", currentAPI);
                        startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(getApplicationContext(), Identify.class);
                        intent.putExtra("foodName", foodInList);
                        intent.putExtra("foodCategory", foodCategory);
                        intent.putExtra("foodIndex", index);
                        intent.putExtra("currentAPI", currentAPI);
                        startActivity(intent);
                    }
                }
            });
        }

        setContentView(layout);
    }
}