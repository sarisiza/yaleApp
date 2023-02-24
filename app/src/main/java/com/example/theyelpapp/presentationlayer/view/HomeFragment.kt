package com.example.theyelpapp.presentationlayer.view

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.theyelpapp.R
import com.example.theyelpapp.databinding.FragmentRestaurantsListBinding
import com.example.theyelpapp.presentationlayer.adapters.RestaurantAdapter
import com.example.theyelpapp.utils.UIState
import com.example.theyelpapp.utils.ViewIntents

private const val TAG = "HomeFragment"
class HomeFragment : BaseFragment() {

    private val binding by lazy {
        FragmentRestaurantsListBinding.inflate(layoutInflater)
    }

    private val restaurantAdapter by lazy {
        RestaurantAdapter{restaurant ->
            yelpViewModel.selectedRestaurant = restaurant
            findNavController().navigate(R.id.action_frag_home_to_frag_det)
        }
    }

    private lateinit var permission: ArrayList<String>

    private lateinit var swipeToRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permission = arrayListOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        swipeToRefreshLayout = binding.swipeLayout

        binding.tvListTitle.text = "Restaurants Near Me"

        binding.rvRestaurantsList.apply {
            adapter = restaurantAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false)
        }

        updateList()

        swipeToRefreshLayout.setOnRefreshListener {
            swipeToRefreshLayout.isRefreshing = false
            yelpViewModel.getIntentView(ViewIntents.GET_LOCATION)
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        yelpViewModel.fragmentState.observe(viewLifecycleOwner){
            if(!it){
                permission.forEach {
                    if(checkSelfPermission(requireContext(),it) != PackageManager.PERMISSION_GRANTED){
                        requestPermissions(permission.toTypedArray(),900)
                    }
                    else{
                        yelpViewModel.locationPermissionEnabled = true
                        yelpViewModel.getIntentView(ViewIntents.GET_LOCATION)
                    }
                }
            }
        }
    }

    @Deprecated("")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 900){
            grantResults.forEach {
                yelpViewModel.locationPermissionEnabled = it == PackageManager.PERMISSION_GRANTED
                yelpViewModel.getIntentView(ViewIntents.GET_LOCATION)
            }
        }
    }

    fun updateList(){
        yelpViewModel.locationState.observe(viewLifecycleOwner){state ->
            when(state){
                is UIState.ERROR -> {
                    showError(state.e.localizedMessage){
                        yelpViewModel.getIntentView(ViewIntents.GET_LOCATION)
                    }
                }
                UIState.LOADING -> {}
                is UIState.SUCCESS -> {
                    yelpViewModel.currentLocation = state.response
                    yelpViewModel.getIntentView(ViewIntents.RESTAURANT_LIST)
                }
            }
        }

        yelpViewModel.restaurantsNearMe.observe(viewLifecycleOwner){state ->
            when(state){
                is UIState.ERROR -> {
                    showError(state.e.localizedMessage){
                        yelpViewModel.getIntentView(ViewIntents.RESTAURANT_LIST)
                    }
                }
                UIState.LOADING -> {}
                is UIState.SUCCESS -> {
                    restaurantAdapter.updateRestaurants(state.response)
                }
            }
        }
    }



}