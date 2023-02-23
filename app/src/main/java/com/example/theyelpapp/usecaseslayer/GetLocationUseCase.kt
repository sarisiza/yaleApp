package com.example.theyelpapp.usecaseslayer

import android.annotation.SuppressLint
import android.location.Location
import com.example.theyelpapp.utils.LocationFailureException
import com.example.theyelpapp.utils.LocationRequiredException
import com.example.theyelpapp.utils.PermissionsRequiredException
import com.example.theyelpapp.utils.UIState
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val fusedLocation: FusedLocationProviderClient,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
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
    }.flowOn(ioDispatcher)

}