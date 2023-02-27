package com.example.theyelpapp.presentationlayer.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.theyelpapp.R
import com.example.theyelpapp.databinding.RestaurantFragmentBinding
import com.example.theyelpapp.utils.ViewIntents
import com.squareup.picasso.Picasso

private const val TAG = "RestaurantFragment"
class RestaurantFragment : BaseFragment() {

    private val binding by lazy {
        RestaurantFragmentBinding.inflate(layoutInflater)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.tvRestaurantName.text = yelpViewModel.selectedRestaurant?.name
        binding.tvPrice.text = yelpViewModel.selectedRestaurant?.price
        binding.rbRating.rating = yelpViewModel.selectedRestaurant?.rating?.toFloat() ?: 1F
        binding.tvAddress.text = yelpViewModel.selectedRestaurant?.address
        binding.tvPhone.text = yelpViewModel.selectedRestaurant?.phone
        Picasso
            .get()
            .load(yelpViewModel.selectedRestaurant?.imgUrl)
            .placeholder(R.drawable.ic_image_search_24)
            .error(R.drawable.ic_broken_image_24)
            .into(binding.ivRestaurantImage)

        checkFavorite()

        binding.inBar.barDetailsBar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.frag_ratings -> {
                    findNavController().navigate(R.id.action_frag_det_to_frag_ratings2)
                    true
                }
                R.id.frag_det -> true
                else -> false
            }
        }

        binding.ibStarFav.setOnClickListener {
            yelpViewModel.getIntentView(ViewIntents.UPDATE_FAVORITE)
            checkFavorite()
        }

        return binding.root
    }

    private fun checkFavorite(){
        yelpViewModel.selectedRestaurant?.isFavorite?.let {
            if(it){
                Log.d(TAG, "checkFavorite: is filled")
                binding.ibStarFav.setImageResource(R.drawable.baseline_star_filled_24)
            } else{
                Log.d(TAG, "checkFavorite: is empty")
                binding.ibStarFav.setImageResource(R.drawable.baseline_star_empty_24)
            }
        } ?: binding.ibStarFav.setImageResource(R.drawable.baseline_star_empty_24)
    }

}