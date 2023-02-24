package com.example.theyelpapp.presentationlayer.view

import android.app.AlertDialog
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.theyelpapp.presentationlayer.viewmodel.YelpViewModel
import com.example.theyelpapp.utils.ViewIntents
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "BaseFragment"
@AndroidEntryPoint
open class BaseFragment: Fragment() {

    protected val yelpViewModel: YelpViewModel by lazy {
        ViewModelProvider(requireActivity())[YelpViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()
        yelpViewModel.getIntentView(ViewIntents.START_FRAGMENT)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d(TAG, "onConfigurationChanged: changed")
        yelpViewModel.getIntentView(ViewIntents.CONFIGURATION_CHANGE)
    }

    protected fun showError(message: String, action: () -> Unit){
        AlertDialog.Builder(requireActivity())
            .setTitle("Error Occurred")
            .setMessage(message)
            .setPositiveButton("RETRY"){dialog, _ ->
                action()
                dialog.dismiss()
            }
            .setNegativeButton("DISMISS"){dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }



}