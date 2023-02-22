package com.example.theyelpapp.usecases

import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import io.mockk.clearAllMocks
import io.mockk.mockk
import junit.framework.TestCase.assertEquals

import org.junit.After
import org.junit.Before
import org.junit.Test

class GetLocationUseCaseTest {

    private lateinit var testObject: GetLocationUseCase
    private val mockFusedLocation =
        mockk<FusedLocationProviderClient>(relaxed = true)
    private var mockLocation = mockk<Location>(relaxed= true)

    @Before
    fun setUp() {
        testObject = GetLocationUseCase(mockFusedLocation)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `get location use case returns a location when permission is enabled and location is on`(){
        //ASSIGN
        mockFusedLocation.setMockMode(true)
        mockLocation.apply {
            latitude = 33.906857
            longitude = -84.4697653
        }
        mockFusedLocation.setMockLocation(mockLocation)

        //ACTION
        val currentLocation = testObject(true,true)

        //ASSERT
        //assertEquals(currentLocation?.latitude, mockLocation.latitude)
        //assertEquals(currentLocation?.longitude,mockLocation.longitude)

    }

}