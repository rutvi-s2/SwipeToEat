package com.example.swipetoeat

import com.yalantis.library.Koloda
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.ArrayList
import android.widget.ImageButton
import android.widget.Button
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import com.example.swipetoeat.data.DataSource
import com.example.swipetoeat.databinding.ActivitySwipeBinding
import com.example.swipetoeat.model.Restaurant
import com.yalantis.library.KolodaListener
import android.graphics.Color
import android.media.AudioManager
import android.media.MediaPlayer
import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.Transformations.map
import com.example.swipetoeat.adapter.SwipeAdapter
import java.io.IOException


class SwipeActivity : AppCompatActivity() {
    private lateinit var adapter: SwipeAdapter
    private lateinit var list: MutableList<YelpRestaurant>
    private lateinit var koloda: Koloda
    var mediaPlayer : MediaPlayer? = null
    var countSwiped : Int = 0
    //    lateinit var imageResourceBitmap: Bitmap
    private lateinit var binding : ActivitySwipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe)
        binding = ActivitySwipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // set the cuisine the user chose to the text shown on the screen
        val extras = intent.extras
        val chosenCuisine: String = extras?.getString("chosen cuisine")!!
        var displayCuisine = ""
        if (chosenCuisine.isEmpty()) {
            displayCuisine = "Desired Cuisine: All"
        } else {
            displayCuisine = "Desired Cuisine: $chosenCuisine"
        }
        binding.desiredCuisine.text = displayCuisine

        binding.bottomNavigationBar.selectedItemId = R.id.swipe
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
                    if (DataSource.swipedRightRestaurants.isEmpty()) {
                        val text = "You have not swiped right on any meals yet!"
                        val duration = Toast.LENGTH_SHORT

                        val toast = Toast.makeText(applicationContext, text, duration)
                        toast.show()
                    } else {
                        startActivity(Intent(this,FindRestaurantActivity::class.java))
                    }
                }
                else -> {
                }
            }
            true
        }


        koloda = findViewById(R.id.koloda)

        list = (DataSource.restaurants).toCollection(mutableListOf())
        adapter = SwipeAdapter(this, list)
        koloda.adapter = adapter
        Log.d("the first length is", list.size.toString())


        koloda.kolodaListener = object : KolodaListener {

            override fun onCardSwipedLeft(position: Int) {
                val text = "Swiped Left!"
                val duration = Toast.LENGTH_SHORT
                playAudio()
                countSwiped++
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }

            override fun onCardSwipedRight(position: Int) {
                val text = "Swiped Right!"
                val duration = Toast.LENGTH_SHORT
                playAudio()
                countSwiped++
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()

                // get the restaurant swiped right on
                val restaurant: YelpRestaurant = list[position + 1]
                DataSource.swipedRightRestaurants.add(restaurant)
            }

        }

//        imageResourceBitmap = BitmapFactory.decodeResource(resources, R.drawable.casa_de_mariscos_enchiladas);

        // Intent takes user to the find restaurants page once they are done swiping
        val restaurantsPage = findViewById<Button>(R.id.done_swiping)
        restaurantsPage.setOnClickListener {
            var start = 0;
            while(start < countSwiped){
                list.removeFirst()
                start++;
            }

            val intent = Intent(this, FindRestaurantActivity::class.java)
            startActivity(intent)
        }

        val reset = findViewById<Button>(R.id.reset)
        reset.setOnClickListener{
            list = (DataSource.restaurants).toCollection(mutableListOf())
            adapter = SwipeAdapter(this, list)
            koloda.adapter = adapter

            Log.d("the second length is", list.size.toString())
        }

    }

    private fun playAudio(){
        val audioUrl = "https://www.fesliyanstudios.com/play-mp3/7756"
        mediaPlayer = MediaPlayer()
        mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
        try{
            mediaPlayer!!.setDataSource(audioUrl)
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()
        } catch(e: IOException){
            e.printStackTrace()
        }
    }

}