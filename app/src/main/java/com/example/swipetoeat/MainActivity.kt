package com.example.swipetoeat

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.swipetoeat.data.DataSource
import com.example.swipetoeat.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*


//SwipeToEat
private const val TAG = "MainActivity"
private const val BASE_URL = "https://api.yelp.com/v3/"
private const val API_KEY = "u0xY7xJPFNwzMdqfDLljz3N1pbhesJ7WEFt8exp9A0-G8mMDEj2DJjCY6u4RWdly7zs1GbYiJ4oaIfjgOAKdSyC0qhw_zexcKTp1hCaaAfhLiE_tuRr2ioPmEfliY3Yx"
class MainActivity : AppCompatActivity()  {
    private lateinit var binding : ActivityMainBinding
    var spinnerLabel : String = ""
    var chosenCuisine : String = ""
    var timeInput : Int = (System.currentTimeMillis()/1000).toInt()
    var timeInputStr : String = ""
    @RequiresApi(Build.VERSION_CODES.N)
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
        val cuisineAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
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


        binding.desiredCuisineSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // first get the cuisine that the user selected
                chosenCuisine = parent?.getItemAtPosition(position).toString()
                // now set it to the alias so we can send it in the YELP request
                spinnerLabel =
                    DataSource.cuisinesWithAlias.find { it.title == chosenCuisine }?.alias ?: "no alias found"
            }

        }


        fun yelpAPIForRestaurants() {
            val restaurants: MutableList<YelpRestaurant> = DataSource.restaurants

            val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
            val yelpService = retrofit.create(YelpService::class.java)
            Log.d("spinnerLabel", spinnerLabel)
            Log.d("time input", timeInput.toString())
            yelpService.searchRestaurants("Bearer $API_KEY",timeInput, spinnerLabel, binding.location.text.toString()).enqueue(object : Callback<YelpSearchResult> {
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
        }

        binding.startSwiping.setOnClickListener {
            if (binding.location.text.isEmpty()) {
                val text = "Please enter information."
                val duration = Toast.LENGTH_SHORT

                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            } else {
                if (spinnerLabel.isEmpty()) {
                    val text = "You did not enter a cuisine. All will be included in search."
                    val duration = Toast.LENGTH_SHORT

                    val toast = Toast.makeText(applicationContext, text, duration)
                    toast.show()
                }
                // intent to go to start swiping page
                GlobalScope.launch() {
                    // function call to retrieve list of restaurants from yelp
                    yelpAPIForRestaurants()
                    delay(1000L)
                    val intent = Intent(this@MainActivity, SwipeActivity::class.java)
                    intent.putExtra("chosen cuisine", chosenCuisine)
                    startActivity(intent)


                }
            }
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
                    // spinnerLabel.isEmpty() || binding.location.text.isEmpty()
                    if (DataSource.restaurants.isEmpty()) {
                        binding.bottomNavigationBar.selectedItemId = R.id.home
                        val text = "Please enter information and click on start swiping."
                        val duration = Toast.LENGTH_SHORT

                        val toast = Toast.makeText(applicationContext, text, duration)
                        toast.show()
                    } else {
                        val intent = Intent(this,SwipeActivity::class.java)
                        startActivity(intent)
                    }
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
        val selectTimeButton = findViewById<Button>(R.id.desiredTimeBtn)
        val formatter = SimpleDateFormat("MMM dd yyyy", Locale.US)
        val timeFormatter = SimpleDateFormat("hh:mm a", Locale.US)
        selectTimeButton.setOnClickListener{
            val now = Calendar.getInstance()
            var date : String = ""
            var time : String = ""
            val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)
                time = timeFormatter.format(selectedTime.time)
                timeInputStr = if(time.substring(time.length- 2, time.length-1) == "P"){
                    date + " " + (Calendar.HOUR_OF_DAY + 12).toString() + ":" + Calendar.MINUTE + ":00.000 UTC"
                } else{
                    date + " " + (Calendar.HOUR_OF_DAY).toString() + ":" + Calendar.MINUTE + ":00.000 UTC"
                }

                val df = SimpleDateFormat("MMM dd yyyy hh:mm:ss.SSS zzz")
                var my_dt : Date = df.parse(timeInputStr)
                timeInput = (my_dt.getTime() / 1000).toInt()

                Log.d("my time", timeInput.toString())
            },
                now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), false)
            timePicker.show()

            val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{view, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, month)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                date = formatter.format(selectedDate.time)
            },
                now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH))
            datePicker.show()

        }
    }


}