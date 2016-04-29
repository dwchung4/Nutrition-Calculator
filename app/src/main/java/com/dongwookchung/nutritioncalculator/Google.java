package com.dongwookchung.nutritioncalculator;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.AnnotateImageResponse;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;

public class Google extends AsyncTask<String, Void, String> {
    private static final String CLOUD_VISION_API_KEY = "AIzaSyAoBTdk04zwxJc1QX4bwM6qYcwfQQqSclg";

    @Override
    protected String doInBackground(String... params) {
        try {
            Drawable drawable = Drawable.createFromPath(params[0]);
            final Bitmap bitmap = scaleBitmapDown(((BitmapDrawable) drawable).getBitmap(), 1200);

            HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

            Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
            builder.setVisionRequestInitializer(new
                    VisionRequestInitializer(CLOUD_VISION_API_KEY));
            Vision vision = builder.build();

            BatchAnnotateImagesRequest batchAnnotateImagesRequest =
                    new BatchAnnotateImagesRequest();
            batchAnnotateImagesRequest.setRequests(new ArrayList<AnnotateImageRequest>() {{
                AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();

                // Add the image
                Image base64EncodedImage = new Image();
                // Convert the bitmap to a JPEG
                // Just in case it's a format that Android understands but Cloud Vision
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
                byte[] imageBytes = byteArrayOutputStream.toByteArray();

                // Base64 encode the JPEG
                base64EncodedImage.encodeContent(imageBytes);
                annotateImageRequest.setImage(base64EncodedImage);

                // add the features we want
                annotateImageRequest.setFeatures(new ArrayList<Feature>() {{
                    Feature labelDetection = new Feature();
                    labelDetection.setType("LABEL_DETECTION");
                    labelDetection.setMaxResults(10);
                    add(labelDetection);
                }});

                // Add the list of one thing to the request
                add(annotateImageRequest);
            }});

            Vision.Images.Annotate annotateRequest =
                    vision.images().annotate(batchAnnotateImagesRequest);
            // Due to a bug: requests to Vision API containing large images fail when GZipped.
            annotateRequest.setDisableGZipContent(true);

            BatchAnnotateImagesResponse response = annotateRequest.execute();

            return getFood(response);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Bitmap scaleBitmapDown(Bitmap bitmap, int maxDimension) {

        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;

        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }

    private String getFood(BatchAnnotateImagesResponse response) {
        List<String> foodList = new ArrayList<>(
                Arrays.asList("french fries", "sandwich", "burger", "hot dog", "pizza", "chili dog",
                        "cheeseburger", "veggie burger", "buffalo burger", "submarine sandwich",
                        "croissant", "rice", "risotto", "doughnut", "pudding",
                        "salad", "jackfruit", "muffin", "candy",
                        "cupcake", "cake",
                        "steak", "rib eye steak", "sirloin steak", "pork chop",
                        "quesadilla", "panini", "blt",
                        "spaghetti", "lo mein", "chow mein", "yakisoba", "mie goreng", "rice noodles", "capellini",
                        "korokke", "schnitzel", "tonkatsu",
                        "sushi",
                        "waffle", "ice cream", "gelato", "canapé",
                        "burrito"));

        List<String> modifierList = new ArrayList<>(
            Arrays.asList("chocolate", "strawberry"));

        List<EntityAnnotation> labels = response.getResponses().get(0).getLabelAnnotations();
        if (labels != null) {
            //System.out.println(labels);
            for (EntityAnnotation label: labels) {
                System.out.println(label.getDescription());
            }
            for (EntityAnnotation label : labels) {
                if (foodList.contains(label.getDescription())) {
                    if (label.getDescription().equals("cake") || label.getDescription().equals("cupcake")) {
                        for (EntityAnnotation tempLabel: labels) {
                            if (modifierList.contains(tempLabel.getDescription())) {
                                return tempLabel.getDescription()+" "+label.getDescription();
                            }
                        }
                        return "cake";
                    }
                    return label.getDescription();
                }
            }
        }
        return "No match";
    }
}
