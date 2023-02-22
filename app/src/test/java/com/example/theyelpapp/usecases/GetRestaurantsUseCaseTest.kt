package com.example.theyelpapp.usecases

import android.location.Location
import com.example.theyelpapp.data.domain.Restaurant
import com.example.theyelpapp.data.service.NetworkRepository
import com.example.theyelpapp.data.service.YelpApi
import com.example.theyelpapp.utils.ApiSortedBy
import com.example.theyelpapp.utils.NetworkState
import com.example.theyelpapp.utils.UIState
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

class GetRestaurantsUseCaseTest {

    private lateinit var testObject: GetRestaurantsUseCase
    private val mockNetworkRepository = mockk<NetworkRepository>(relaxed = true)
    private val mockNetworkState = mockk<NetworkState>(relaxed = true)
    private val mockLocation = mockk<Location>(relaxed = true)

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        testObject = GetRestaurantsUseCase(
            mockNetworkRepository,
            mockNetworkState
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `get restaurants when server is a success response returns a list of restaurants is a success state`(){
        //ASSIGN
        every { mockNetworkState.isInternetEnabled() } returns true
        coEvery { mockNetworkRepository.getRestaurants(
            mockLocation.latitude,
            mockLocation.longitude,
            "",
            ApiSortedBy.BEST_MATCH
        ) } returns mockk{
            every { isSuccessful } returns true
            every { body() } returns mockk{
                every { businesses } returns listOf(
                    mockk{
                        every { id } returns "1"
                        every { name } returns "One"
                        every { coordinates } returns mockk{
                            every { latitude } returns 0.0
                            every {longitude} returns 0.0
                        }
                        every { location } returns mockk{
                            every { address1 } returns ""
                            every { address2 } returns ""
                            every { address3 } returns ""
                            every { city } returns ""
                            every { country } returns ""
                            every { displayAddress } returns listOf()
                            every { state } returns ""
                            every { zipCode } returns ""
                        }
                        every { categories } returns listOf()
                        every { transactions } returns listOf()
                        every {displayPhone} returns ""
                        every {imageUrl} returns ""
                        every {price} returns ""
                        every {distance} returns 0.0
                        every {rating} returns 0.0
                        every {reviewCount} returns 0
                    },mockk{
                        every { id } returns "2"
                        every { name } returns "Two"
                        every { coordinates } returns mockk{
                            every { latitude } returns 0.0
                            every {longitude} returns 0.0
                        }
                        every { location } returns mockk{
                            every { address1 } returns ""
                            every { address2 } returns ""
                            every { address3 } returns ""
                            every { city } returns ""
                            every { country } returns ""
                            every { displayAddress } returns listOf()
                            every { state } returns ""
                            every { zipCode } returns ""
                        }
                        every { categories } returns listOf()
                        every { transactions } returns listOf()
                        every {displayPhone} returns ""
                        every {imageUrl} returns ""
                        every {price} returns ""
                        every {distance} returns 0.0
                        every {rating} returns 0.0
                        every {reviewCount} returns 0
                    },mockk{
                        every { id } returns "3"
                        every { name } returns "Three"
                        every { coordinates } returns mockk{
                            every { latitude } returns 0.0
                            every {longitude} returns 0.0
                        }
                        every { location } returns mockk{
                            every { address1 } returns ""
                            every { address2 } returns ""
                            every { address3 } returns ""
                            every { city } returns ""
                            every { country } returns ""
                            every { displayAddress } returns listOf()
                            every { state } returns ""
                            every { zipCode } returns ""
                        }
                        every { categories } returns listOf()
                        every { transactions } returns listOf()
                        every {displayPhone} returns ""
                        every {imageUrl} returns ""
                        every {price} returns ""
                        every {distance} returns 0.0
                        every {rating} returns 0.0
                        every {reviewCount} returns 0
                    }
                )
            }
        }

        //ACTION
        val states = mutableListOf<UIState<List<Restaurant>>>()
        val job = testScope.launch {
            testObject(mockLocation,"",ApiSortedBy.BEST_MATCH).collect{
                states.add(it)
            }
        }

        //ASSERT
        assertEquals(2,states.size)
        assert(states[1] is UIState.SUCCESS)
        assertEquals(3,(states[1] as UIState.SUCCESS).response.size)
        assertEquals("2",(states[1] as UIState.SUCCESS).response[1].id)

    }

}