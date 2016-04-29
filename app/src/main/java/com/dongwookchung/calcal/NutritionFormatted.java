package com.dongwookchung.calcal;

import java.lang.StringBuilder;
import java.text.DecimalFormat;
import java.util.Hashtable;
import java.util.Set;

public class NutritionFormatted {
	private DecimalFormat df = new DecimalFormat("#.##");
	private DecimalFormat df1 = new DecimalFormat("#.#");

	public String getNutrition(Hashtable<String, Double> nutritionTable, double proportion,
							   Hashtable<String, Double> dailyNutritionTable) {
		StringBuilder sb = new StringBuilder();
		Set<String> keys = nutritionTable.keySet();
		String serving = "";
		double metric_serving_amount = -1.0;
		for (String key: keys) {
			if (key.contains("Serving: ")) {
				serving = key.replace("Serving: ", "");
				metric_serving_amount = nutritionTable.get(key);
				break;
			}
		}

		if (metric_serving_amount == -1.0) {
			if (proportion == 1.0) {
				sb.append("per serving\n");
			}
			else {
				sb.append(df1.format(proportion)+" servings\n");
			}
		}
		else {
			if (proportion == 1.0) {
				sb.append("per serving: ("+df.format(metric_serving_amount*proportion)+"g)\n");
			}
			else {
				sb.append(df1.format(proportion)+" servings ("+df.format(metric_serving_amount*proportion)+"g)\n");
			}
		}

		if (dailyNutritionTable == null) {
			sb.append("Calories: "+df.format(nutritionTable.get("Calories") * proportion)+"kcal\n");
			sb.append("Carbohydrate: "+df.format(nutritionTable.get("Carbohydrate") * proportion)+"g\n");
			sb.append("Protein: "+df.format(nutritionTable.get("Protein") * proportion)+"g\n");
			sb.append("Fat: "+df.format(nutritionTable.get("Fat") * proportion)+"g\n");

			if (nutritionTable.containsKey("Saturated Fat")) {
				sb.append("Saturated Fat: "+df.format(nutritionTable.get("Saturated Fat") * proportion)+"g\n");
			}
			if (nutritionTable.containsKey("Polyunsaturated Fat")) {
				sb.append("Polyunsaturated Fat: "+df.format(nutritionTable.get("Polyunsaturated Fat") * proportion)+"g\n");
			}
			if (nutritionTable.containsKey("Monounsaturated Fat")) {
				sb.append("Monounsaturated Fat: "+df.format(nutritionTable.get("Monounsaturated Fat") * proportion)+"g\n");
			}
			if (nutritionTable.containsKey("Trans Fat")) {
				sb.append("Trans Fat: "+df.format(nutritionTable.get("Trans Fat") * proportion)+"g\n");
			}
			if (nutritionTable.containsKey("Cholesterol")) {
				sb.append("Cholesterol: "+df.format(nutritionTable.get("Cholesterol") * proportion)+"mg\n");
			}
			if (nutritionTable.containsKey("Sodium")) {
				sb.append("Sodium: "+df.format(nutritionTable.get("Sodium") * proportion)+"mg\n");
			}
			if (nutritionTable.containsKey("Potassium")) {
				sb.append("Potassium: "+df.format(nutritionTable.get("Potassium") * proportion)+"mg\n");
			}
			if (nutritionTable.containsKey("Fiber")) {
				sb.append("Fiber: "+df.format(nutritionTable.get("Fiber") * proportion)+"g\n");
			}
			if (nutritionTable.containsKey("Sugar")) {
				sb.append("Sugar: "+df.format(nutritionTable.get("Sugar") * proportion)+"g\n");
			}
			if (nutritionTable.containsKey("Vitamin A")) {
				sb.append("Vitamin A: "+df.format(nutritionTable.get("Vitamin A") * proportion)+"mcg\n");
			}
			if (nutritionTable.containsKey("Vitamin C")) {
				sb.append("Vitamin C: "+df.format(nutritionTable.get("Vitamin C") * proportion)+"mg\n");
			}
			if (nutritionTable.containsKey("Calcium")) {
				sb.append("Calcium: "+df.format(nutritionTable.get("Calcium") * proportion)+"mg\n");
			}
			if (nutritionTable.containsKey("Iron")) {
				sb.append("Iron: "+df.format(nutritionTable.get("Iron") * proportion)+"mg\n");
			}
		}
		else {


			sb.append("Calories: "+df.format(nutritionTable.get("Calories") * proportion)+"kcal ("+df.format(nutritionTable.get("Calories") * proportion/dailyNutritionTable.get("DailyCalorie")*100)+"%)\n");
			sb.append("Carbohydrate: "+df.format(nutritionTable.get("Carbohydrate") * proportion)+"g ("+df.format(nutritionTable.get("Carbohydrate") * proportion/dailyNutritionTable.get("DailyCarbohydrate")*100)+"%)\n");
			sb.append("Protein: "+df.format(nutritionTable.get("Protein") * proportion)+"g ("+df.format(nutritionTable.get("Protein") * proportion/dailyNutritionTable.get("DailyProtein")*100)+"%)\n");
			sb.append("Fat: "+df.format(nutritionTable.get("Fat") * proportion)+"g\n");

			if (nutritionTable.containsKey("Saturated Fat")) {
				sb.append("Saturated Fat: "+df.format(nutritionTable.get("Saturated Fat") * proportion)+"g\n");
			}
			if (nutritionTable.containsKey("Polyunsaturated Fat")) {
				sb.append("Polyunsaturated Fat: "+df.format(nutritionTable.get("Polyunsaturated Fat") * proportion)+"g\n");
			}
			if (nutritionTable.containsKey("Monounsaturated Fat")) {
				sb.append("Monounsaturated Fat: "+df.format(nutritionTable.get("Monounsaturated Fat") * proportion)+"g\n");
			}
			if (nutritionTable.containsKey("Trans Fat")) {
				sb.append("Trans Fat: "+df.format(nutritionTable.get("Trans Fat") * proportion)+"g\n");
			}
			if (nutritionTable.containsKey("Cholesterol")) {
				sb.append("Cholesterol: "+df.format(nutritionTable.get("Cholesterol") * proportion)+"mg\n");
			}
			if (nutritionTable.containsKey("Sodium")) {
				sb.append("Sodium: "+df.format(nutritionTable.get("Sodium") * proportion)+"mg ("+df.format(nutritionTable.get("Sodium") * proportion/dailyNutritionTable.get("DailySodium")*100)+"%)\n");
			}
			if (nutritionTable.containsKey("Potassium")) {
				sb.append("Potassium: "+df.format(nutritionTable.get("Potassium") * proportion)+"mg ("+df.format(nutritionTable.get("Potassium") * proportion/dailyNutritionTable.get("DailyPotassium")*100)+"%)\n");
			}
			if (nutritionTable.containsKey("Fiber")) {
				sb.append("Fiber: "+df.format(nutritionTable.get("Fiber") * proportion)+"g ("+df.format(nutritionTable.get("Fiber") * proportion/dailyNutritionTable.get("DailyFiber")*100)+"%)\n");
			}
			if (nutritionTable.containsKey("Sugar")) {
				sb.append("Sugar: "+df.format(nutritionTable.get("Sugar") * proportion)+"g\n");
			}
			if (nutritionTable.containsKey("Vitamin A")) {
				sb.append("Vitamin A: "+df.format(nutritionTable.get("Vitamin A") * proportion)+"mcg ("+df.format(nutritionTable.get("Vitamin A") * proportion/dailyNutritionTable.get("DailyVitaminA")*100)+"%)\n");
			}
			if (nutritionTable.containsKey("Vitamin C")) {
				sb.append("Vitamin C: "+df.format(nutritionTable.get("Vitamin C") * proportion)+"mg ("+df.format(nutritionTable.get("Vitamin C") * proportion/dailyNutritionTable.get("DailyVitaminC")*100)+"%)\n");
			}
			if (nutritionTable.containsKey("Calcium")) {
				sb.append("Calcium: "+df.format(nutritionTable.get("Calcium") * proportion)+"mg ("+df.format(nutritionTable.get("Calcium") * proportion/dailyNutritionTable.get("DailyCalcium")*100)+"%)\n");
			}
			if (nutritionTable.containsKey("Iron")) {
				sb.append("Iron: "+df.format(nutritionTable.get("Iron") * proportion)+"mg ("+df.format(nutritionTable.get("Iron") * proportion/dailyNutritionTable.get("DailyIron")*100)+"%)\n");
			}
		}

		return sb.toString();
	}

