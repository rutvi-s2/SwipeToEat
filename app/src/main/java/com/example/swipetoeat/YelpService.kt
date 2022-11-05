package com.example.swipetoeat

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

public interface YelpService {
    @GET("businesses/search")
    fun searchRestaurants(
        @Header("Authorization") authHeader: String,
        @Query("categories") restaurantCategory: String,
        @Query("location") location:String) : Call<YelpSearchResult>

    @GET("categories")
    fun searchCuisines(
        @Header("Authorization") authHeader: String) : Call<YelpSearchResultCuisine>
//        @Path("alias") alias: String) : Call<YelpSearchResultCuisine>

}