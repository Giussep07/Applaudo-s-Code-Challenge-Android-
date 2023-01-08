/*
 * Created by Giussep Ricardo on 07/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

@file:OptIn(ExperimentalCoroutinesApi::class)

package com.giussepr.mubi.presentation.screens.tvshowdetail

import com.giussepr.mubi.domain.error.DomainException
import com.giussepr.mubi.domain.usecase.CheckIfTvShowIsFavoriteUseCase
import com.giussepr.mubi.domain.usecase.GetTvShowDetailsUseCase
import com.giussepr.mubi.domain.usecase.RemoveLocalFavoriteTvShowUseCase
import com.giussepr.mubi.domain.usecase.SaveLocalFavoriteTvShowUseCase
import com.giussepr.mubi.presentation.screens.tvshowdetail.model.UiTvShowDetail
import org.junit.After
import org.junit.Before
import com.giussepr.mubi.domain.model.Result
import com.giussepr.mubi.domain.model.Season
import com.giussepr.mubi.domain.model.TvShowDetail
import com.giussepr.mubi.util.MainCoroutineRule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class TvShowDetailViewModelTest {

  @get:Rule
  val mainCoroutineRule = MainCoroutineRule()

  private lateinit var tvShowDetailViewModel: TvShowDetailViewModel

  private val getTvShowDetailsUseCase: GetTvShowDetailsUseCase = mockk()
  private val saveLocalFavoriteTvShowsUseCase: SaveLocalFavoriteTvShowUseCase = mockk()
  private val removeLocalFavoriteTvShowUseCase: RemoveLocalFavoriteTvShowUseCase = mockk()
  private val checkIfTvShowIsFavoriteUseCase: CheckIfTvShowIsFavoriteUseCase = mockk()
  private val _isFavorite: MutableStateFlow<Boolean> = mockk()

  @Before
  fun setUp() {
    tvShowDetailViewModel = TvShowDetailViewModel(
      getTvShowDetailsUseCase,
      saveLocalFavoriteTvShowsUseCase,
      removeLocalFavoriteTvShowUseCase,
      checkIfTvShowIsFavoriteUseCase,
      _isFavorite
    )
  }

  @After
  fun tearDown() {
    confirmVerified(getTvShowDetailsUseCase, checkIfTvShowIsFavoriteUseCase)
  }

  @Test
  fun `test init()`() = mainCoroutineRule.runBlockingTest {
    // prepare
    val expectedUiTvShowDetail = UiTvShowDetail(
      id = 130392,
      imageUrl = "https://image.tmdb.org/t/p/w1280/99vBORZixICa32Pwdwj0lWcr8K.jpg",
      originalName = "The D\u0027Amelio Show",
      name = "The D\u0027Amelio Show",
      averageRate = 9.0,
      overview = "From relative obscurity and a seemingly normal life, to overnight success and thrust into the Hollywood limelight overnight, the D’Amelios are faced with new challenges and opportunities they could not have imagined."
    )

    val expectedJsonString =
      "{\"averageRate\":9.0,\"id\":130392,\"imageUrl\":\"https://image.tmdb.org/t/p/w1280/99vBORZixICa32Pwdwj0lWcr8K.jpg\",\"name\":\"The D\\u0027Amelio Show\",\"originalName\":\"The D\\u0027Amelio Show\",\"overview\":\"From relative obscurity and a seemingly normal life, to overnight success and thrust into the Hollywood limelight overnight, the D’Amelios are faced with new challenges and opportunities they could not have imagined.\"}"

    every { getTvShowDetailsUseCase.invoke(any()) } returns flowOf()
    every { checkIfTvShowIsFavoriteUseCase.invoke(any()) } returns flowOf()

    // run
    tvShowDetailViewModel.init(expectedJsonString)

    // verify
    verify(exactly = 1) {
      getTvShowDetailsUseCase.invoke(any())
      checkIfTvShowIsFavoriteUseCase.invoke(any())
    }

    assertEquals(tvShowDetailViewModel.uiTvShowDetail.value, expectedUiTvShowDetail)
    assertEquals(tvShowDetailViewModel.tvShowId, expectedUiTvShowDetail.id)
  }

  @Test
  fun `test getTvShowDetail() success`() = mainCoroutineRule.runBlockingTest {
    // prepare
    val expectedTvShowDetail = TvShowDetail(
      id = 130392,
      seasons = buildList {
        Season(
          id = 204453,
          airDate = "2021-09-03",
          episodeCount = 8,
          name = "Season 1",
          overview = "",
          posterPath = "/z0iCS5Znx7TeRwlYSd4c01Z0lFx.jpg",
          seasonNumber = 1,
          imageUrl = "https://image.tmdb.org/t/p/w780/z0iCS5Znx7TeRwlYSd4c01Z0lFx.jpg"
        )
      }
    )

    every { getTvShowDetailsUseCase.invoke(any()) } returns flowOf(Result.Success(expectedTvShowDetail))

    // run
    tvShowDetailViewModel.getTvShowDetail()

    // verify
    verify(exactly = 1) { getTvShowDetailsUseCase.invoke(any()) }
    assertEquals(tvShowDetailViewModel.uiState.value, TvShowDetailViewModel.UiState.Success(expectedTvShowDetail))
  }

  @Test
  fun `test getTvShowDetail() error`() = mainCoroutineRule.runBlockingTest {
    // prepare
    val expectedError = "Error message"

    every { getTvShowDetailsUseCase.invoke(any()) } returns flowOf(Result.Error(DomainException(expectedError)))

    // run
    tvShowDetailViewModel.getTvShowDetail()

    // verify
    verify(exactly = 1) { getTvShowDetailsUseCase.invoke(any()) }
    assertEquals(tvShowDetailViewModel.uiState.value, TvShowDetailViewModel.UiState.Error(expectedError))
  }

  @Test
  fun `test checkIfTvShowIsFavorite()`() = mainCoroutineRule.runBlockingTest {
    // prepare
    val expectedValue = true

    every { checkIfTvShowIsFavoriteUseCase.invoke(any()) } returns flowOf(Result.Success(true))
    every { _isFavorite.value } returns true

    // run
    tvShowDetailViewModel.checkIfTvShowIsFavorite()

    // verify
    verify(exactly = 1) { checkIfTvShowIsFavoriteUseCase.invoke(any()) }
    assertEquals(tvShowDetailViewModel.isFavorite.value, expectedValue)
  }

  @Test
  fun `test onFavoriteButtonClicked() is saved as favorite`() {
    // prepare
    val expectedUiTvShowDetail = UiTvShowDetail(
      id = 130392,
      imageUrl = "https://image.tmdb.org/t/p/w1280/99vBORZixICa32Pwdwj0lWcr8K.jpg",
      originalName = "The D\u0027Amelio Show",
      name = "The D\u0027Amelio Show",
      averageRate = 9.0,
      overview = "From relative obscurity and a seemingly normal life, to overnight success and thrust into the Hollywood limelight overnight, the D’Amelios are faced with new challenges and opportunities they could not have imagined."
    )

    every { _isFavorite.value } returns true
    every { saveLocalFavoriteTvShowsUseCase.invoke(any()) } returns flowOf()
    every { removeLocalFavoriteTvShowUseCase.invoke(any()) } returns flowOf()

    // run
    tvShowDetailViewModel.onFavoriteButtonClicked(expectedUiTvShowDetail)

    // verify
    verify(exactly = 1) { removeLocalFavoriteTvShowUseCase.invoke(any()) }
    verify { saveLocalFavoriteTvShowsUseCase wasNot Called }
  }

  @Test
  fun `test onFavoriteButtonClicked() is not saved as favorite`() {
    // prepare
    val expectedUiTvShowDetail = UiTvShowDetail(
      id = 130392,
      imageUrl = "https://image.tmdb.org/t/p/w1280/99vBORZixICa32Pwdwj0lWcr8K.jpg",
      originalName = "The D\u0027Amelio Show",
      name = "The D\u0027Amelio Show",
      averageRate = 9.0,
      overview = "From relative obscurity and a seemingly normal life, to overnight success and thrust into the Hollywood limelight overnight, the D’Amelios are faced with new challenges and opportunities they could not have imagined."
    )

    every { _isFavorite.value } returns false
    every { saveLocalFavoriteTvShowsUseCase.invoke(any()) } returns flowOf()
    every { removeLocalFavoriteTvShowUseCase.invoke(any()) } returns flowOf()

    // run
    tvShowDetailViewModel.onFavoriteButtonClicked(expectedUiTvShowDetail)

    // verify
    verify(exactly = 1) { saveLocalFavoriteTvShowsUseCase.invoke(any()) }
    verify { removeLocalFavoriteTvShowUseCase wasNot Called }
  }

  @Test
  fun `test saveLocalFavoriteTvShow()`() = mainCoroutineRule.runBlockingTest {
    // prepare
    val uiTvShowDetail = UiTvShowDetail(
      id = 130392,
      imageUrl = "https://image.tmdb.org/t/p/w1280/99vBORZixICa32Pwdwj0lWcr8K.jpg",
      originalName = "The D\u0027Amelio Show",
      name = "The D\u0027Amelio Show",
      averageRate = 9.0,
      overview = "From relative obscurity and a seemingly normal life, to overnight success and thrust into the Hollywood limelight overnight, the D’Amelios are faced with new challenges and opportunities they could not have imagined."
    )
    val expectedValue = true

    every { _isFavorite.value } returns true
    every { saveLocalFavoriteTvShowsUseCase.invoke(any()) } returns flowOf(Result.Success(true))

    // run
    tvShowDetailViewModel.saveLocalFavoriteTvShow(uiTvShowDetail)

    // verify
    verify(exactly = 1) { saveLocalFavoriteTvShowsUseCase.invoke(any()) }
    assertEquals(tvShowDetailViewModel.isFavorite.value, expectedValue)
  }

  @Test
  fun `test removeLocalFavoriteTvShow()`() = mainCoroutineRule.runBlockingTest {
    // prepare
    val uiTvShowDetail = UiTvShowDetail(
      id = 130392,
      imageUrl = "https://image.tmdb.org/t/p/w1280/99vBORZixICa32Pwdwj0lWcr8K.jpg",
      originalName = "The D\u0027Amelio Show",
      name = "The D\u0027Amelio Show",
      averageRate = 9.0,
      overview = "From relative obscurity and a seemingly normal life, to overnight success and thrust into the Hollywood limelight overnight, the D’Amelios are faced with new challenges and opportunities they could not have imagined."
    )
    val expectedValue = false

    every { _isFavorite.value } returns false
    every { removeLocalFavoriteTvShowUseCase.invoke(any()) } returns flowOf(Result.Success(true))

    // run
    tvShowDetailViewModel.removeLocalFavoriteTvShow(uiTvShowDetail)

    // verify
    verify(exactly = 1) { removeLocalFavoriteTvShowUseCase.invoke(any()) }
    assertEquals(tvShowDetailViewModel.isFavorite.value, expectedValue)
  }

}
