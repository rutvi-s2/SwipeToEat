package com.example.swipetoeat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.content.Intent
import android.util.Log
import com.example.swipetoeat.data.DataSource
import com.example.swipetoeat.databinding.ActivityMainBinding
import com.example.swipetoeat.model.Restaurant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//SwipeToEat
private const val TAG = "MainActivity"
private const val BASE_URL = "https://api.yelp.com/v3/"
private const val API_KEY = "DaqKYV7ZnhMBzLmhY_foH30uyNlsU8Kg2XyUGi0Gb5C5vOVBDbzq71AiYaOPmnSKBUi60QBqr_kiHvZPjrp6dPxQ87bIghKoZeWtCtZongAIBnFUUf1KM0GvaZZkY3Yx"
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val restaurants: MutableList<YelpRestaurant> = DataSource.restaurants

        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
        val yelpService = retrofit.create(YelpService::class.java)
        yelpService.searchRestaurants("Bearer $API_KEY","indpak", "Austin").enqueue(object : Callback<YelpSearchResult> {
            override fun onResponse(call: Call<YelpSearchResult>, response: Response<YelpSearchResult>) {
                Log.i(TAG, "onResponse $response")
                val body = response.body()
                if (body == null) {
                    Log.w(TAG, "Did not receive valid response body from Yelp API ... exit")
                    return
                }
                restaurants.addAll(body.restaurants)
                DataSource.restaurants = restaurants
            }

            override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                Log.i(TAG, "onFailure $t")
            }
        })
        // Moves the user to the second page to start swiping
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationBar.setOnItemSelectedListener {
            when (it.itemId) {
                // Takes user to the home page
                R.id.home -> {
                    startActivity(Intent(this,MainActivity::class.java))
                }
                R.id.swipe -> {
                        val intent = Intent(this,SwipeActivity::class.java)
                        startActivity(intent)
                }
                R.id.restaurants -> {
                    startActivity(Intent(this,FindRestaurantActivity::class.java))
                }
                else -> {
                }
            }
            true
        }
    }


}