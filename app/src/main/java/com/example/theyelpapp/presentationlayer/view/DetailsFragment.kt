package com.example.theyelpapp.presentationlayer.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.theyelpapp.R
import com.example.theyelpapp.databinding.RestaurantDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val binding by lazy {
        RestaurantDetailsBinding.inflate(layoutInflater)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //Add navigation for details
        val navHost = activity?.supportFragmentManager
            ?.findFragmentById(R.id.frag_container_details) as NavHostFragment
        binding.bnvDetails.setupWithNavController(navHost.navController)
        // Inflate the layout for this fragment
        return binding.root
    }

}