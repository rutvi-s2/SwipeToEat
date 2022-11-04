package com.example.swipetoeat;

import com.example.swipetoeat.model.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class YelpService {

    public static void findRestaurants(String location, Callback callback) {

        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.YELP_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.YELP_LOCATION_QUERY_PARAMETER, location);
        String url = urlBuilder.build().toString();

        Request request= new Request.Builder()
                .url(url)
                .header("Authorization", Constants.YELP_TOKEN)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Restaurant> processResults(Response response) {
        ArrayList<Restaurant> restaurants = new ArrayList<>();

        try {
            String jsonData = response.body().string();
            JSONObject yelpJSON = new JSONObject(jsonData);
            JSONArray businessesJSON = yelpJSON.getJSONArray("businesses");
            for (int i = 0; i < businessesJSON.length(); i++) {
                JSONObject restaurantJSON = businessesJSON.getJSONObject(i);
                String name = restaurantJSON.getString("name");
//                String website = restaurantJSON.getString("url");
//                double rating = restaurantJSON.getDouble("rating");

                String imageUrl = restaurantJSON.getString("image_url");


//                ArrayList<String> address = new ArrayList<>();
//                JSONArray addressJSON = restaurantJSON.getJSONObject("location")
//                        .getJSONArray("display_address");
//                for (int y = 0; y < addressJSON.length(); y++) {
//                    address.add(addressJSON.get(y).toString());
//                }

//                ArrayList<String> categories = new ArrayList<>();
//                JSONArray categoriesJSON = restaurantJSON.getJSONArray("categories");

//                for (int y = 0; y < categoriesJSON.length(); y++) {
//                    categories.add(categoriesJSON.getJSONObject(y).getString("title"));
//                }
                Restaurant restaurant = new Restaurant(5, name, "5 miles", "3:00pm", imageUrl);
                restaurants.add(restaurant);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return restaurants;
    }
}
