package com.example.swipetoeat

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.swipetoeat.data.DataSource
import com.example.swipetoeat.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//SwipeToEat
private const val TAG = "MainActivity"
private const val BASE_URL = "https://api.yelp.com/v3/"
private const val API_KEY = "u0xY7xJPFNwzMdqfDLljz3N1pbhesJ7WEFt8exp9A0-G8mMDEj2DJjCY6u4RWdly7zs1GbYiJ4oaIfjgOAKdSyC0qhw_zexcKTp1hCaaAfhLiE_tuRr2ioPmEfliY3Yx"
class MainActivity : AppCompatActivity()  {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val buttonToSwipePage = findViewById<Button>(R.id.start_swiping)
        buttonToSwipePage.setOnClickListener {
            val intent = Intent(this, SwipeActivity::class.java)
            startActivity(intent)
        }



        val cuisines: MutableList<String> = DataSource.cuisines

        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
        val yelpService = retrofit.create(YelpService::class.java)
        yelpService.searchCuisines("Bearer $API_KEY").enqueue(object : Callback<YelpSearchResultCuisine> {
            override fun onResponse(call: Call<YelpSearchResultCuisine>, response: Response<YelpSearchResultCuisine>) {
                Log.i(TAG, "onResponse $response")
                val body = response.body()
                if (body == null) {
                    Log.w(TAG, "Did not receive valid response body from Yelp API ... exit")
                    return
                }
                cuisines.addAll(body.populateCuisines())
                DataSource.cuisines = cuisines
                Log.d("cuisinesList", DataSource.cuisines.toString())
            }

            override fun onFailure(call: Call<YelpSearchResultCuisine>, t: Throwable) {
                Log.i(TAG, "onFailure $t")
            }
        })


        // Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<YelpCategory>() adapter_category = new ArrayAdapter<YelpCategory>(this, android.R.layout.simple_spinner_item, cuisines)
        var cuisineAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            DataSource.cuisines
        )
        cuisineAdapter.also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.desiredCuisineSpinner.adapter = adapter
        }


//        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//
//            }
//
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//
//            }
//
//        }


        binding.startSwiping.setOnClickListener {

            // TODO: this is the code that works that populates the restaurant screen. However, it needs to be moved to populate when user swipes right on card
            // TODO: the below needs to populate the swiping screen depending on user input
//            val restaurants: MutableList<YelpRestaurant> = DataSource.restaurantOptions
//
//            val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create()).build()
//            val yelpService = retrofit.create(YelpService::class.java)
//            yelpService.searchRestaurants("Bearer $API_KEY","indpak", "Austin").enqueue(object : Callback<YelpSearchResult> {
//                override fun onResponse(call: Call<YelpSearchResult>, response: Response<YelpSearchResult>) {
//                    Log.i(TAG, "onResponse $response")
//                    val body = response.body()
//                    if (body == null) {
//                        Log.w(TAG, "Did not receive valid response body from Yelp API ... exit")
//                        return
//                    }
//                    restaurants.addAll(body.restaurants)
//                    DataSource.restaurantOptions = restaurants
//                }
//
//                override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
//                    Log.i(TAG, "onFailure $t")
//                }
//            })


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


            // intent to go to start swiping page
            val intent = Intent(this, SwipeActivity::class.java)
            startActivity(intent)
        }



        // Moves the user to the second page to start swiping
        binding.bottomNavigationBar.selectedItemId = R.id.home
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