package com.example.theyelpapp.usecaseslayer

import com.example.theyelpapp.datalayer.domain.Rating
import com.example.theyelpapp.datalayer.service.NetworkRepository
import com.example.theyelpapp.utils.ApiSortedBy
import com.example.theyelpapp.utils.NetworkState
import com.example.theyelpapp.utils.UIState
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

import org.junit.After
import org.junit.Before
import org.junit.Test

class GetRatingsUseCaseTest {

    private lateinit var testObject: GetRatingsUseCase
    private val mockNetworkRepository = mockk<NetworkRepository>(relaxed = true)
    private val mockNetworkState = mockk<NetworkState>(relaxed = true)

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        testObject = GetRatingsUseCase(mockNetworkRepository,mockNetworkState)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `get ratings when server is a success response returns a list of ratings is a success state`(){

        //ASSIGN
        every { mockNetworkState.isInternetEnabled() } returns true
        coEvery { mockNetworkRepository.getRatings("1234",ApiSortedBy.RELEVANT) } returns mockk{
            every { isSuccessful } returns true
            every { body() } returns mockk{
                every { reviews } returns listOf(
                    mockk{
                        every { id } returns "524"
                        every { rating } returns 3
                        every { text } returns "fbdsbhkdn"
                        every { timeCreated } returns "8393"
                        every {user} returns mockk{
                            every { name } returns "Pancho"
                            every {imageUrl} returns ""
                        }
                    },
                    mockk{
                        every { id } returns "987"
                        every { rating } returns 5
                        every { text } returns "ihfioif"
                        every { timeCreated } returns "7564"
                        every {user} returns mockk{
                            every { name } returns "Maria"
                            every {imageUrl} returns ""
                        }
                    }
                )
            }
        }

        //ACTION
        val states = mutableListOf<UIState<List<Rating>>>()
        val job = testScope.launch{
            testObject("1234",ApiSortedBy.RELEVANT).collect{
                states.add(it)
            }
        }

        //ASSERT
        assertEquals(2,states.size)
        assert(states[1] is UIState.SUCCESS)
        assertEquals(2,(states[1] as UIState.SUCCESS).response.size)
        assertEquals("987",(states[1] as UIState.SUCCESS).response[1].id)

    }

}