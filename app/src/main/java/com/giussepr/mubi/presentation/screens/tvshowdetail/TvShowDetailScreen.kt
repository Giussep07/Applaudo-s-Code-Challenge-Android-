/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.presentation.screens.tvshowdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.giussepr.mubi.R
import com.giussepr.mubi.domain.model.Season
import com.giussepr.mubi.presentation.navigation.AppScreens
import com.giussepr.mubi.presentation.screens.tvshowdetail.model.UiTvShowDetail
import com.giussepr.mubi.presentation.theme.*
import com.giussepr.mubi.presentation.widgets.MubiImage
import com.giussepr.mubi.presentation.widgets.MubiRatingBar

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun TvShowDetailPreview() {
  TvShowDetailScreen(
    rememberNavController()
  )
}

@Composable
@Preview
fun TvShowSeasonItemPreview() {
  val season = Season(
    airDate = "2021-12-04",
    episodeCount = 11,
    id = 215073,
    name = "Entertainment District Arc",
    overview = "Tanjirou, Zenitsu, and Inosuke, aided by the Sound Hashira Tengen Uzui, travel to Yoshiwara red light district to hunt down a demon that has been terrorizing the town.",
    posterPath = "https://image.tmdb.org/t/p/w780/kyhi1OjpifFkfo8gXrRvf9jI7SJ.jpg",
    seasonNumber = 3
  )
  TvShowSeasonItem(season = season, onSeasonClicked = {})
}

@Composable
fun TvShowDetailScreen(
  navController: NavHostController,
  viewModel: TvShowDetailViewModel = hiltViewModel()
) {
  viewModel.uiTvShowDetail.collectAsState().value?.let { uiTvShowDetail ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(Background),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // Header
      TvShowDetailsHeader(
        uiTvShowDetail = uiTvShowDetail,
        viewModel = viewModel,
        navController = navController
      )

      val state = viewModel.uiState.collectAsState().value

      LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
          // Tv Show Summary
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(start = 16.dp, end = 16.dp, top = 16.dp)
          ) {
            // Summary Title
            Text(
              modifier = Modifier
                .fillMaxWidth(),
              text = stringResource(R.string.summary_title),
              style = MaterialTheme.typography.h6.copy(color = Purple),
            )
            // Overview
            Text(
              text = uiTvShowDetail.overview,
              modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 24.dp),
              style = MaterialTheme.typography.body2.copy(
                color = ColorText,
                shadow = Shadow(
                  color = Color.Black.copy(alpha = 0.25f),
                  offset = Offset(0f, 4f),
                  blurRadius = 4f
                )
              ),
            )
          }
        }

        when (state) {
          is TvShowDetailViewModel.UiState.Loading -> {
            item {
              // Loading
              Box(
                modifier = Modifier
                  .fillMaxSize()
                  .background(Background)
                  .padding(16.dp),
                contentAlignment = Alignment.Center
              ) {
                CircularProgressIndicator(
                  color = Purple,
                  strokeWidth = 4.dp
                )
              }
            }
          }
          is TvShowDetailViewModel.UiState.Success -> {
            items(items = state.tvShowDetail.seasons) {
              TvShowSeasonItem(season = it, onSeasonClicked = { season ->
                val route = AppScreens.SeasonDetail.withArgs(
                  state.tvShowDetail.id.toString(),
                  season.seasonNumber.toString()
                )
                navController.navigate(route)
              })
            }
          }
          is TvShowDetailViewModel.UiState.Error -> {
            item {
              Text(
                modifier = Modifier.padding(16.dp),
                text = state.message,
                style = MaterialTheme.typography.h6,
                color = Red
              )
            }
          }
        }
      }
    }
  }
}

@Composable
fun TvShowDetailsHeader(
  uiTvShowDetail: UiTvShowDetail,
  viewModel: TvShowDetailViewModel,
  navController: NavHostController
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(320.dp)
  ) {
    // Tv Show Images
    MubiImage(
      modifier = Modifier
        .fillMaxSize(),
      imageUrl = uiTvShowDetail.imageUrl,
      contentDescription = uiTvShowDetail.name,
    )
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(brush = Brush.verticalGradient(colors = listOf(Color.Transparent, Black)))
    )
    // Back button
    IconButton(
      onClick = { navController.navigateUp() },
      modifier = Modifier
        .size(56.dp)
    ) {
      Icon(
        modifier = Modifier.size(24.dp),
        imageVector = Icons.Filled.ArrowBack,
        contentDescription = stringResource(R.string.navigate_back_description),
        tint = White
      )
    }
    // Favorite Icon button
    val isFavorite = viewModel.isFavorite.collectAsState().value
    val favoriteIcon = if (isFavorite) {
      Icons.Filled.Favorite
    } else {
      Icons.Filled.FavoriteBorder
    }
    IconButton(
      onClick = { viewModel.onFavoriteButtonClicked(uiTvShowDetail) },
      modifier = Modifier
        .size(56.dp)
        .align(Alignment.TopEnd)
    ) {
      Icon(
        modifier = Modifier.size(24.dp),
        imageVector = favoriteIcon,
        contentDescription = stringResource(R.string.navigate_back_description),
        tint = White
      )
    }
    // Tv Show title and rating
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(start = 16.dp, end = 32.dp, bottom = 8.dp)
        .align(Alignment.BottomStart)
    ) {
      val shadow = Shadow(
        color = Color.Black,
        offset = Offset(5f, 5f),
        blurRadius = 8f
      )
      // Tv Show original title if it's different from the name
      if (uiTvShowDetail.originalName != uiTvShowDetail.name) {
        Text(
          modifier = Modifier
            .fillMaxWidth(),
          text = uiTvShowDetail.originalName,
          style = MaterialTheme.typography.caption.copy(shadow = shadow),
          color = White
        )
      }
      // Tv Show title
      Text(
        modifier = Modifier
          .fillMaxWidth(),
        text = uiTvShowDetail.name,
        style = MaterialTheme.typography.h4.copy(shadow = shadow),
        color = White
      )
      Spacer(modifier = Modifier.height(16.dp))
      // Tv Show rating
      MubiRatingBar(
        voteAverage = uiTvShowDetail.averageRate,
        starColor = TurquoiseLight,
        showRatingNumber = false,
        starSpacing = 8.dp
      )
    }
  }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TvShowSeasonItem(season: Season, onSeasonClicked: (Season) -> Unit) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
      .height(180.dp),
    shape = MaterialTheme.shapes.large,
    onClick = { onSeasonClicked(season) }
  ) {
    Row(modifier = Modifier.fillMaxWidth()) {
      // Season poster
      MubiImage(
        modifier = Modifier
          .weight(0.3f),
        imageUrl = season.imageUrl,
        contentDescription = season.name,
      )
      // Season details
      Column(
        modifier = Modifier
          .weight(0.7f)
          .padding(16.dp)
      ) {
        // Season title
        Text(
          modifier = Modifier,
          text = season.name,
          style = MaterialTheme.typography.h6.copy(color = ColorText),
        )
        // Season Episodes
        Text(
          modifier = Modifier.padding(vertical = 8.dp),
          text = stringResource(
            R.string.season_episodes,
            season.episodeCount
          ),
          style = MaterialTheme.typography.caption.copy(color = Purple),
        )
        // Season overview
        Text(
          modifier = Modifier,
          text = season.overview,
          style = MaterialTheme.typography.body2.copy(
            color = ColorText,
            fontWeight = FontWeight.Normal
          ),
          maxLines = 5,
          overflow = TextOverflow.Ellipsis,
        )
      }
    }
  }
}
