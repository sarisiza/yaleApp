package com.example.theyelpapp.presentationlayer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.theyelpapp.R
import com.example.theyelpapp.databinding.RatingsHolderBinding
import com.example.theyelpapp.databinding.RestaurantHolderBinding
import com.example.theyelpapp.databinding.UserHolderBinding
import com.example.theyelpapp.datalayer.domain.Rating
import com.example.theyelpapp.datalayer.domain.Restaurant
import com.squareup.picasso.Picasso

class RestaurantAdapter(
    private val itemSet: MutableList<Restaurant> = mutableListOf(),
    private val onRestaurantClicked: (Restaurant) -> Unit
): RecyclerView.Adapter<RestaurantsViewHolder>() {

    fun updateRestaurants(newList: List<Restaurant>){

        if(itemSet != newList){
            itemSet.clear()
            itemSet.addAll(newList)
        }
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)  =
        RestaurantsViewHolder(
            RestaurantHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount() = itemSet.size

    override fun onBindViewHolder(holder: RestaurantsViewHolder, position: Int) {
        holder.restaurantsBind(itemSet[position],onRestaurantClicked)
    }

}

class RestaurantsViewHolder(
    private val binding: RestaurantHolderBinding
): RecyclerView.ViewHolder(binding.root){

    fun restaurantsBind(item: Restaurant, onRestaurantClicked: (Restaurant) -> Unit){
        binding.tvRestaurantName.text = item.name
        binding.tvPrice.text = item.price
        binding.rbRating.rating = item.rating.toFloat()
        binding.tvDistance.text = item.distance.toString()
        Picasso
            .get()
            .load(item.imgUrl)
            .placeholder(R.drawable.ic_image_search_24)
            .error(R.drawable.ic_broken_image_24)
            .into(binding.ivRestaurantImage)
        itemView.setOnClickListener {
            item.let(onRestaurantClicked)
        }
    }

}

