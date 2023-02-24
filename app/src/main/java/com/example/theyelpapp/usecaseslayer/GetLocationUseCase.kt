package com.example.theyelpapp.usecaseslayer

import android.annotation.SuppressLint
import android.location.Location
import com.example.theyelpapp.datalayer.location.LocationRepository
import com.example.theyelpapp.utils.LocationFailureException
import com.example.theyelpapp.utils.PermissionsRequiredException
import com.example.theyelpapp.utils.UIState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

private const val TAG = "GetLocationUseCase"
class GetLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    @SuppressLint("MissingPermission")
    operator fun invoke(
        locationPermissionEnabled: Boolean
    ): Flow<UIState<Location>> = flow{
        emit(UIState.LOADING)
        try {
            var location: Location? = null
            if(locationPermissionEnabled){
                location = locationRepository.getLocationData()
                location?.let {
                    emit(UIState.SUCCESS(it))
                }?: throw LocationFailureException()
            }else throw PermissionsRequiredException()
        }catch (e: Exception){
            emit(UIState.ERROR(e))
        }
    }.flowOn(ioDispatcher)

}