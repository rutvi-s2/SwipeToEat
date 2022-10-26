package com.example.swipetoeat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.swipetoeat.R
import com.example.swipetoeat.data.DataSource.restaurants
import com.example.swipetoeat.model.Restaurant
import javax.sql.DataSource

class RestaurantCardAdapter (
    private val listener: OnItemClickListener
): RecyclerView.Adapter<RestaurantCardAdapter.RestaurantCardViewHolder>() {


    // initialize the data using the restaurants List found in DataSource
    private var restaurants: List<Restaurant> = com.example.swipetoeat.data.DataSource.restaurants
    /**
     * Initialize view elements
     */
    inner class RestaurantCardViewHolder(view: View?): RecyclerView.ViewHolder(view!!),
        View.OnClickListener{
        // declare and initialize all of the bunny list item UI components
        val restaurantImageView: ImageView = view!!.findViewById(R.id.restaurant_grid_image)
        val restaurantNameTextView: TextView = view!!.findViewById(R.id.restaurant_grid_name)

        // set up the onClick so restaurant card can be clickable in order to view YELP screen
        init {
            if (view != null) {
                view.setOnClickListener(this)
            }
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position, restaurants)
            }
        }
    }

    // declare the onItemClick to be initialized in MainActivity2.kt
    interface OnItemClickListener {
        fun onItemClick(position: Int, restaurants: List<Restaurant>)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantCardAdapter.RestaurantCardViewHolder {
        // determine which list item should be used and set layout accordingly
        // grid vs vertical/horizontal

        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.restaurant_grid_item, parent, false)
        // inflate layout and return
        return RestaurantCardViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int {
        // return size of bunnies data set
        return restaurants.size
    }

    override fun onBindViewHolder(holder: RestaurantCardAdapter.RestaurantCardViewHolder, position: Int) {
        //get the data at the current position
        val movie = restaurants[position]
        holder.restaurantImageView.setImageBitmap(movie.imageResourceBitmap)
        //set text for name and age
        holder.restaurantNameTextView.text = movie.name
    }

}