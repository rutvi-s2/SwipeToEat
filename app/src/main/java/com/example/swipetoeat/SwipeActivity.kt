package com.example.swipetoeat

import com.yalantis.library.Koloda
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.ArrayList
import android.widget.ImageButton
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.swipetoeat.data.DataSource
import com.example.swipetoeat.databinding.ActivitySwipeBinding
import com.example.swipetoeat.model.Restaurant


class SwipeActivity : AppCompatActivity() {
    private lateinit var adapter: SwipeAdapter
    private lateinit var list: List<Int>
    private lateinit var koloda: Koloda
//    lateinit var imageResourceBitmap: Bitmap
    private lateinit var binding : ActivitySwipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe)
        binding = ActivitySwipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        koloda = findViewById(R.id.koloda)

        list = ArrayList()
        adapter = SwipeAdapter(this, list)
        koloda.adapter = adapter

//        imageResourceBitmap = BitmapFactory.decodeResource(resources, R.drawable.casa_de_mariscos_enchiladas);

        // Moves the user to back to the home page
//        val homePage = findViewById<ImageButton>(R.id.home_button)
//        homePage.setOnClickListener {
//            val intent = Intent(this,MainActivity::class.java)
//            startActivity(intent)
//        }



        // Intent takes user to the find restaurants page once they are done swiping
        val restaurantsPage = findViewById<ImageButton>(R.id.done_swiping_button)
        restaurantsPage.setOnClickListener {
            // temp values to test RestaurantCardAdapter
            // Adds restaurant user has swiped right on to the list
//            val restaurants: MutableList<Restaurant> = DataSource.restaurants
//            restaurants.add(
//                Restaurant(
//                    imageResourceBitmap,
//                    "Casa de Mariscos",
//                    "3 miles",
//                    "10:00am - 5:00pm"
////                    editText.text.toString(),
////                    reviewText.text.toString(),
////                    ratingNumber
//                )
//            )
            val intent = Intent(this, FindRestaurantActivity::class.java)
            startActivity(intent)
        }

        // Navigation bar
//        binding = ActivitySwipeBinding.inflate(layoutInflater)
//        binding.bottomNavigationBar.setOnItemSelectedListener {
//            when (it.itemId) {
//                // Takes user to the home page
//                R.id.home -> {
//                    // val homePage = Intent(this,MainActivity::class.java)
//                    startActivity(Intent(this,MainActivity::class.java))
//                }
//                R.id.swipe -> {
//                    // val swipePage = Intent(this,SwipeActivity::class.java)
//                    startActivity(Intent(this,SwipeActivity::class.java))
//                }
//                R.id.restaurants -> {
//
//                    startActivity(Intent(this,FindRestaurantActivity::class.java))
//                }
//                else -> {
//                }
//            }
//            true
//        }
    }

}