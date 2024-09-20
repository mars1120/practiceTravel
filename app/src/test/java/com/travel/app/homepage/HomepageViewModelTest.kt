package com.travel.app.homepage

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.travel.app.data.AttractionsAll
import com.travel.app.data.TravelNews
import com.travel.app.network.ITravelRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import retrofit2.Response
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@ExperimentalCoroutinesApi
class HomepageViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var travelRepository: ITravelRepository

    private lateinit var viewModel: HomepageViewModel
    private val gson = Gson()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = HomepageViewModel(travelRepository)
    }

    @Test
    fun verifyTravelApiResult(): Unit = runTest {
        val testLang = "zh-tw"
        val resultData = getStringFromFiles("travelnewsResultData.txt")
        // Prepare mock data
        val mockTravelNews = Gson().fromJson(resultData, TravelNews::class.java)
        val mockResponse = Response.success(mockTravelNews)

        // Mock repository behavior
        whenever(travelRepository.getTravelNews(testLang)).thenReturn(
            MutableLiveData(Result.success(mockResponse))
        )

        val resultData1 = getStringFromFiles("attractionsResultData.txt")
        // Prepare mock data
        val mockAttractionsAll = Gson().fromJson(resultData1, AttractionsAll::class.java)
        val mockResponse1 = Response.success(mockAttractionsAll)

        // Mock repository behavior
        whenever(travelRepository.getAttractionsAll(testLang)).thenReturn(
            MutableLiveData(Result.success(mockResponse1))
        )

        // Call the method under test
        viewModel.fetchData(testLang)

        // Get the value from LiveData
        val result = viewModel.travelnewsResult.getOrAwaitValue()

        // Verify the result
        val actualJson = gson.toJson(result?.getOrNull())
        val expectedJson = gson.toJson(mockTravelNews)
        assertEquals(expectedJson, actualJson)

        // Get the value from LiveData
        val result1 = viewModel.attractionsResult.getOrAwaitValue()

        // Verify the result
        val actualJson1 = gson.toJson(result1?.getOrNull())
        val expectedJson1 = gson.toJson(mockAttractionsAll)
        assertEquals(expectedJson1, actualJson1)
    }

    private fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS
    ): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {

            override fun onChanged(value: T) {
                data = value
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }

        this.observeForever(observer)

        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }

        @Suppress("UNCHECKED_CAST")
        return data as T
    }

    private fun getStringFromFiles(fileName: String): String {
        val currentPath: Path = FileSystems.getDefault().getPath("").toAbsolutePath()
        var currentAbsPath =
            Paths.get(currentPath.toString() + "/src/test/java/com/travel/app/resources/${fileName}")
        val content: String = String(Files.readAllBytes(currentAbsPath));
        return content
    }
}