/*
 * Created by Giussep Ricardo on 07/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

@file:OptIn(ExperimentalCoroutinesApi::class)

package com.giussepr.mubi.presentation.screens.profile

import com.giussepr.mubi.domain.error.DomainException
import com.giussepr.mubi.domain.model.FavoriteTvShow
import com.giussepr.mubi.domain.usecase.GetLocalFavoriteTvShowsUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import org.junit.After
import org.junit.Before
import com.giussepr.mubi.domain.model.Result
import com.giussepr.mubi.util.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class ProfileViewModelTest {

  @get:Rule
  val mainCoroutineRule = MainCoroutineRule()

  private val getLocalFavoriteTvShowsUseCase: GetLocalFavoriteTvShowsUseCase = mockk()
  private lateinit var profileViewModel: ProfileViewModel


  @Before
  fun setUp() {
    profileViewModel = ProfileViewModel(getLocalFavoriteTvShowsUseCase)
  }

  @After
  fun tearDown() {
  }

  @Test
  fun `test getFavoriteTvShows`() = mainCoroutineRule.runBlockingTest {
    // prepare
    val expectedFavoriteTvShows: List<FavoriteTvShow> = buildList {
      FavoriteTvShow(
        id = 1,
        imageUrl = "https://image.tmdb.org/t/p/w500/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg",
        originalName = "The Mandalorian",
        name = "name",
        voteAverage = 1.0,
        overview = "overview",
      )
    }

    every { getLocalFavoriteTvShowsUseCase() } returns flow {
      emit(
        Result.Success(
          expectedFavoriteTvShows
        )
      )
    }

    // run
    profileViewModel.getFavoriteTvShows()

    // verify
    verify(exactly = 1) {
      profileViewModel.getFavoriteTvShows()
      getLocalFavoriteTvShowsUseCase()
    }

    assertEquals(
      profileViewModel.uiState.value,
      ProfileViewModel.ProfileUiState.Success(expectedFavoriteTvShows)
    )
  }

  @Test
  fun `test getFavoriteTvShows with error`() = mainCoroutineRule.runBlockingTest {
    // prepare
    val expectedErrorMessage = "Error message"

    every { getLocalFavoriteTvShowsUseCase() } returns flow {
      emit(Result.Error(DomainException(expectedErrorMessage)))
    }

    // run
    profileViewModel.getFavoriteTvShows()

    // verify
    verify(exactly = 1) {
      profileViewModel.getFavoriteTvShows()
      getLocalFavoriteTvShowsUseCase()
    }

    assertEquals(
      profileViewModel.uiState.value,
      ProfileViewModel.ProfileUiState.Error(expectedErrorMessage)
    )
  }

  @Test
  fun `test onTvShowItemClicked()`() {
    // prepare
    val favoriteTvShow = FavoriteTvShow(
      id = 1,
      imageUrl = "https://image.tmdb.org/t/p/w500/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg",
      originalName = "The Mandalorian",
      name = "name",
      voteAverage = 1.0,
      overview = "overview",
    )

    val expectedJson = "{\"id\":1,\"imageUrl\":\"https://image.tmdb.org/t/p/w500/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg\",\"originalName\":\"The Mandalorian\",\"name\":\"name\",\"averageRate\":1.0,\"overview\":\"overview\"}"

    // run
    profileViewModel.onTvShowItemClicked(favoriteTvShow)

    // verify
    assertNotNull(profileViewModel.navigateToTvShowDetails.value)
    assertEquals(profileViewModel.navigateToTvShowDetails.value, expectedJson)
  }

  @Test
  fun `test navigateToTvShowDetailsHandled()`() {
    // run
    profileViewModel.navigateToTvShowDetailsHandled()

    // verify
    assertEquals(profileViewModel.navigateToTvShowDetails.value, "")
  }

}
