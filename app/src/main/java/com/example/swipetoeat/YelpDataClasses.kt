package com.example.swipetoeat

import com.google.gson.annotations.SerializedName

data class YelpSearchResult(
    @SerializedName("businesses") val restaurants: List<YelpRestaurant>
//    @SerializedName("categories") val cuisines: List<YelpCategory>
)

data class YelpSearchResultCuisine(
    @SerializedName("categories") val cuisines: List<YelpCategory>
) {
    fun populateCuisines() : MutableList<String> {
        var restaurantCuisines: MutableList<String> = mutableListOf()
        for (c in cuisines) {
            if (c.parentAliases.isNotEmpty() && c.parentAliases[0] == "restaurants") {
                restaurantCuisines.add(c.title)
            }
        }
        return restaurantCuisines
    }
}

data class YelpRestaurant(
    //no need to specify serialized name if name of parameter in the object exactly matches var name in Kotlin
    val name: String,
    val rating: Double,
    val price : String,
    @SerializedName("review_count") val numReviews: Int,
    @SerializedName("distance") val distanceInMeters: Double,
    @SerializedName("image_url") val imageUrl: String,
    val categories: List<YelpCategory>,
    val location : YelpLocation
) {
    fun displayDistance() : String{
        val milesPerMeter = 0.000621371
        val distanceInMiles = "%.2f".format(distanceInMeters * milesPerMeter)
        return "$distanceInMiles miles"
    }
}

data class YelpCategory(
    val title:String,
    @SerializedName("parent_aliases") val parentAliases: List<String>
)


data class YelpLocation(
    @SerializedName("address1") val address : String,
    @SerializedName("city") val city : String
)