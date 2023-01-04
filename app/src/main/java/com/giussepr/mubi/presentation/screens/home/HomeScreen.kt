/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.presentation.screens.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.giussepr.mubi.domain.model.TvShow
import com.giussepr.mubi.presentation.screens.home.TvShowFilter.*
import com.giussepr.mubi.presentation.theme.*
import com.giussepr.mubi.presentation.widgets.MubiRatingBar
import com.giussepr.mubi.presentation.widgets.MubiTopAppBar

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun HomeScreenPreview() {
  HomeScreen(rememberNavController())
}

@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {
  viewModel.getTopRatedMovies()

  var selectedFilter by remember { mutableStateOf(TOP_RATED) }
  val filters = listOf(
    TOP_RATED,
    POPULAR,
    ON_TV,
    AIRING_TODAY
  )

  Scaffold(
    modifier = Modifier
      .fillMaxSize()
      .background(Background),
    topBar = {
      MubiTopAppBar(navController = navController,
        onSearchClicked = { /*TODO*/ },
        onProfileClicked = { /*TODO*/ })
    }
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {
      // Tv Show Filters
      Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(7.dp)) {
        filters.forEach { filter ->
          val isSelected = selectedFilter == filter
          TvShowFilterChip(
            filter = filter,
            isSelected = isSelected,
            onTvShowFilterClicked = { selectedFilter = filter })
        }
      }

      when (val homeState = viewModel.homeState.collectAsState().value) {
        is HomeViewModel.HomeState.Loading -> {
          Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
          }
        }
        is HomeViewModel.HomeState.Success -> {
          LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 150.dp)) {
            items(homeState.tvShowList) { tvShow ->
              TvShowListItem(tvShow = tvShow, onTvShowItemClicked = { /*TODO*/ })
            }
          }
        }
        is HomeViewModel.HomeState.Error -> {
          // TODO
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TvShowFilterChip(filter: TvShowFilter, isSelected: Boolean, onTvShowFilterClicked: () -> Unit) {
  val backgroundColor by animateColorAsState(
    targetValue = if (isSelected) MaterialTheme.colors.primary else Gray,
    animationSpec = tween(
      durationMillis = 300
    )
  )
  val textColor by animateColorAsState(
    targetValue = if (isSelected) White else ColorText,
    animationSpec = tween(
      durationMillis = 300
    )
  )
  val borderColor by animateColorAsState(
    targetValue = if (isSelected) MaterialTheme.colors.primary else BorderColor,
    animationSpec = tween(
      durationMillis = 300
    )
  )

  Chip(
    onClick = { onTvShowFilterClicked() },
    colors = ChipDefaults.chipColors(backgroundColor = backgroundColor),
    border = BorderStroke(1.dp, borderColor)
  ) {
    Text(text = filter.text, style = MaterialTheme.typography.subtitle2, color = textColor)
  }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TvShowListItem(tvShow: TvShow, onTvShowItemClicked: () -> Unit) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .heightIn(min = 260.dp)
      .padding(start = 8.dp, end = 8.dp, top = 16.dp),
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
          .height(136.dp),
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