	public String getNutritionTwo(Hashtable<String, Double> nutritionTableOne, double proportionOne,
								  Hashtable<String, Double> nutritionTableTwo, double proportionTwo,
								  Hashtable<String, Double> dailyNutritionTable) {

		StringBuilder sb = new StringBuilder();

		Set<String> keysOne = nutritionTableOne.keySet();
		double metric_serving_amount_One = -1.0;
		for (String key: keysOne) {
			if (key.contains("Serving: ")) {
				metric_serving_amount_One = nutritionTableOne.get(key);
				break;
			}
		}

		Set<String> keysTwo = nutritionTableTwo.keySet();
		double metric_serving_amount_Two = -1.0;
		for (String key: keysTwo) {
			if (key.contains("Serving: ")) {
				metric_serving_amount_Two = nutritionTableTwo.get(key);
				break;
			}
		}

		sb.append("Food1: ");
		if (metric_serving_amount_One == -1.0) {
			if (proportionOne == 1.0) {
				sb.append("per serving\n");
			}
			else {
				sb.append(df1.format(proportionOne)+" servings\n");
			}
		}
		else {
			if (proportionOne == 1.0) {
				sb.append("per serving: ("+df.format(metric_serving_amount_One*proportionOne)+"g)\n");
			}
			else {
				sb.append(df1.format(proportionOne)+" servings ("+df.format(metric_serving_amount_One*proportionOne)+"g)\n");
			}
		}

		sb.append("Food2: ");
		if (metric_serving_amount_Two == -1.0) {
			if (proportionTwo == 1.0) {
				sb.append("per serving\n");
			}
			else {
				sb.append(df1.format(proportionTwo)+" servings\n");
			}
		}
		else {
			if (proportionTwo == 1.0) {
				sb.append("per serving: ("+df.format(metric_serving_amount_Two*proportionTwo)+"g)\n");
			}
			else {
				sb.append(df1.format(proportionTwo)+" servings ("+df.format(metric_serving_amount_Two*proportionTwo)+"g)\n");
			}
		}

		if (dailyNutritionTable == null) {
			sb.append("Calories: "+df.format(nutritionTableOne.get("Calories")*proportionOne+nutritionTableTwo.get("Calories")*proportionTwo)+"kcal\n");
			sb.append("Carbohydrate: "+df.format(nutritionTableOne.get("Carbohydrate")*proportionOne+nutritionTableTwo.get("Carbohydrate")*proportionTwo)+"g\n");
			sb.append("Protein: "+df.format(nutritionTableOne.get("Protein")*proportionOne+nutritionTableTwo.get("Protein")*proportionTwo)+"g\n");
			sb.append("Fat: "+df.format(nutritionTableOne.get("Fat")*proportionOne+nutritionTableTwo.get("Fat")*proportionTwo)+"g\n");

			double sum = 0.0;
			if (nutritionTableOne.containsKey("Saturated Fat")) {
				sum += nutritionTableOne.get("Saturated Fat")*proportionOne;
			}
			if (nutritionTableTwo.containsKey("Saturated Fat")) {
				sum += nutritionTableTwo.get("Saturated Fat")*proportionTwo;
			}
			if (sum != 0.0) {
				sb.append("Saturated Fat: "+df.format(sum)+"g\n");
			}

			sum = 0.0;
			if (nutritionTableOne.containsKey("Polyunsaturated Fat")) {
				sum += nutritionTableOne.get("Polyunsaturated Fat")*proportionOne;
			}
			if (nutritionTableTwo.containsKey("Polyunsaturated Fat")) {
				sum += nutritionTableTwo.get("Polyunsaturated Fat")*proportionTwo;
			}
			if (sum != 0.0) {
				sb.append("Polyunsaturated Fat: "+df.format(sum)+"g\n");
			}

			sum = 0.0;
			if (nutritionTableOne.containsKey("Monounsaturated Fat")) {
				sum += nutritionTableOne.get("Monounsaturated Fat")*proportionOne;
			}
			if (nutritionTableTwo.containsKey("Monounsaturated Fat")) {
				sum += nutritionTableTwo.get("Monounsaturated Fat")*proportionTwo;
			}
			if (sum != 0.0) {
				sb.append("Monounsaturated Fat: "+df.format(sum)+"g\n");
			}

			sum = 0.0;
			if (nutritionTableOne.containsKey("Trans Fat")) {
				sum += nutritionTableOne.get("Trans Fat")*proportionOne;
			}
			if (nutritionTableTwo.containsKey("Trans Fat")) {
				sum += nutritionTableTwo.get("Trans Fat")*proportionTwo;
			}
			if (sum != 0.0) {
				sb.append("Trans Fat: "+df.format(sum)+"g\n");
			}

			sum = 0.0;
			if (nutritionTableOne.containsKey("Cholesterol")) {
				sum += nutritionTableOne.get("Cholesterol")*proportionOne;
			}
			if (nutritionTableTwo.containsKey("Cholesterol")) {
				sum += nutritionTableTwo.get("Cholesterol")*proportionTwo;
			}
			if (sum != 0.0) {
				sb.append("Cholesterol: "+df.format(sum)+"mg\n");
			}

			sum = 0.0;
			if (nutritionTableOne.containsKey("Sodium")) {
				sum += nutritionTableOne.get("Sodium")*proportionOne;
			}
			if (nutritionTableTwo.containsKey("Sodium")) {
				sum += nutritionTableTwo.get("Sodium")*proportionTwo;
			}
			if (sum != 0.0) {
				sb.append("Sodium: "+df.format(sum)+"g\n");
			}

			sum = 0.0;
			if (nutritionTableOne.containsKey("Potassium")) {
				sum += nutritionTableOne.get("Potassium")*proportionOne;
			}
			if (nutritionTableTwo.containsKey("Potassium")) {
				sum += nutritionTableTwo.get("Potassium")*proportionTwo;
			}
			if (sum != 0.0) {
				sb.append("Potassium: "+df.format(sum)+"g\n");
			}

			sum = 0.0;
			if (nutritionTableOne.containsKey("Fiber")) {
				sum += nutritionTableOne.get("Fiber")*proportionOne;
			}
			if (nutritionTableTwo.containsKey("Fiber")) {
				sum += nutritionTableTwo.get("Fiber")*proportionTwo;
			}
			if (sum != 0.0) {
				sb.append("Fiber: "+df.format(sum)+"g\n");
			}

			sum = 0.0;
			if (nutritionTableOne.containsKey("Sugar")) {
				sum += nutritionTableOne.get("Sugar")*proportionOne;
			}
			if (nutritionTableTwo.containsKey("Sugar")) {
				sum += nutritionTableTwo.get("Sugar")*proportionTwo;
			}
			if (sum != 0.0) {
				sb.append("Sugar: "+df.format(sum)+"g\n");
			}

			sum = 0.0;
			if (nutritionTableOne.containsKey("Vitamin A")) {
				sum += nutritionTableOne.get("Vitamin A")*proportionOne;
			}
			if (nutritionTableTwo.containsKey("Vitamin A")) {
				sum += nutritionTableTwo.get("Vitamin A")*proportionTwo;
			}
			if (sum != 0.0) {
				sb.append("Vitamin A: "+df.format(sum)+"g\n");
			}

			sum = 0.0;
			if (nutritionTableOne.containsKey("Vitamin C")) {
				sum += nutritionTableOne.get("Vitamin C")*proportionOne;
			}
			if (nutritionTableTwo.containsKey("Vitamin C")) {
				sum += nutritionTableTwo.get("Vitamin C")*proportionTwo;
			}
			if (sum != 0.0) {
				sb.append("Vitamin C: "+df.format(sum)+"g\n");
			}

			sum = 0.0;
			if (nutritionTableOne.containsKey("Calcium")) {
				sum += nutritionTableOne.get("Calcium")*proportionOne;
			}
			if (nutritionTableTwo.containsKey("Calcium")) {
				sum += nutritionTableTwo.get("Calcium")*proportionTwo;
			}
			if (sum != 0.0) {
				sb.append("Calcium: "+df.format(sum)+"g\n");
			}

			sum = 0.0;
			if (nutritionTableOne.containsKey("Iron")) {
				sum += nutritionTableOne.get("Iron")*proportionOne;
			}
			if (nutritionTableTwo.containsKey("Iron")) {
				sum += nutritionTableTwo.get("Iron")*proportionTwo;
			}
			if (sum != 0.0) {
				sb.append("Iron: "+df.format(sum)+"g\n");
			}
		}
		else {

			sb.append("Calories: "+df.format(nutritionTableOne.get("Calories")*proportionOne+nutritionTableTwo.get("Calories")*proportionTwo)+"kcal ("+df.format((nutritionTableOne.get("Calories")*proportionOne+nutritionTableTwo.get("Calories")*proportionTwo)/dailyNutritionTable.get("DailyCalorie")*100)+"%)\n");
			sb.append("Carbohydrate: "+df.format(nutritionTableOne.get("Carbohydrate")*proportionOne+nutritionTableTwo.get("Carbohydrate")*proportionTwo)+"g ("+df.format((nutritionTableOne.get("Carbohydrate")*proportionOne+nutritionTableTwo.get("Carbohydrate")*proportionTwo)/dailyNutritionTable.get("DailyCarbohydrate")*100)+"%)\n");
			sb.append("Protein: "+df.format(nutritionTableOne.get("Protein")*proportionOne+nutritionTableTwo.get("Protein")*proportionTwo)+"g ("+df.format((nutritionTableOne.get("Protein")*proportionOne+nutritionTableTwo.get("Protein")*proportionTwo)/dailyNutritionTable.get("DailyProtein")*100)+"%)\n");
			sb.append("Fat: "+df.format(nutritionTableOne.get("Fat")*proportionOne+nutritionTableTwo.get("Fat")*proportionTwo)+"g\n");

			double sum = 0.0;
			if (nutritionTableOne.containsKey("Saturated Fat")) {
				sum += nutritionTableOne.get("Saturated Fat")*proportionOne;
			}
			if (nutritionTableTwo.containsKey("Saturated Fat")) {
				sum += nutritionTableTwo.get("Saturated Fat")*proportionTwo;
			}
			if (sum != 0.0) {
				sb.append("Saturated Fat: "+df.format(sum)+"g\n");
			}

			sum = 0.0;
			if (nutritionTableOne.containsKey("Polyunsaturated Fat")) {
				sum += nutritionTableOne.get("Polyunsaturated Fat")*proportionOne;
			}
			if (nutritionTableTwo.containsKey("Polyunsaturated Fat")) {
				sum += nutritionTableTwo.get("Polyunsaturated Fat")*proportionTwo;
			}
			if (sum != 0.0) {
				sb.append("Polyunsaturated Fat: "+df.format(sum)+"g\n");
			}

			sum = 0.0;
			if (nutritionTableOne.containsKey("Monounsaturated Fat")) {
				sum += nutritionTableOne.get("Monounsaturated Fat")*proportionOne;
			}
			if (nutritionTableTwo.containsKey("Monounsaturated Fat")) {
				sum += nutritionTableTwo.get("Monounsaturated Fat")*proportionTwo;
			}
			if (sum != 0.0) {
				sb.append("Monounsaturated Fat: "+df.format(sum)+"g\n");
			}

			sum = 0.0;
			if (nutritionTableOne.containsKey("Trans Fat")) {
				sum += nutritionTableOne.get("Trans Fat")*proportionOne;
			}
			if (nutritionTableTwo.containsKey("Trans Fat")) {
				sum += nutritionTableTwo.get("Trans Fat")*proportionTwo;
			}
			if (sum != 0.0) {
				sb.append("Trans Fat: "+df.format(sum)+"g\n");
			}

			sum = 0.0;
			if (nutritionTableOne.containsKey("Cholesterol")) {
				sum += nutritionTableOne.get("Cholesterol")*proportionOne;
			}
			if (nutritionTableTwo.containsKey("Cholesterol")) {
				sum += nutritionTableTwo.get("Cholesterol")*proportionTwo;
			}
			if (sum != 0.0) {
				sb.append("Cholesterol: "+df.format(sum)+"mg\n");
			}

			sum = 0.0;
			if (nutritionTableOne.containsKey("Sodium")) {
				sum += nutritionTableOne.get("Sodium")*proportionOne;
			}
			if (nutritionTableTwo.containsKey("Sodium")) {
				sum += nutritionTableTwo.get("Sodium")*proportionTwo;
			}
			if (sum != 0.0) {
				sb.append("Sodium: "+df.format(sum)+"mg ("+df.format(sum/dailyNutritionTable.get("DailySodium")*100)+"%)\n");
			}

			sum = 0.0;
			if (nutritionTableOne.containsKey("Potassium")) {
				sum += nutritionTableOne.get("Potassium")*proportionOne;
			}
			if (nutritionTableTwo.containsKey("Potassium")) {
				sum += nutritionTableTwo.get("Potassium")*proportionTwo;
			}
			if (sum != 0.0) {
				sb.append("Potassium: "+df.format(sum)+"mg ("+df.format(sum/dailyNutritionTable.get("DailyPotassium")*100)+"%)\n");
			}

			sum = 0.0;
			if (nutritionTableOne.containsKey("Fiber")) {
				sum += nutritionTableOne.get("Fiber")*proportionOne;
			}
			if (nutritionTableTwo.containsKey("Fiber")) {
				sum += nutritionTableTwo.get("Fiber")*proportionTwo;
			}
			if (sum != 0.0) {
				sb.append("Fiber: "+df.format(sum)+"g ("+df.format(sum/dailyNutritionTable.get("DailyFiber")*100)+"%)\n");
			}

			sum = 0.0;
			if (nutritionTableOne.containsKey("Sugar")) {
				sum += nutritionTableOne.get("Sugar")*proportionOne;
			}
			if (nutritionTableTwo.containsKey("Sugar")) {
				sum += nutritionTableTwo.get("Sugar")*proportionTwo;
			}
			if (sum != 0.0) {
				sb.append("Sugar: "+df.format(sum)+"g\n");
			}

			sum = 0.0;
			if (nutritionTableOne.containsKey("Vitamin A")) {
				sum += nutritionTableOne.get("Vitamin A")*proportionOne;
			}
			if (nutritionTableTwo.containsKey("Vitamin A")) {
				sum += nutritionTableTwo.get("Vitamin A")*proportionTwo;
			}
			if (sum != 0.0) {
				sb.append("Vitamin A: "+df.format(sum)+"mcg ("+df.format(sum/dailyNutritionTable.get("DailyVitaminA")*100)+"%)\n");
			}

			sum = 0.0;
			if (nutritionTableOne.containsKey("Vitamin C")) {
				sum += nutritionTableOne.get("Vitamin C")*proportionOne;
			}
			if (nutritionTableTwo.containsKey("Vitamin C")) {
				sum += nutritionTableTwo.get("Vitamin C")*proportionTwo;
			}
			if (sum != 0.0) {
				sb.append("Vitamin C: "+df.format(sum)+"mg ("+df.format(sum/dailyNutritionTable.get("DailyVitaminC")*100)+"%)\n");
			}

			sum = 0.0;
			if (nutritionTableOne.containsKey("Calcium")) {
				sum += nutritionTableOne.get("Calcium")*proportionOne;
			}
			if (nutritionTableTwo.containsKey("Calcium")) {
				sum += nutritionTableTwo.get("Calcium")*proportionTwo;
			}
			if (sum != 0.0) {
				sb.append("Calcium: "+df.format(sum)+"mg ("+df.format(sum/dailyNutritionTable.get("DailyCalcium")*100)+"%)\n");
			}

			sum = 0.0;
			if (nutritionTableOne.containsKey("Iron")) {
				sum += nutritionTableOne.get("Iron")*proportionOne;
			}
			if (nutritionTableTwo.containsKey("Iron")) {
				sum += nutritionTableTwo.get("Iron")*proportionTwo;
			}
			if (sum != 0.0) {
				sb.append("Iron: "+df.format(sum)+"mg ("+df.format(sum/dailyNutritionTable.get("DailyIron")*100)+"%)\n");
			}
		}

		return sb.toString();
	}
}