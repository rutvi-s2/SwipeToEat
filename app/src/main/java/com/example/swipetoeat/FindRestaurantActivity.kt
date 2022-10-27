package com.example.swipetoeat

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.swipetoeat.MainActivity
import com.example.swipetoeat.databinding.ActivityFindRestaurantBinding

import com.example.swipetoeat.adapter.RestaurantCardAdapter
import com.example.swipetoeat.model.Restaurant

class FindRestaurantActivity : AppCompatActivity(), RestaurantCardAdapter.OnItemClickListener{
    private lateinit var binding: ActivityFindRestaurantBinding
    // Creates layout for find restaurant page and makes it scrollable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.adapter = RestaurantCardAdapter(this)

        // Specify fixed size to improve performance
        binding.recyclerView.setHasFixedSize(true)
        // Intent takes user back to the add reviews page
//        val firstPage = binding.addMovieBtn
//        firstPage.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }

    }

    // when RestaurantCardAdapter is clicked, send the data over the intent and start the intent
    // for yelp screen
    override fun onItemClick(position: Int, restaurants: List<Restaurant>) {
        val intent = Intent(this,YelpActivity::class.java)
        intent.putExtra("restaurant_name", restaurants[position].name)
//        intent.putExtra("movie_review", movies[position].review)
//        intent.putExtra("movie_rating", movies[position].rating)
        startActivity(intent)
    }
}