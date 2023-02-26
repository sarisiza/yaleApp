package com.example.theyelpapp.presentationlayer.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.theyelpapp.R
import com.example.theyelpapp.databinding.FragmentRestaurantsListBinding
import com.example.theyelpapp.presentationlayer.adapters.RestaurantAdapter
import com.example.theyelpapp.utils.UIState
import com.example.theyelpapp.utils.ViewIntents

class FavoriteFragment : BaseFragment() {

    private val binding by lazy {
        FragmentRestaurantsListBinding.inflate(layoutInflater)
    }

    private val favoriteAdapter by lazy {
        RestaurantAdapter{
            yelpViewModel.selectedRestaurant = it
            findNavController().navigate(R.id.action_frag_favorite_to_frag_det)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.tvListTitle.text = "Favorite Restaurants"

        binding.rvRestaurantsList.apply {
            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                true
            )
        }

        updateList()

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        yelpViewModel.fragmentState.observe(viewLifecycleOwner){
            if(!it){
                yelpViewModel.getIntentView(ViewIntents.GET_FAVORITES)
            }
        }
    }

    private fun updateList(){

        yelpViewModel.favoriteRestaurants.observe(viewLifecycleOwner){state ->
            when(state){
                is UIState.ERROR -> {
                    showError(state.e.localizedMessage){
                        yelpViewModel.getIntentView(ViewIntents.GET_FAVORITES)
                    }
                }
                UIState.LOADING -> {}
                is UIState.SUCCESS -> {
                    favoriteAdapter.updateRestaurants(state.response)
                }
            }
        }

    }


}