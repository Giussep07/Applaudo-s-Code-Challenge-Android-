/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.presentation.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.giussepr.mubi.R
import com.giussepr.mubi.domain.model.FavoriteTvShow
import com.giussepr.mubi.domain.model.TvShow
import com.giussepr.mubi.presentation.navigation.AppScreens
import com.giussepr.mubi.presentation.theme.*
import com.giussepr.mubi.presentation.widgets.MubiRatingBar
import com.giussepr.mubi.presentation.widgets.MubiTopAppBar
import com.giussepr.mubi.presentation.widgets.TvShowListItem
import kotlinx.coroutines.flow.collectLatest

@Composable
@Preview
fun ProfileScreenPreview() {
  ProfileScreen(rememberNavController())
}

@Composable
fun ProfileScreen(navController: NavHostController, viewModel: ProfileViewModel = hiltViewModel()) {
  Scaffold(modifier = Modifier
    .fillMaxSize()
    .background(Background), topBar = {
    MubiTopAppBar(
      navController = navController,
      title = stringResource(id = R.string.profile),
      onSearchClicked = {},
      onProfileClicked = {})
  }) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // Profile photo
      Box(
        modifier = Modifier
          .padding(top = 24.dp)
          .size(142.dp)
          .border(16.dp, color = Purple.copy(alpha = 0.1f), shape = CircleShape),
        contentAlignment = Alignment.Center
      ) {
        Image(
          modifier = Modifier
            .clip(CircleShape)
            .size(100.dp),
          painter = painterResource(id = R.drawable.giussep_photo_profile),
          contentDescription = stringResource(
            id = R.string.profile_photo
          )
        )
      }

      // User name
      Text(
        text = stringResource(id = R.string.giussep_ricardo),
        style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Medium),
        color = ColorText,
        modifier = Modifier.padding(top = 12.dp)
      )

      // Username
      Text(
        text = stringResource(id = R.string.giussep_ricardo_username),
        style = MaterialTheme.typography.caption,
        color = SubtleText,
      )

      // Favorite tv shows
      Column(modifier = Modifier.fillMaxWidth()) {
        Text(
          text = stringResource(id = R.string.favorite_tv_shows),
          style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Medium),
          color = ColorText,
          modifier = Modifier.padding(top = 24.dp, start = 16.dp)
        )
        Spacer(Modifier.size(16.dp))
        when (val state = viewModel.uiState.collectAsState().value) {
          is ProfileViewModel.ProfileUiState.Loading -> {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
              CircularProgressIndicator()
            }
          }
          is ProfileViewModel.ProfileUiState.Success -> {
            if (state.favoriteTvShows.isEmpty()) {
              Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                  text = stringResource(id = R.string.no_favorite_tv_shows),
                  style = MaterialTheme.typography.subtitle1,
                  color = SubtleText,
                )
              }
            } else {
              LazyRow(
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp),
                contentPadding = PaddingValues(end = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
              ) {
                items(state.favoriteTvShows) { favoriteTvShow ->
                  FavoriteTvShowListItem(tvShow = favoriteTvShow, onTvShowItemClicked = {
                    viewModel.onTvShowItemClicked(favoriteTvShow)
                  })
                }
              }
            }
          }
          is ProfileViewModel.ProfileUiState.Error -> {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
              Text(
                text = state.message,
                style = MaterialTheme.typography.subtitle1,
                color = Red,
              )
            }
          }
        }
      }

      LaunchedEffect(key1 = Unit) {
        viewModel.navigateToTvShowDetails.collectLatest { value ->
          if (value.isNotEmpty()) {
            viewModel.navigateToTvShowDetailsHandled()
            navController.navigate(AppScreens.TvShowDetail.withArg(value))
          }
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavoriteTvShowListItem(tvShow: FavoriteTvShow, onTvShowItemClicked: () -> Unit) {
  Card(
    modifier = Modifier
      .width(180.dp)
      .height(240.dp),
    shape = MaterialTheme.shapes.large,
    onClick = { onTvShowItemClicked() }
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
    ) {
      AsyncImage(
        modifier = Modifier
          .fillMaxWidth()
          .height(160.dp),
        contentScale = ContentScale.Crop,
        model = tvShow.imageUrl,
        contentDescription = tvShow.name,
      )
      // Tv Show Title
      Text(
        modifier = Modifier
          .fillMaxWidth()
          .padding(start = 12.dp, end = 12.dp, top = 16.dp),
        text = tvShow.name,
        style = MaterialTheme.typography.subtitle2,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = ColorText
      )
      // Tv Show Rating
      MubiRatingBar(
        voteAverage = tvShow.voteAverage,
        modifier = Modifier.padding(start = 12.dp, bottom = 16.dp)
      )
    }
  }
}
