package com.dongwookchung.calcal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Hashtable;
import java.util.Set;

public class Identify extends Activity {
    private static String currentAPI = "MetaMind";
    private static String foodCategory = "";
    private static String foodName = "";
    private static Hashtable<String, Double> nutritionTable = new Hashtable<>();
    private TextView textNutrition;
    private static double proportion = 1.0;
    private static String gender = "";
    private static double age = -1.0;
    private static double height = -1.0;
    private static double weight = -1.0;
    private static int activity = -1;
    final private static String imagePath = "sdcard/camera_app/food_image.jpeg";
    private TextView textMatch;
    private static int foodIndex = 0;
    private static Button buttonFood;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.identify);

        textNutrition = (TextView) findViewById(R.id.text_Nutrition);
        textNutrition.setTextSize(15);

        ImageView imageFood = (ImageView) findViewById(R.id.image_Food);
        imageFood.setImageDrawable(Drawable.createFromPath(imagePath));

        Bundle bundle = getIntent().getExtras();

        final Button buttonMetaMind = (Button)findViewById(R.id.button_MetaMind);
        final Button buttonGoogle = (Button)findViewById(R.id.button_Google);
        final Button buttonClarifai = (Button)findViewById(R.id.button_Clarifai);

        if (bundle != null && bundle.containsKey("foodCategory")) {
            foodCategory = (String)bundle.get("foodCategory");
            currentAPI = (String)bundle.get("currentAPI");
            if (currentAPI.equals("MetaMind")) {
                buttonMetaMind.setText("MetaMind (current)");
                buttonGoogle.setText("Google");
                buttonClarifai.setText("Clarifai");
            }
            else if (currentAPI.equals("Google")) {
            buttonMetaMind.setText("MetaMind");
            buttonGoogle.setText("Google (current)");
            buttonClarifai.setText("Clarifai");
        }
            else {
                buttonMetaMind.setText("MetaMind");
                buttonGoogle.setText("Google");
                buttonClarifai.setText("Clarifai (current)");
            }
        }
        else {
            try {
                foodCategory = new MetaMind().execute(imagePath).get();
                currentAPI = "MetaMind";
                buttonMetaMind.setText("MetaMind (current)");
                buttonGoogle.setText("Google");
                buttonClarifai.setText("Clarifai");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        textMatch = (TextView)findViewById(R.id.text_Match);
        textMatch.setText("Match Result: "+foodCategory);

        buttonFood = (Button) findViewById(R.id.button_Food);
        if (bundle != null && bundle.containsKey("foodIndex") && bundle.containsKey("foodName")) {
            foodIndex = (int)bundle.get("foodIndex");
            foodName = (String)bundle.get("foodName");
        }
        else {
            try {
                foodIndex = new FirstFoodIndex().execute(foodCategory).get();
                foodName = new FirstFoodName().execute(foodIndex).get();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        buttonFood.setText(foodName);
        if (foodName.length() > 40) {
            buttonFood.setTextSize(8);
        }
        else if (foodName.length() > 20) {
            buttonFood.setTextSize(10);
        }

        nutritionTable = new Nutrition().getNutrition(foodIndex);
        String nutritionFormatted = new NutritionFormatted().getNutrition(nutritionTable, 1.0, null);
        textNutrition.setText(nutritionFormatted);

        buttonFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonFood.getText().equals("No match")) {
                    Toast.makeText(getApplicationContext(), "There are no foods to choose from", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), Analyze.class);
                    intent.putExtra("foodCategory", foodCategory);
                    intent.putExtra("currentAPI", currentAPI);
                    startActivity(intent);
                }
            }
        });

        Button buttonPortion = (Button)findViewById(R.id.button_Portion);
        buttonPortion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProportionDialog();
            }
        });

        Button buttonPersonal = (Button)findViewById(R.id.button_Personal);
        buttonPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPersonalDialog();
            }
        });

        buttonMetaMind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentAPI.equals("MetaMind")) {
                    Toast.makeText(getApplicationContext(), "Already using MetaMind", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (currentAPI.equals("Google")) {
                        buttonGoogle.setText("Google");
                    }
                    else {
                        buttonClarifai.setText("Clarifai");
                    }
                    buttonMetaMind.setText("MetaMind (current)");
                    currentAPI = "MetaMind";

                    try {
                        String newFoodCategory = new MetaMind().execute(imagePath).get();
                        if (!newFoodCategory.equals(foodCategory)) {
                            foodCategory = newFoodCategory;
                            textMatch.setText("Match Result: "+foodCategory);
                            foodIndex = new FirstFoodIndex().execute(foodCategory).get();
                            foodName = new FirstFoodName().execute(foodIndex).get();
                            buttonFood.setText(foodName);
                            if (foodName.length() > 40) {
                                buttonFood.setTextSize(8);
                            }
                            else if (foodName.length() > 20) {
                                buttonFood.setTextSize(10);
                            }
                            nutritionTable = new Nutrition().getNutrition(foodIndex);
                            String nutritionFormatted = new NutritionFormatted().getNutrition(nutritionTable, 1.0, null);
                            textNutrition.setText(nutritionFormatted);
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        buttonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentAPI.equals("Google")) {
                    Toast.makeText(getApplicationContext(), "Already using Google", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (currentAPI.equals("MetaMind")) {
                        buttonMetaMind.setText("MetaMind");
                    }
                    else {
                        buttonClarifai.setText("Clarifai");
                    }
                    buttonGoogle.setText("Google (current)");
                    currentAPI = "Google";

                    try {
                        String newFoodCategory = new Google().execute(imagePath).get();
                        if (newFoodCategory.equals("No match")) {
                            buttonFood.setText("No match");
                            textNutrition.setText("");
                            textMatch.setText("No match");
                            foodCategory = "No match";
                        }
                        else if (!newFoodCategory.equals(foodCategory)) {
                            foodCategory = newFoodCategory;
                            textMatch.setText("Match result: "+foodCategory);
                            foodIndex = new FirstFoodIndex().execute(foodCategory).get();
                            foodName = new FirstFoodName().execute(foodIndex).get();
                            buttonFood.setText(foodName);
                            if (foodName.length() > 40) {
                                buttonFood.setTextSize(8);
                            } else if (foodName.length() > 20) {
                                buttonFood.setTextSize(10);
                            }
                            nutritionTable = new Nutrition().getNutrition(foodIndex);
                            String nutritionFormatted = new NutritionFormatted().getNutrition(nutritionTable, 1.0, null);
                            textNutrition.setText(nutritionFormatted);
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        buttonClarifai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentAPI.equals("Clarifai")) {
                    Toast.makeText(getApplicationContext(), "Already using Clarifai", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (currentAPI.equals("MetaMind")) {
                        buttonMetaMind.setText("MetaMind");
                    }
                    else {
                        buttonGoogle.setText("Google");
                    }
                    buttonClarifai.setText("Clarifai (current)");
                    currentAPI = "Clarifai";

                    try {
                        String newFoodCategory = new Clarifai().execute(imagePath).get();
                        if (newFoodCategory.equals("No match")) {
                            buttonFood.setText("No match");
                            textNutrition.setText("");
                            textMatch.setText("No match");
                            foodCategory = "No match";
                        }
                        else if (!newFoodCategory.equals(foodCategory)) {
                            foodCategory = newFoodCategory;
                            textMatch.setText("Match Result: "+foodCategory);
                            foodIndex = new FirstFoodIndex().execute(foodCategory).get();
                            foodName = new FirstFoodName().execute(foodIndex).get();
                            buttonFood.setText(foodName);
                            if (foodName.length() > 40) {
                                buttonFood.setTextSize(8);
                            }
                            else if (foodName.length() > 20) {
                                buttonFood.setTextSize(10);
                            }
                            nutritionTable = new Nutrition().getNutrition(foodIndex);
                            String nutritionFormatted = new NutritionFormatted().getNutrition(nutritionTable, 1.0, null);
                            textNutrition.setText(nutritionFormatted);
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    protected void showProportionDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(Identify.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Identify.this);
        alertDialogBuilder.setView(promptView);

        Set<String> keys = nutritionTable.keySet();
        double metric_serving_amount = -1.0;
        for (String key: keys) {
            if (key.contains("Serving: ")) {
                metric_serving_amount = nutritionTable.get(key);
                break;
            }
        }

        if (metric_serving_amount == -1.0) {
            alertDialogBuilder.setTitle("Enter portion (1 = per serving)");
        }
        else {
            alertDialogBuilder.setTitle("Enter portion in gram (per serving = "+metric_serving_amount+"g)");
        }

        final double amount = metric_serving_amount;

        final EditText editText = (EditText) promptView.findViewById(R.id.editText);
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String response = editText.getText().toString();
                        if (!isValid(response)) {
                            Toast.makeText(getApplicationContext(), "Please enter a positive number", Toast.LENGTH_SHORT).show();
                        } else {
                            double enteredWeight = Double.parseDouble(editText.getText().toString());
                            if (amount == -1.0) {
                                proportion = enteredWeight;
                            }
                            else {
                                proportion = enteredWeight / amount;
                            }

                            String nutritionFormatted = new NutritionFormatted().getNutrition(nutritionTable, proportion, null);
                            textNutrition.setText(nutritionFormatted);
                        }
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private boolean isValid(String s) {
        boolean isValid = false;
        try {
            Double.parseDouble(s);
            double temp = Double.parseDouble(s);
            if (temp > 0) {
                isValid = true;
            }
        }
        catch (NumberFormatException e) {

        }
        return isValid;
    }

    protected void showPersonalDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(Identify.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialog1, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Identify.this);
        alertDialogBuilder.setView(promptView);

        final RadioGroup radioGroup = (RadioGroup)promptView.findViewById(R.id.genderGroup);
        final RadioButton button1 = (RadioButton)promptView.findViewById(R.id.genderButton1);
        final RadioButton button2 = (RadioButton)promptView.findViewById(R.id.genderButton2);
        final EditText ageEditText = (EditText)promptView.findViewById(R.id.ageEditText);
        final EditText heightEditText = (EditText)promptView.findViewById(R.id.heightEditText);
        final EditText weightEditText = (EditText)promptView.findViewById(R.id.weightEditText);
        final RadioGroup activityGroup = (RadioGroup)promptView.findViewById(R.id.activityGroup);
        final RadioButton activityButton1 = (RadioButton)promptView.findViewById(R.id.activityButton1);
        final RadioButton activityButton2 = (RadioButton)promptView.findViewById(R.id.activityButton2);
        final RadioButton activityButton3 = (RadioButton)promptView.findViewById(R.id.activityButton3);
        final RadioButton activityButton4 = (RadioButton)promptView.findViewById(R.id.activityButton4);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        int selectedId = radioGroup.getCheckedRadioButtonId();
                        String getAge = ageEditText.getText().toString();
                        String getHeight = heightEditText.getText().toString();
                        String getWeight = weightEditText.getText().toString();
                        int activityId = activityGroup.getCheckedRadioButtonId();
                        if (selectedId == -1 || getAge.length() == 0 || getHeight.length() == 0 || getWeight.length() == 0 || activityId == -1) {
                            Toast.makeText(getApplicationContext(), "Please enter all information", Toast.LENGTH_SHORT).show();
                        }
                        else if (!isValid(getAge) || !isValid(getHeight) || !isValid(getWeight)) {
                            Toast.makeText(getApplicationContext(), "Please enter positive numbers for Age, Height and Weight", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (selectedId == button1.getId()) {
                                gender = "Male";
                            }
                            else if (selectedId == button2.getId()){
                                gender = "Female";
                            }
                            if (activityId == activityButton1.getId()) {
                                activity = 0;
                            }
                            else if (activityId == activityButton2.getId()) {
                                activity = 1;
                            }
                            else if (activityId == activityButton3.getId()) {
                                activity = 2;
                            }
                            else if (activityId == activityButton4.getId()) {
                                activity = 3;
                            }
                            age = Double.parseDouble(getAge);
                            height = Double.parseDouble(getHeight);
                            weight = Double.parseDouble(getWeight);

                            Daily daily = new Daily();
                            double dailyCalories = daily.getDailyCalories(gender, age, height, weight, activity);
                            double dailyCarbohydrate = daily.getDailyCarbohydrate(age);
                            double dailyProtein = daily.getDailyProtein(gender, age);
                            double dailySodium = daily.getDailySodium(age);
                            double dailyPotassium = daily.getDailyPotassium(age);
                            double dailyFiber = daily.getDailyFiber(gender, age);
                            double dailyVitaminA = daily.getDailyVitaminA(gender, age);
                            double dailyVitaminC = daily.getDailyVitaminC(gender, age);
                            double dailyCalcium = daily.getDailyCalcium(gender, age);
                            double dailyIron = daily.getDailyIron(gender, age);

                            Hashtable<String, Double> dailyNutritionTable = new Hashtable<>();
                            dailyNutritionTable.put("DailyCalorie", dailyCalories);
                            dailyNutritionTable.put("DailyCarbohydrate", dailyCarbohydrate);
                            dailyNutritionTable.put("DailyProtein", dailyProtein);
                            dailyNutritionTable.put("DailySodium", dailySodium);
                            dailyNutritionTable.put("DailyPotassium", dailyPotassium);
                            dailyNutritionTable.put("DailyFiber", dailyFiber);
                            dailyNutritionTable.put("DailyVitaminA", dailyVitaminA);
                            dailyNutritionTable.put("DailyVitaminC", dailyVitaminC);
                            dailyNutritionTable.put("DailyCalcium", dailyCalcium);
                            dailyNutritionTable.put("DailyIron", dailyIron);

                            String nutritionFormatted = new NutritionFormatted().getNutrition(nutritionTable, proportion, dailyNutritionTable);
                            textNutrition.setText(nutritionFormatted);
                        }
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

}