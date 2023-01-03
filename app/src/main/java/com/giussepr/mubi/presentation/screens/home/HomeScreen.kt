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
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
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
fun HomeScreen(navController: NavHostController) {
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

      // Tv Show List
      LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 150.dp)) {
        repeat(10) {
          item {
            TvShowListItem({ /*TODO*/ })
          }
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
fun TvShowListItem(onTvShowItemClicked: () -> Unit) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
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
        model = "https://d32qys9a6wm9no.cloudfront.net/images/movies/backdrop/90/7ca0518b563b0be26fbcd4d9adb08c52_706x397.jpg?t=1636102095",
        contentDescription = "Tv Show Image",
      )
      // Tv Show Title
      Text(
        modifier = Modifier
          .fillMaxWidth()
          .padding(start = 12.dp, end = 12.dp, top = 16.dp),
        text = "The Mandalorian",
        style = MaterialTheme.typography.subtitle2,
        color = ColorText
      )
      // Tv Show Rating
      MubiRatingBar(voteAverage = 8.5, modifier = Modifier.padding(start = 12.dp, bottom = 16.dp))
    }
  }
}
