package com.dongwookchung.nutritioncalculator;

import android.os.AsyncTask;

import com.clarifai.api.ClarifaiClient;
import com.clarifai.api.RecognitionRequest;
import com.clarifai.api.RecognitionResult;
import com.clarifai.api.Tag;
import com.clarifai.api.exception.ClarifaiException;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Clarifai extends AsyncTask<String, Void, String> {
	private static final String CLIENT_ID = "26xhqW5FMimQEh8q7sXvxEC1F-auofA5kFy3z_eh";
	private static final String CLIENT_SECRET = "HyWKXukT5ZbwmEdiqBN62vLKyWoFql8Psv7LBZ25";

	@Override
	protected String doInBackground(String... params) {
		try {
			ClarifaiClient clarifai = new ClarifaiClient(CLIENT_ID, CLIENT_SECRET);
			List<RecognitionResult> results =
					clarifai.recognize(new RecognitionRequest(new File(params[0])));

			List<String> tagList = new ArrayList<String>();

			for (Tag tag : results.get(0).getTags()) {
				tagList.add(tag.getName().toString());
				System.out.println(tag.getName() + ": " + tag.getProbability());
				//System.out.println(tag.getName().toString());
			}
			return getFood(tagList);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getFood(List<String> tagList) {

		List<String> foodList = new ArrayList<>(
				Arrays.asList("french fries", "sandwich", "burger", "hotdog", "pizza",
						"croissant", "risotto", "doughnut", "pudding",
						"salad",
						"cupcake", "cake", "sorbet", "waffle",
						"steak", "tenderloin", "sirloin", "fillet", "beefsteak",
						"spaghetti", "macaroni", "pasta",
						"beef", "pork", "chicken",
						"tomato", "cookie", "pie", "candy",
						"sorbet",
						"taco"));

		List<String> modifierList = new ArrayList<>(
				Arrays.asList("chocolate", "strawberry"));

		System.out.println(tagList);

		for (String tag: tagList) {
			if (foodList.contains(tag)) {
				if (tag.equals("cake") || tag.equals("cupcake")) {
					for (String tempTag: tagList) {
						if (modifierList.contains(tempTag)) {
							return tempTag+" cake";
						}
					}
					return "cake";
				}
				return tag;
			}
		}
		return "No match";
	}
}