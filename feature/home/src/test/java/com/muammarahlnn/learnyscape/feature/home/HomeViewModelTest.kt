package com.muammarahlnn.learnyscape.feature.home

import com.muammarahlnn.learnyscape.core.domain.home.GetEnrolledClassesUseCase
import com.muammarahlnn.learnyscape.core.model.data.EnrolledClassInfoModel
import com.muammarahlnn.learnyscape.core.testing.repository.TestHomeRepository
import com.muammarahlnn.learnyscape.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

/**
 * @Author Muammar Ahlan Abimanyu
 * @File HomeViewModelTest, 30/05/2024 17.04
 */
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val homeRepository = TestHomeRepository()

    private val getEnrolledClassesUseCase = GetEnrolledClassesUseCase(
        homeRepository::getEnrolledClasses
    )

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        viewModel = HomeViewModel(
            getEnrolledClassesUseCase = getEnrolledClassesUseCase,
        )
    }

    @Test
    fun uiState_whenInitialized_thenShowLoading() = runTest {
        assertEquals(
            expected = HomeContract.UiState.Loading,
            actual = viewModel.state.value.uiState
        )
    }

    @Test
    fun uiState_whenEnrolledClassesFetched_thenSuccess() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.state.collect()
        }

        homeRepository.sendEnrolledClasses(sampleEnrolledClasses)
        viewModel.event(HomeContract.Event.FetchEnrolledClasses)

        val uiState = viewModel.state.value.uiState
        assertIs<HomeContract.UiState.Success>(uiState)
        assertEquals(
            expected = sampleEnrolledClasses,
            actual = viewModel.state.value.classes,
        )

        collectJob.cancel()
    }

    @Test
    fun uiState_whenFetchEnrolledClassesEmpty_thenSuccessEmpty() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.state.collect()
        }

        homeRepository.sendEnrolledClasses(emptyList())
        viewModel.event(HomeContract.Event.FetchEnrolledClasses)

        val uiState = viewModel.state.value.uiState
        assertIs<HomeContract.UiState.SuccessEmpty>(uiState)
        assertEquals(
            expected = emptyList(),
            actual = viewModel.state.value.classes,
        )

        collectJob.cancel()
    }

    @Test
    fun searchedClasses_whenSearchQueryEmpty_returnsAllClasses() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.state.collect()
        }

        homeRepository.sendEnrolledClasses(sampleEnrolledClasses)
        viewModel.event(HomeContract.Event.FetchEnrolledClasses)
        viewModel.event(HomeContract.Event.OnSearchQueryChanged(""))

        assertEquals(
            expected = sampleEnrolledClasses,
            actual = viewModel.state.value.searchedClasses,
        )

        collectJob.cancel()
    }

    @Test
    fun searchedClasses_whenSearchQueryExists_returnsMatchedClasses() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.state.collect()
        }

        homeRepository.sendEnrolledClasses(sampleEnrolledClasses)
        viewModel.event(HomeContract.Event.FetchEnrolledClasses)

        advanceUntilIdle()
        val searchQuery = "android"
        viewModel.event(HomeContract.Event.OnSearchQueryChanged(searchQuery))
        advanceTimeBy(500)
        runCurrent()

        assertEquals(
            expected = sampleEnrolledClasses.filter {
                it.className.startsWith(searchQuery, true)
            },
            actual = viewModel.state.value.searchedClasses,
        )

        collectJob.cancel()
    }
}

private val sampleEnrolledClasses = listOf(
    EnrolledClassInfoModel(
        id = "0",
        className = "Android Development",
        lecturerNames = listOf("Lorem Ipsum Dolor Sit Amet"),
    ),
    EnrolledClassInfoModel(
        id = "1",
        className = "Back End Development",
        lecturerNames = listOf("Lorem Ipsum Dolor Sit Amet"),
    ),
    EnrolledClassInfoModel(
        id = "2",
        className = "Machine Learning",
        lecturerNames = listOf("Lorem Ipsum Dolor Sit Amet"),
    )
)