package com.example.swipetoeat

import com.example.swipetoeat.SwipeAdapter
import com.yalantis.library.Koloda
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.swipetoeat.R
import java.util.ArrayList
import android.widget.ImageButton
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.swipetoeat.data.DataSource
import com.example.swipetoeat.model.Restaurant

class SwipeActivity : AppCompatActivity() {
    private lateinit var adapter: SwipeAdapter
    private lateinit var list: List<Int>
    private lateinit var koloda: Koloda
    lateinit var imageResourceBitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe)

        koloda = findViewById(R.id.koloda)

        list = ArrayList()
        adapter = SwipeAdapter(this, list)
        koloda.adapter = adapter

        imageResourceBitmap = BitmapFactory.decodeResource(resources, R.drawable.casa_de_mariscos_enchiladas);

        // Moves the user to back to the home page
        val homePage = findViewById<ImageButton>(R.id.home_button)
        homePage.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        // Intent takes user to the find restaurants page once they are done swiping
        val restaurantsPage = findViewById<ImageButton>(R.id.done_swiping_button)
        restaurantsPage.setOnClickListener {
            // temp values to test RestaurantCardAdapter
            // Adds restaurant user has swiped right on to the list
            val restaurants: MutableList<Restaurant> = DataSource.restaurants
            restaurants.add(
                Restaurant(
                    imageResourceBitmap,
                    "Casa de Mariscos",
                    "3 miles",
                    "5:00 pm"
//                    editText.text.toString(),
//                    reviewText.text.toString(),
//                    ratingNumber
                )
            )
            val intent = Intent(this, FindRestaurantActivity::class.java)
            startActivity(intent)
        }
    }

}