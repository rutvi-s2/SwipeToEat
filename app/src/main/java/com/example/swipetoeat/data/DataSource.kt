package com.example.swipetoeat.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.swipetoeat.R
import com.example.swipetoeat.YelpCategory
import com.example.swipetoeat.YelpRestaurant
import com.example.swipetoeat.model.Restaurant

/**
 * An object to generate a static list of movie reviews from user
 */
object DataSource {
//    var imageResourceBitmap: Bitmap = BitmapFactory.decodeResource(null, R.drawable.casa_de_mariscos_enchiladas);


    var restaurants: MutableList<YelpRestaurant> = mutableListOf(
//        Restaurant(
//            R.drawable.casa_de_mariscos_enchiladas,
//            "Casa de Marsicos",
//            "3 miles",
//            "10:00am - 5:00pm"
////                    editText.text.toString(),
////                    reviewText.text.toString(),
////                    ratingNumber
//        )
    )

    var cuisines: MutableList<String> = mutableListOf()
    var cuisinesWithAlias: MutableList<YelpCategory> = mutableListOf()

    var swipedRightRestaurants: MutableList<YelpRestaurant> = mutableListOf()
}