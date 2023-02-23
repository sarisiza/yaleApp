package com.example.theyelpapp.presentationlayer.viewmodel

import android.location.Location
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.theyelpapp.datalayer.domain.Rating
import com.example.theyelpapp.datalayer.domain.Restaurant
import com.example.theyelpapp.usecaseslayer.GetLocationUseCase
import com.example.theyelpapp.usecaseslayer.YelpUseCases
import com.example.theyelpapp.utils.ApiSortedBy
import com.example.theyelpapp.utils.UIState
import com.example.theyelpapp.utils.ViewIntents
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class YelpViewModelTest {

    private lateinit var testObject: YelpViewModel
    private val mockUseCases = mockk<YelpUseCases>()

    private val mockDispatcher = UnconfinedTestDispatcher()

    @get:Rule val instantTask = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(mockDispatcher)
        testObject = YelpViewModel(mockUseCases)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `get user location when location use case gets a location returns a success state`(){
        //ASSIGN
        testObject.locationEnabled = true
        testObject.locationPermissionEnabled = true
        every { mockUseCases.getLocation(
            true,
            true) } returns flowOf(
                UIState.SUCCESS(mockk())
            )
        var state: UIState<Location> = UIState.LOADING

        //ACTION
        testObject.getIntentView(ViewIntents.GET_LOCATION)
        testObject.locationState.observeForever {
            state = it
        }

        //ASSERT
        assert(state is UIState.SUCCESS)

    }

    @Test
    fun `get restaurant near me when current location is not null and restaurants use case returns a list of restaurants returns a success rate`(){
        //ASSIGN
        val location = mockk<Location>()
        testObject.currentLocation = location
        every { mockUseCases.getRestaurants(
            location,
            "hot_and_new",
            ApiSortedBy.DISTANCE) } returns flowOf(
                UIState.SUCCESS(listOf(
                    mockk(),
                    mockk(),
                    mockk()
                ))
            )
        var state: UIState<List<Restaurant>> = UIState.LOADING

        //ACTION
        testObject.getIntentView(ViewIntents.RESTAURANT_LIST)
        testObject.restaurantsNearMe.observeForever {
            state = it
        }

        //ASSERT
        assert(state is UIState.SUCCESS)
        when(state){
            is UIState.ERROR -> {}
            UIState.LOADING -> {}
            is UIState.SUCCESS -> {
                assertEquals(3,(state as UIState.SUCCESS).response.size)
            }
        }

    }

    @Test
    fun `get ratings when ratings use case returns a list of ratings return a success state`(){
        //ASSIGN
        testObject.selectedRestaurant = mockk{
            every { id } returns "1234"
        }
        every { mockUseCases.getRatings(
            "1234",
            ApiSortedBy.RELEVANT) } returns flowOf(
                UIState.SUCCESS(listOf(
                    mockk(),
                    mockk(),
                    mockk(),
                    mockk()
                ))
            )
        var state: UIState<List<Rating>> = UIState.LOADING

        //ACTION
        testObject.getIntentView(ViewIntents.RESTAURANT_RATINGS)
        testObject.restaurantRatings.observeForever {
            state = it
        }

        //ASSERT
        assert(state is UIState.SUCCESS)
        when(state){
            is UIState.ERROR -> {}
            UIState.LOADING -> {}
            is UIState.SUCCESS -> {
                assertEquals(4,(state as UIState.SUCCESS).response.size)
            }
        }

    }

}