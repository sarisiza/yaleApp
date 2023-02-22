package com.example.theyelpapp.usecases

import android.annotation.SuppressLint
import android.location.Location
import com.example.theyelpapp.utils.LocationRequiredException
import com.example.theyelpapp.utils.PermissionsRequiredException
import com.google.android.gms.location.FusedLocationProviderClient
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val fusedLocation: FusedLocationProviderClient
) {

    @SuppressLint("MissingPermission")
    operator fun invoke(
        locationPermissionEnabled: Boolean,
        locationEnabled: Boolean
    ): Location?{
        var location: Location? = null
        if(locationPermissionEnabled){
            if(locationEnabled){
                fusedLocation.lastLocation.addOnCompleteListener{task ->
                    location = task.result
                }
                return location
            }else throw LocationRequiredException()
        }else throw PermissionsRequiredException()
    }

}