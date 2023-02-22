package com.example.theyelpapp.usecases

import android.annotation.SuppressLint
import android.location.Location
import com.example.theyelpapp.utils.LocationFailureException
import com.example.theyelpapp.utils.LocationRequiredException
import com.example.theyelpapp.utils.PermissionsRequiredException
import com.example.theyelpapp.utils.UIState
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val fusedLocation: FusedLocationProviderClient
) {

    @SuppressLint("MissingPermission")
    operator fun invoke(
        locationPermissionEnabled: Boolean,
        locationEnabled: Boolean
    ): Flow<UIState<Location>> = flow{
        emit(UIState.LOADING)
        try {
            var location: Location? = null
            if(locationPermissionEnabled){
                if(locationEnabled){
                    fusedLocation.lastLocation.addOnCompleteListener{task ->
                        location = task.result
                    }
                    location?.let {
                        emit(UIState.SUCCESS(it))
                    }?: throw LocationFailureException()
                }else throw LocationRequiredException()
            }else throw PermissionsRequiredException()
        }catch (e: Exception){
            emit(UIState.ERROR(e))
        }
    }

}