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

public class IdentifyTwo extends Activity {
    final private static String imagePathOne = "sdcard/camera_app/food_image_0.jpeg";
    final private static String imagePathTwo = "sdcard/camera_app/food_image_1.jpeg";
    private static String currentAPI = "MetaMind";
    private static String foodCategoryOne = "";
    private static String foodCategoryTwo = "";
    private static String foodNameOne = "";
    private static String foodNameTwo = "";
    private static Hashtable<String, Double> nutritionTableOne = new Hashtable<>();
    private static Hashtable<String, Double> nutritionTableTwo = new Hashtable<>();
    private TextView textNutrition;
    private static double proportionOne = 1.0;
    private static double proportionTwo = 1.0;
    private static String gender = "";
    private static double age = -1.0;
    private static double height = -1.0;
    private static double weight = -1.0;
    private static int activity = -1;
    private TextView textMatchOne;
    private TextView textMatchTwo;
    private static int foodIndexOne = 0;
    private static int foodIndexTwo = 0;
    private static Button buttonFoodOne;
    private static Button buttonFoodTwo;
    private static String portionOne;
    private static String portionTwo;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.identifytwo);

        textNutrition = (TextView) findViewById(R.id.text_Nutrition);
        textNutrition.setTextSize(13);

        ImageView imageFoodOne = (ImageView) findViewById(R.id.image_FoodOne);
        imageFoodOne.setImageDrawable(Drawable.createFromPath(imagePathOne));
        ImageView imageFoodTwo = (ImageView) findViewById(R.id.image_FoodTwo);
        imageFoodTwo.setImageDrawable(Drawable.createFromPath(imagePathTwo));

        Bundle bundle = getIntent().getExtras();

        final Button buttonMetaMind = (Button)findViewById(R.id.button_MetaMind);
        final Button buttonGoogle = (Button)findViewById(R.id.button_Google);
        final Button buttonClarifai = (Button)findViewById(R.id.button_Clarifai);

        if (bundle != null && bundle.containsKey("foodCategoryOne")) {
            foodCategoryOne = (String)bundle.get("foodCategoryOne");
        }
        else {
            try {
                foodCategoryOne = new MetaMind().execute(imagePathOne).get();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (bundle != null && bundle.containsKey("foodCategoryTwo")) {
            foodCategoryTwo = (String)bundle.get("foodCategoryTwo");
        }
        else {
            try {
                foodCategoryTwo = new MetaMind().execute(imagePathTwo).get();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (bundle != null && bundle.containsKey("currentAPI")) {
            String newCurrentAPI = (String)bundle.get("currentAPI");
            if (newCurrentAPI.equals("MetaMind")) {
                buttonMetaMind.setText("MetaMind (current)");
                buttonGoogle.setText("Google");
                buttonClarifai.setText("Clarifai");
            }
            else if (newCurrentAPI.equals("Google")) {
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

        textMatchOne = (TextView)findViewById(R.id.text_MatchOne);
        textMatchOne.setText("Match Result: "+foodCategoryOne);
        textMatchTwo = (TextView)findViewById(R.id.text_MatchTwo);
        textMatchTwo.setText("Match Result: "+foodCategoryTwo);

        buttonFoodOne = (Button) findViewById(R.id.button_FoodOne);
        if (bundle != null && bundle.containsKey("foodIndexOne") && bundle.containsKey("foodNameOne")) {
            foodIndexOne = (int)bundle.get("foodIndexOne");
            foodNameOne = (String)bundle.get("foodNameOne");
        }
        else {
            try {
                foodIndexOne = new FirstFoodIndex().execute(foodCategoryOne).get();
                foodNameOne = new FirstFoodName().execute(foodIndexOne).get();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        buttonFoodOne.setText(foodNameOne);
        if (foodNameOne.length() > 40) {
            buttonFoodOne.setTextSize(8);
        }
        else if (foodNameOne.length() > 20) {
            buttonFoodOne.setTextSize(10);
        }

        buttonFoodTwo = (Button) findViewById(R.id.button_FoodTwo);
        if (bundle != null && bundle.containsKey("foodIndexTwo") && bundle.containsKey("foodNameTwo")) {
            foodIndexTwo = (int)bundle.get("foodIndexTwo");
            foodNameTwo = (String)bundle.get("foodNameTwo");
        }
        else {
            try {
                foodIndexTwo = new FirstFoodIndex().execute(foodCategoryTwo).get();
                foodNameTwo = new FirstFoodName().execute(foodIndexTwo).get();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        buttonFoodTwo.setText(foodNameTwo);
        if (foodNameTwo.length() > 40) {
            buttonFoodTwo.setTextSize(8);
        }
        else if (foodNameTwo.length() > 20) {
            buttonFoodTwo.setTextSize(10);
        }

        nutritionTableOne = new Nutrition().getNutrition(foodIndexOne);
        nutritionTableTwo = new Nutrition().getNutrition(foodIndexTwo);
        String nutritionFormattedOne = new NutritionFormatted().getNutritionTwo(nutritionTableOne, 1.0, nutritionTableTwo, 1.0, null);
        textNutrition.setText(nutritionFormattedOne);

        buttonFoodOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonFoodOne.getText().equals("No match")) {
                    Toast.makeText(getApplicationContext(), "There are no foods to choose from", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), Analyze.class);
                    intent.putExtra("foodCategoryOne", foodCategoryOne);
                    intent.putExtra("currentAPI", currentAPI);
                    startActivity(intent);
                }
            }
        });

        buttonFoodTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonFoodTwo.getText().equals("No match")) {
                    Toast.makeText(getApplicationContext(), "There are no foods to choose from", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), Analyze.class);
                    intent.putExtra("foodCategoryTwo", foodCategoryTwo);
                    intent.putExtra("currentAPI", currentAPI);
                    startActivity(intent);
                }
            }
        });

        Button buttonPortionOne = (Button)findViewById(R.id.button_PortionOne);
        buttonPortionOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProportionDialogOne();
            }
        });

        Button buttonPortionTwo = (Button)findViewById(R.id.button_PortionTwo);
        buttonPortionTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProportionDialogTwo();
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
                        String newFoodCategoryOne = new MetaMind().execute(imagePathOne).get();
                        if (!newFoodCategoryOne.equals(foodCategoryOne)) {
                            foodCategoryOne = newFoodCategoryOne;
                            textMatchOne.setText("Match Result: "+foodCategoryOne);
                            foodIndexOne = new FirstFoodIndex().execute(foodCategoryOne).get();
                            foodNameOne = new FirstFoodName().execute(foodIndexOne).get();
                            buttonFoodOne.setText(foodNameOne);
                            if (foodNameOne.length() > 40) {
                                buttonFoodOne.setTextSize(8);
                            }
                            else if (foodNameOne.length() > 20) {
                                buttonFoodOne.setTextSize(10);
                            }
                            nutritionTableOne = new Nutrition().getNutrition(foodIndexOne);
                        }

                        String newFoodCategoryTwo = new MetaMind().execute(imagePathTwo).get();
                        if (!newFoodCategoryTwo.equals(foodCategoryTwo)) {
                            foodCategoryTwo = newFoodCategoryTwo;
                            textMatchTwo.setText("Match Result: "+foodCategoryTwo);
                            foodIndexTwo = new FirstFoodIndex().execute(foodCategoryTwo).get();
                            foodNameTwo = new FirstFoodName().execute(foodIndexTwo).get();
                            buttonFoodTwo.setText(foodNameTwo);
                            if (foodNameTwo.length() > 40) {
                                buttonFoodTwo.setTextSize(8);
                            }
                            else if (foodNameTwo.length() > 20) {
                                buttonFoodTwo.setTextSize(10);
                            }
                            nutritionTableTwo = new Nutrition().getNutrition(foodIndexTwo);
                        }
                        String nutritionFormatted = new NutritionFormatted().getNutritionTwo(nutritionTableOne, 1.0, nutritionTableTwo, 1.0, null);
                        textNutrition.setText(nutritionFormatted);
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
                        String newFoodCategoryOne = new Google().execute(imagePathOne).get();
                        if (newFoodCategoryOne.equals("No match")) {
                            buttonFoodOne.setText("No match");
                            textMatchOne.setText("No match");
                            foodCategoryOne = "No match";
                        }
                        else if (!newFoodCategoryOne.equals(foodCategoryOne)) {
                            foodCategoryOne = newFoodCategoryOne;
                            textMatchOne.setText("Match result: "+foodCategoryOne);
                            foodIndexOne = new FirstFoodIndex().execute(foodCategoryOne).get();
                            foodNameOne = new FirstFoodName().execute(foodIndexOne).get();
                            buttonFoodOne.setText(foodNameOne);
                            if (foodNameOne.length() > 40) {
                                buttonFoodOne.setTextSize(8);
                            } else if (foodNameOne.length() > 20) {
                                buttonFoodOne.setTextSize(10);
                            }
                            nutritionTableOne = new Nutrition().getNutrition(foodIndexOne);
                        }
                        String newFoodCategoryTwo = new Google().execute(imagePathTwo).get();
                        if (newFoodCategoryTwo.equals("No match")) {
                            buttonFoodTwo.setText("No match");
                            textMatchTwo.setText("No match");
                            foodCategoryTwo = "No match";
                        }
                        else if (!newFoodCategoryTwo.equals(foodCategoryTwo)) {
                            foodCategoryTwo = newFoodCategoryTwo;
                            textMatchTwo.setText("Match result: "+foodCategoryTwo);
                            foodIndexTwo = new FirstFoodIndex().execute(foodCategoryTwo).get();
                            foodNameTwo = new FirstFoodName().execute(foodIndexTwo).get();
                            buttonFoodTwo.setText(foodNameTwo);
                            if (foodNameTwo.length() > 40) {
                                buttonFoodTwo.setTextSize(8);
                            } else if (foodNameTwo.length() > 20) {
                                buttonFoodTwo.setTextSize(10);
                            }
                            nutritionTableTwo = new Nutrition().getNutrition(foodIndexTwo);
                        }

                        if (foodCategoryOne.equals("No match")) {
                            if (foodCategoryTwo.equals("No match")) {
                                textNutrition.setText("");
                            }
                            else {
                                String nutritionFormatted = new NutritionFormatted().getNutrition(nutritionTableTwo, 1.0, null);
                                textNutrition.setText(nutritionFormatted);
                            }
                        }
                        else if (foodCategoryTwo.equals("No match")) {
                            String nutritionFormatted = new NutritionFormatted().getNutrition(nutritionTableOne, 1.0, null);
                            textNutrition.setText(nutritionFormatted);
                        }
                        else {
                            String nutritionFormatted = new NutritionFormatted().getNutritionTwo(nutritionTableOne, 1.0, nutritionTableTwo, 1.0, null);
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
                        String newFoodCategoryOne = new Clarifai().execute(imagePathOne).get();
                        if (newFoodCategoryOne.equals("No match")) {
                            buttonFoodOne.setText("No match");
                            textNutrition.setText("");
                            textMatchOne.setText("No match");
                            foodCategoryOne = "No match";
                        }
                        else if (!newFoodCategoryOne.equals(foodCategoryOne)) {
                            foodCategoryOne = newFoodCategoryOne;
                            textMatchOne.setText("Match result: "+foodCategoryOne);
                            foodIndexOne = new FirstFoodIndex().execute(foodCategoryOne).get();
                            foodNameOne = new FirstFoodName().execute(foodIndexOne).get();
                            buttonFoodOne.setText(foodNameOne);
                            if (foodNameOne.length() > 40) {
                                buttonFoodOne.setTextSize(8);
                            } else if (foodNameOne.length() > 20) {
                                buttonFoodOne.setTextSize(10);
                            }
                            nutritionTableOne = new Nutrition().getNutrition(foodIndexOne);
                        }
                        String newFoodCategoryTwo = new Clarifai().execute(imagePathTwo).get();
                        if (newFoodCategoryTwo.equals("No match")) {
                            buttonFoodTwo.setText("No match");
                            textNutrition.setText("");
                            textMatchTwo.setText("No match");
                            foodCategoryTwo = "No match";
                        }
                        else if (!newFoodCategoryTwo.equals(foodCategoryTwo)) {
                            foodCategoryTwo = newFoodCategoryTwo;
                            textMatchTwo.setText("Match result: "+foodCategoryTwo);
                            foodIndexTwo = new FirstFoodIndex().execute(foodCategoryTwo).get();
                            foodNameTwo = new FirstFoodName().execute(foodIndexTwo).get();
                            buttonFoodTwo.setText(foodNameTwo);
                            if (foodNameTwo.length() > 40) {
                                buttonFoodTwo.setTextSize(8);
                            } else if (foodNameTwo.length() > 20) {
                                buttonFoodTwo.setTextSize(10);
                            }
                            nutritionTableTwo = new Nutrition().getNutrition(foodIndexTwo);
                        }

                        if (foodCategoryOne.equals("No match")) {
                            if (foodCategoryTwo.equals("No match")) {
                                textNutrition.setText("");
                            }
                            else {
                                String nutritionFormatted = new NutritionFormatted().getNutrition(nutritionTableTwo, 1.0, null);
                                textNutrition.setText(nutritionFormatted);
                            }
                        }
                        else if (foodCategoryTwo.equals("No match")) {
                            String nutritionFormatted = new NutritionFormatted().getNutrition(nutritionTableOne, 1.0, null);
                            textNutrition.setText(nutritionFormatted);
                        }
                        else {
                            String nutritionFormatted = new NutritionFormatted().getNutritionTwo(nutritionTableOne, 1.0, nutritionTableTwo, 1.0, null);
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

    protected void showProportionDialogOne() {
        LayoutInflater layoutInflater = LayoutInflater.from(IdentifyTwo.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(IdentifyTwo.this);
        alertDialogBuilder.setView(promptView);

        Set<String> keys = nutritionTableOne.keySet();
        double metric_serving_amount = -1.0;
        for (String key: keys) {
            if (key.contains("Serving: ")) {
                metric_serving_amount = nutritionTableOne.get(key);
                break;
            }
        }

        if (metric_serving_amount == -1.0) {
            alertDialogBuilder.setTitle("Enter portion (1 = per serving)");
        }
        else {
            alertDialogBuilder.setTitle("Enter portion in gram (per serving = "+metric_serving_amount+"g)");
        }

        final double amountOne = metric_serving_amount;

        final EditText editText = (EditText) promptView.findViewById(R.id.editText);
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String response = editText.getText().toString();
                        if (!isValid(response)) {
                            Toast.makeText(getApplicationContext(), "Please enter a positive number", Toast.LENGTH_SHORT).show();
                        } else {
                            double enteredWeight = Double.parseDouble(editText.getText().toString());
                            if (amountOne == -1.0) {
                                proportionOne = enteredWeight;
                            }
                            else {
                                proportionOne = enteredWeight / amountOne;
                            }

                            String nutritionFormatted = new NutritionFormatted().getNutritionTwo(nutritionTableOne, proportionOne, nutritionTableTwo, proportionTwo, null);
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

    protected void showProportionDialogTwo() {
        LayoutInflater layoutInflater = LayoutInflater.from(IdentifyTwo.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(IdentifyTwo.this);
        alertDialogBuilder.setView(promptView);

        Set<String> keys = nutritionTableTwo.keySet();
        double metric_serving_amount = -1.0;
        for (String key: keys) {
            if (key.contains("Serving: ")) {
                metric_serving_amount = nutritionTableTwo.get(key);
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
                                proportionTwo = enteredWeight;
                            }
                            else {
                                proportionTwo = enteredWeight / amount;
                            }

                            String nutritionFormatted = new NutritionFormatted().getNutritionTwo(nutritionTableOne, proportionOne, nutritionTableTwo, proportionTwo, null);
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
        LayoutInflater layoutInflater = LayoutInflater.from(IdentifyTwo.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialog1, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(IdentifyTwo.this);
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

                            String nutritionFormatted = new NutritionFormatted().getNutritionTwo(nutritionTableOne, proportionOne, nutritionTableTwo, proportionTwo, dailyNutritionTable);
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