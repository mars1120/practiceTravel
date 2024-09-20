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
import kotlinx.coroutines.Job
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

        // Prepare TravelNews mock data
        val travelNewsResultData = getStringFromFiles("travelnewsResultData.txt")
        val mockTravelNews = gson.fromJson(travelNewsResultData, TravelNews::class.java)

        // Prepare AttractionsAll mock data
        val attractionsResultData = getStringFromFiles("attractionsResultData.txt")
        val mockAttractionsAll = gson.fromJson(attractionsResultData, AttractionsAll::class.java)

        // Mock repository behavior
        val travelNewsLiveData = MutableLiveData<Result<TravelNews>>()
        val attractionsLiveData = MutableLiveData<Result<AttractionsAll>>()
        val mockJob = Job()

        whenever(travelRepository.getTravelNews(testLang)).thenReturn(
            Pair(
                travelNewsLiveData,
                mockJob
            )
        )
        whenever(travelRepository.getAttractionsAll(testLang)).thenReturn(
            Pair(
                attractionsLiveData,
                mockJob
            )
        )

        // Call the method under test
        viewModel.fetchData(testLang)

        // setup mock result
        travelNewsLiveData.value = Result.success(mockTravelNews)
        attractionsLiveData.value = Result.success(mockAttractionsAll)

        // Verify TravelNews result
        val travelNewsResult = viewModel.travelnewsResult.getOrAwaitValue()
        val actualTravelNewsJson = gson.toJson(travelNewsResult?.getOrNull())
        val expectedTravelNewsJson = gson.toJson(mockTravelNews)
        assertEquals(expectedTravelNewsJson, actualTravelNewsJson)

        // Verify AttractionsAll result
        val attractionsResult = viewModel.attractionsResult.getOrAwaitValue()
        val actualAttractionsJson = gson.toJson(attractionsResult?.getOrNull())
        val expectedAttractionsJson = gson.toJson(mockAttractionsAll)
        assertEquals(expectedAttractionsJson, actualAttractionsJson)
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