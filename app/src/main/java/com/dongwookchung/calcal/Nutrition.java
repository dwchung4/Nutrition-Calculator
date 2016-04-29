package com.dongwookchung.calcal;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Hashtable;
import java.util.Set;

public class Nutrition {
    private DecimalFormat df = new DecimalFormat("#.##");
    private String serving = "";
    private double proportion = 1.0;
    private double amount = -1.0;
    private double calories = -1.0;
    private double carbohydrate = -1.0;
    private double protein = -1.0;
    private double fat = -1.0;
    private double saturated_fat = -1.0;
    private double polyunsaturated_fat = -1.0;
    private double monounsaturated_fat = -1.0;
    private double trans_fat = -1.0;
    private double cholesterol = -1.0;
    private double sodium = -1.0;
    private double potassium = -1.0;
    private double fiber = -1.0;
    private double sugar = -1.0;
    private double vitamin_a = -1.0;
    private double vitamin_c = -1.0;
    private double calcium = -1.0;
    private double iron = -1.0;

    public Hashtable<String, Double> getNutrition(int index) {
        try {
            Hashtable<String, Double> nutritionTable = new Hashtable<String, Double>();
            JSONObject nutrition = new foodTask().execute(index).get();
            Object item = nutrition.get("serving");
            JSONObject menu = null;
            if (item instanceof JSONArray) {
                JSONArray array = (JSONArray) item;
                for (int i = 0; i < array.length(); i++) {
                    JSONObject temp = array.getJSONObject(i);
                    String quant = temp.getString("metric_serving_unit");
                    if (quant.equals("g") || quant.equals("oz")) {
                        menu = temp;
                        break;
                    }
                }
                if (menu == null) {
                    menu = array.getJSONObject(0);
                }

            }
            else {
                menu = (JSONObject) item;
            }

            calories = Double.parseDouble(menu.getString("calories"));
            carbohydrate = Double.parseDouble(menu.getString("carbohydrate"));
            protein = Double.parseDouble(menu.getString("protein"));
            fat = Double.parseDouble(menu.getString("fat"));

            if (menu.has("saturated_fat")) {
                saturated_fat = Double.parseDouble(menu.getString("saturated_fat"));
            }
            if (menu.has("polyunsaturated_fat")) {
                polyunsaturated_fat = Double.parseDouble(menu.getString("polyunsaturated_fat"));
            }
            if (menu.has("monounsaturated_fat")) {
                monounsaturated_fat = Double.parseDouble(menu.getString("monounsaturated_fat"));
            }
            if (menu.has("trans_fat")) {
                trans_fat = Double.parseDouble(menu.getString("trans_fat"));
            }
            if (menu.has("cholesterol")) {
                cholesterol = Double.parseDouble(menu.getString("cholesterol"));
            }
            if (menu.has("sodium")) {
                sodium = Double.parseDouble(menu.getString("sodium"));
            }
            if (menu.has("potassium")) {
                potassium = Double.parseDouble(menu.getString("potassium"));
            }
            if (menu.has("fiber")) {
                fiber = Double.parseDouble(menu.getString("fiber"));
            }
            if (menu.has("sugar")) {
                sugar = Double.parseDouble(menu.getString("sugar"));
            }
            if (menu.has("vitamin_a")) {
                vitamin_a = Double.parseDouble(menu.getString("vitamin_a"))/100.0*800.0;
                vitamin_a = Double.parseDouble(df.format(vitamin_a));
            }
            if (menu.has("vitamin_c")) {
                vitamin_c = Double.parseDouble(menu.getString("vitamin_c"))/100.0*60.0;
                vitamin_c = Double.parseDouble(df.format(vitamin_c));
            }
            if (menu.has("calcium")) {
                calcium = Double.parseDouble(menu.getString("calcium"))/100.0*1000.0;
                calcium = Double.parseDouble(df.format(calcium));
            }
            if (menu.has("iron")) {
                iron = Double.parseDouble(menu.getString("iron"))/100.0*18.0;
                iron = Double.parseDouble(df.format(iron));
            }

            if (menu.has("measurement_description")) {
                String measurement_description = "per "+menu.getString("measurement_description");
                serving += measurement_description+" ";
            }

            double metric_serving_amount = -1.0;
            if (menu.has("metric_serving_amount") && menu.has("metric_serving_unit")) {
                metric_serving_amount = Double.parseDouble(menu.getString("metric_serving_amount"));
                String weightunit = menu.getString("metric_serving_unit");
                if (weightunit.equals("oz")) {
                    metric_serving_amount *= 28.3495;
                    metric_serving_amount = Double.parseDouble(df.format(metric_serving_amount));
                    weightunit = "g";
                }
                amount = metric_serving_amount;
            }

            nutritionTable.put("Serving: " + serving, metric_serving_amount);

            nutritionTable.put("Calories", calories);
            nutritionTable.put("Carbohydrate", carbohydrate);
            nutritionTable.put("Protein", protein);
            nutritionTable.put("Fat", fat);

            if (saturated_fat != -1.0) {
                nutritionTable.put("Saturated Fat", saturated_fat);
            }
            if (polyunsaturated_fat != -1.0) {
                nutritionTable.put("Polyunsaturated Fat", polyunsaturated_fat);
            }
            if (monounsaturated_fat != -1.0) {
                nutritionTable.put("Monounsaturated Fat", monounsaturated_fat);
            }
            if (trans_fat != -1.0) {
                nutritionTable.put("Trans Fat", trans_fat);
            }
            if (cholesterol != -1.0) {
                nutritionTable.put("Cholesterol", cholesterol);
            }
            if (sodium != -1.0) {
                nutritionTable.put("Sodium", sodium);
            }
            if (potassium != -1.0) {
                nutritionTable.put("Potassium", potassium);
            }
            if (fiber != -1.0) {
                nutritionTable.put("Fiber", fiber);
            }
            if (sugar != -1.0) {
                nutritionTable.put("Sugar", sugar);
            }
            if (vitamin_a != -1.0) {
                nutritionTable.put("Vitamin A", vitamin_a);
            }
            if (vitamin_c != -1.0) {
                nutritionTable.put("Vitamin C", vitamin_c);
            }
            if (calcium != -1.0) {
                nutritionTable.put("Calcium", calcium);
            }
            if (iron != -1.0) {
                nutritionTable.put("Iron", iron);
            }

            return nutritionTable;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private class foodTask extends AsyncTask<Integer, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(Integer... params) {
            FatSecretAPI api = new FatSecretAPI("44781770676b400caeabc39cbd1a35ff", "72558506e6ae42cda10636a54198c569");
            Integer index = params[0];
            JSONObject search;
            try {
                search = api.getFoodItem(index);
                JSONObject result = search.getJSONObject("result");
                JSONObject food = result.getJSONObject("food");
                JSONObject servings = food.getJSONObject("servings");
                return servings;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /*
    public Hashtable<String, Double> combine(Hashtable<String, Double> tableOne, Hashtable<String, Double> tableTwo) {
        Hashtable<String, Double> combinedTable = new Hashtable<>();
        combinedTable.put("Food1 - Serving: ", tableOne.get("Serving: "));
        combinedTable.put("Food2 - Serving: ", tableTwo.get("Serving: "));
        combinedTable.put("Calories", tableOne.get("Calories")+tableTwo.get("Calories"));
        combinedTable.put("Carbohydrate", tableOne.get("Carbohydrate")+tableTwo.get("Carbohydrate"));
        combinedTable.put("Protein", tableOne.get("Protein")+tableTwo.get("Protein"));
        combinedTable.put("Fat", tableOne.get("Fat")+tableTwo.get("Fat"));

        Double sum = 0.0;
        if (tableOne.containsKey("Saturated Fat")) {
            sum += tableOne.get("Saturated Fat");
        }
        if (tableTwo.containsKey("Saturated Fat")) {
            sum += tableTwo.get("Saturated Fat");
        }
        if (sum != 0.0) {
            combinedTable.put("Saturated Fat", sum);
        }

        sum = 0.0;
        if (tableOne.containsKey("Polyunsaturated Fat")) {
            sum += tableOne.get("Polyunsaturated Fat");
        }
        if (tableTwo.containsKey("Polyunsaturated Fat")) {
            sum += tableTwo.get("Polyunsaturated Fat");
        }
        if (sum != 0.0) {
            combinedTable.put("Polyunsaturated Fat", sum);
        }

        sum = 0.0;
        if (tableOne.containsKey("Monounsaturated Fat")) {
            sum += tableOne.get("Monounsaturated Fat");
        }
        if (tableTwo.containsKey("Monounsaturated Fat")) {
            sum += tableTwo.get("Monounsaturated Fat");
        }
        if (sum != 0.0) {
            combinedTable.put("Monounsaturated Fat", sum);
        }

        sum = 0.0;
        if (tableOne.containsKey("Trans Fat")) {
            sum += tableOne.get("Trans Fat");
        }
        if (tableTwo.containsKey("Trans Fat")) {
            sum += tableTwo.get("Trans Fat");
        }
        if (sum != 0.0) {
            combinedTable.put("Trans Fat", sum);
        }

        sum = 0.0;
        if (tableOne.containsKey("Cholesterol")) {
            sum += tableOne.get("Cholesterol");
        }
        if (tableTwo.containsKey("Cholesterol")) {
            sum += tableTwo.get("Cholesterol");
        }
        if (sum != 0.0) {
            combinedTable.put("Cholesterol", sum);
        }

        sum = 0.0;
        if (tableOne.containsKey("Sodium")) {
            sum += tableOne.get("Sodium");
        }
        if (tableTwo.containsKey("Sodium")) {
            sum += tableTwo.get("Sodium");
        }
        if (sum != 0.0) {
            combinedTable.put("Sodium", sum);
        }

        sum = 0.0;
        if (tableOne.containsKey("Potassium")) {
            sum += tableOne.get("Potassium");
        }
        if (tableTwo.containsKey("Potassium")) {
            sum += tableTwo.get("Potassium");
        }
        if (sum != 0.0) {
            combinedTable.put("Potassium", sum);
        }

        sum = 0.0;
        if (tableOne.containsKey("Fiber")) {
            sum += tableOne.get("Fiber");
        }
        if (tableTwo.containsKey("Fiber")) {
            sum += tableTwo.get("Fiber");
        }
        if (sum != 0.0) {
            combinedTable.put("Fiber", sum);
        }

        sum = 0.0;
        if (tableOne.containsKey("Sugar")) {
            sum += tableOne.get("Sugar");
        }
        if (tableTwo.containsKey("Sugar")) {
            sum += tableTwo.get("Sugar");
        }
        if (sum != 0.0) {
            combinedTable.put("Sugar", sum);
        }

        sum = 0.0;
        if (tableOne.containsKey("Vitamin A")) {
            sum += tableOne.get("Vitamin A");
        }
        if (tableTwo.containsKey("Vitamin A")) {
            sum += tableTwo.get("Vitamin A");
        }
        if (sum != 0.0) {
            combinedTable.put("Vitamin A", sum);
        }

        sum = 0.0;
        if (tableOne.containsKey("Vitamin C")) {
            sum += tableOne.get("Vitamin C");
        }
        if (tableTwo.containsKey("Vitamin C")) {
            sum += tableTwo.get("Vitamin C");
        }
        if (sum != 0.0) {
            combinedTable.put("Vitamin C", sum);
        }

        sum = 0.0;
        if (tableOne.containsKey("Calcium")) {
            sum += tableOne.get("Calcium");
        }
        if (tableTwo.containsKey("Calcium")) {
            sum += tableTwo.get("Calcium");
        }
        if (sum != 0.0) {
            combinedTable.put("Calcium", sum);
        }

        sum = 0.0;
        if (tableOne.containsKey("Iron")) {
            sum += tableOne.get("Iron");
        }
        if (tableTwo.containsKey("Iron")) {
            sum += tableTwo.get("Iron");
        }
        if (sum != 0.0) {
            combinedTable.put("Iron", sum);
        }

        return combinedTable;
    }
    */
}