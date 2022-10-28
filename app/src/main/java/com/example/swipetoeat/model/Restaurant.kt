package com.example.swipetoeat.model

import android.graphics.Bitmap
import android.graphics.drawable.Drawable

/**
 * A data class to represent the information presented in the restaurant card
 */
data class Restaurant(
    val imageResourceBitmap: Int,
//    val imageResourceBitmap: Bitmap,
    val name: String,
    val distance: String,
    val hours: String
)
