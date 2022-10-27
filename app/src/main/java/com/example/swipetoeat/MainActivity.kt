package com.example.swipetoeat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.content.Intent
import com.example.swipetoeat.databinding.ActivityMainBinding

//SwipeToEat
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
    }


}