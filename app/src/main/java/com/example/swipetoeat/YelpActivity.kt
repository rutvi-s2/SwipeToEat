package com.example.swipetoeat

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.swipetoeat.model.Restaurant
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException


class YelpActivity : AppCompatActivity() {
    val TAG: String = YelpActivity::class.java.simpleName
    var restaurants: ArrayList<Restaurant> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yelp)
        val intent = intent
        val location = intent.getStringExtra("location")
        if (location != null) {
            getRestaurants(location)
        }

    }



    private fun getRestaurants(location: String) {
        val yelpService = YelpService()
        YelpService.findRestaurants(location, object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                try {
                    val jsonData = response.body().string()
                    Log.v(TAG, jsonData)
                    restaurants = yelpService.processResults(response)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        })
    }

}