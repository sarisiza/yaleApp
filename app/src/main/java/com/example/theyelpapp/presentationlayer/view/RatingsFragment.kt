package com.example.theyelpapp.presentationlayer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.theyelpapp.R
import com.example.theyelpapp.databinding.RatingsFragmentBinding
import com.example.theyelpapp.presentationlayer.adapters.RatingAdapter
import com.example.theyelpapp.utils.UIState
import com.example.theyelpapp.utils.ViewIntents

class RatingsFragment : BaseFragment() {

    val binding by lazy {
        RatingsFragmentBinding.inflate(layoutInflater)
    }

    val ratingsAdapter by lazy {
        RatingAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.rvRatingsList.apply {
            adapter = ratingsAdapter
            layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
        }

        binding.inBar.barDetailsBar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.frag_ratings -> true
                R.id.frag_det -> {
                    findNavController().navigate(R.id.action_frag_ratings_to_frag_det2)
                    true
                }
                else -> false
            }
        }

        yelpViewModel.restaurantRatings.observe(viewLifecycleOwner){state ->
            when(state){
                is UIState.ERROR -> {
                    showError(state.e.localizedMessage){
                        yelpViewModel.getIntentView(ViewIntents.RESTAURANT_RATINGS)
                    }
                }
                UIState.LOADING -> {}
                is UIState.SUCCESS -> {
                    ratingsAdapter.updateRatings(state.response)
                }
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        yelpViewModel.fragmentState.observe(viewLifecycleOwner){
            if(!it){
                yelpViewModel.getIntentView(ViewIntents.RESTAURANT_RATINGS)
            }
        }
    }

}