package com.example.swipetoeat

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.swipetoeat.adapter.RestaurantCardAdapter
import com.example.swipetoeat.data.DataSource
import com.example.swipetoeat.databinding.ActivityFindRestaurantBinding
import java.util.*
import kotlin.random.Random


private const val BASE_URL = "https://www.yelp.com/biz/"
class FindRestaurantActivity : AppCompatActivity(), RestaurantCardAdapter.OnItemClickListener{
    private lateinit var binding: ActivityFindRestaurantBinding
    private val mWebsiteEditText: EditText? = null
    // Creates layout for find restaurant page and makes it scrollable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_restaurant)
        binding = ActivityFindRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationBar.selectedItemId = R.id.restaurants
        binding.bottomNavigationBar.setOnItemSelectedListener {
            when (it.itemId) {
                // Takes user to the home page
                R.id.home -> {
                    startActivity(Intent(this,MainActivity::class.java))
                }
                R.id.swipe -> {
                    startActivity(Intent(this,SwipeActivity::class.java))
                }
                R.id.restaurants -> {
                    startActivity(Intent(this,FindRestaurantActivity::class.java))
                }
                else -> {
                }
            }
            true
        }
        binding.gridRecyclerView.adapter = RestaurantCardAdapter(this, this)

        // Specify fixed size to improve performance
        binding.gridRecyclerView.setHasFixedSize(true)



        // TODO: when randomly generate button is clicked, we choose a restaurant for the user
        val randomlyGenerate = findViewById<Button>(R.id.surprise_me_button)
        randomlyGenerate.setOnClickListener {
//            Log.d("restaurntsinfind", DataSource.restaurants.toString())
            val seed = System.nanoTime()
            DataSource.restaurants.shuffle(Random(seed))
            val index: Int = Random.nextInt(DataSource.restaurants.size)
            val item: YelpRestaurant = DataSource.restaurants[index]

            val intent = Intent(this, SurpriseMeActivity::class.java)
            intent.putExtra("restaurantIndex", index)
            startActivity(intent)
        }

    }

    // when RestaurantCardAdapter is clicked, send the data over the intent and start the intent
    // for yelp screen
    override fun onItemClick(position: Int, restaurants: List<YelpRestaurant>) {
        val restaurant: YelpRestaurant = DataSource.restaurants[position]
        var restaurantName = restaurant.name
        var restaurantCity = restaurant.location.city
        restaurantName = restaurantName.replace("\\s".toRegex(), "-")
        restaurantCity = restaurantCity.replace("\\s".toRegex(), "-")
        try {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(BASE_URL.plus(restaurantName).plus("-").plus(restaurantCity)))
            startActivity(browserIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                this, "No application can handle this request."
                        + " Please install a webbrowser", Toast.LENGTH_LONG
            ).show()
            e.printStackTrace()
        }
//        val intent = Intent(this,YelpActivity::class.java)
//        intent.putExtra("restaurant_name", restaurants[position].name)
//        intent.putExtra("movie_review", movies[position].review)
//        intent.putExtra("movie_rating", movies[position].rating)
//        startActivity(intent)
    }

}