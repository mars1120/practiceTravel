package com.travel.app.homepage

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.travel.app.data.AttractionsAll
import com.travel.app.data.TravelNews
import com.travel.app.network.ITravelRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@ExperimentalCoroutinesApi
class HomepageViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

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
    fun verifyTravelApiResult() = runTest {
        val testLang = "zh-tw"

        // Prepare mock data
        val travelNewsResultData = getStringFromFiles("travelnewsResultData.txt")
        val mockTravelNews = gson.fromJson(travelNewsResultData, TravelNews::class.java)

        val attractionsResultData = getStringFromFiles("attractionsResultData.txt")
        val mockAttractionsAll = gson.fromJson(attractionsResultData, AttractionsAll::class.java)

        // Mock repository behavior
        whenever(travelRepository.getTravelNews(testLang)).thenReturn(
            flowOf(Result.success(mockTravelNews))
        )
        whenever(travelRepository.getAttractionsAll(testLang)).thenReturn(
            flowOf(Result.success(mockAttractionsAll))
        )

        // Call the method under test
        viewModel.fetchData(testLang)

        // Advance the dispatcher to run all coroutines
        testDispatcher.scheduler.advanceUntilIdle()

        // Verify results
        val uiState = viewModel.uiState.value
        assertFalse(uiState.isLoading)
        assertNull(uiState.error)

        val actualTravelNewsJson = gson.toJson(uiState.travelNews)
        val expectedTravelNewsJson = gson.toJson(mockTravelNews)
        assertEquals(expectedTravelNewsJson, actualTravelNewsJson)

        val actualAttractionsJson = gson.toJson(uiState.attractions)
        val expectedAttractionsJson = gson.toJson(mockAttractionsAll)
        assertEquals(expectedAttractionsJson, actualAttractionsJson)
    }

    private fun getStringFromFiles(fileName: String): String {
        val currentPath: Path = FileSystems.getDefault().getPath("").toAbsolutePath()
        var currentAbsPath =
            Paths.get(currentPath.toString() + "/src/test/java/com/travel/app/resources/${fileName}")
        val content: String = String(Files.readAllBytes(currentAbsPath));
        return content
    }
}