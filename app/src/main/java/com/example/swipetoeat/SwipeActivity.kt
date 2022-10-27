package com.example.swipetoeat

import com.example.swipetoeat.SwipeAdapter
import com.yalantis.library.Koloda
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.swipetoeat.R
import java.util.ArrayList

class SwipeActivity : AppCompatActivity() {
    private lateinit var adapter: SwipeAdapter
    private lateinit var list: List<Int>
    private lateinit var koloda: Koloda

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe)

        koloda = findViewById(R.id.koloda)

        list = ArrayList()
        adapter = SwipeAdapter(this, list)
        koloda.adapter = adapter
    }

}