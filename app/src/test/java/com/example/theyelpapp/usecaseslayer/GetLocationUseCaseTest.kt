package com.example.theyelpapp.usecaseslayer

import android.location.Location
import com.example.theyelpapp.datalayer.location.LocationRepository
import com.example.theyelpapp.utils.UIState
import com.google.android.gms.location.FusedLocationProviderClient
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

import org.junit.After
import org.junit.Before
import org.junit.Test

class GetLocationUseCaseTest {

    private lateinit var testObject: GetLocationUseCase
    private var mockLocationRepository =
        mockk<LocationRepository>(relaxed = true)

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        testObject = GetLocationUseCase(mockLocationRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `get location use case returns a location when location repository returns a location is a success`(){
        //ASSIGN
        every { mockLocationRepository.getLocationData() } returns mockk{
            every { latitude } returns 67.9754
            every { longitude } returns -123.457
        }

        //ACTION
        val state = mutableListOf<UIState<Location>>()
        val job = testScope.launch {
            testObject(true).collect{
                state.add(it)
            }
        }

        //ASSERT
        assertEquals(2,state.size)
        assert(state[1] is UIState.SUCCESS)
        assertEquals(-123.457,(state[1] as UIState.SUCCESS).response.longitude)

    }

}