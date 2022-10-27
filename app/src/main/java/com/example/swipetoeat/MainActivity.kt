package com.example.swipetoeat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.content.Intent

//SwipeToEat
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Moves the user to the second page to start swiping
        val swipePage = findViewById<ImageButton>(R.id.start_swiping)
        swipePage.setOnClickListener {
            val intent = Intent(this,SwipeActivity::class.java)
            startActivity(intent)
        }
    }
}