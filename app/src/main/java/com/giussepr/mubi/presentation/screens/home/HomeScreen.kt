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
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.giussepr.mubi.presentation.navigation.AppScreens
import com.giussepr.mubi.presentation.screens.home.TvShowFilter.*
import com.giussepr.mubi.presentation.theme.*
import com.giussepr.mubi.presentation.widgets.*
import kotlinx.coroutines.flow.collectLatest

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun HomeScreenPreview() {
  HomeScreen(rememberNavController())
}

@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {
  val selectedFilter by viewModel.tvShowFilter.collectAsState()

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
        onSearchClicked = { navController.navigate(AppScreens.Search.route) },
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
            onTvShowFilterClicked = { viewModel.changeTvShowFilter(it) })
        }
      }

      val tvShowList = viewModel.tvShowList.collectAsState().value.collectAsLazyPagingItems()
      LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        modifier = Modifier.fillMaxSize()
      ) {
        items(tvShowList.itemCount) { index ->
          tvShowList[index]?.let {
            TvShowListItem(tvShow = it, onTvShowItemClicked = { viewModel.onTvShowItemClicked(it) })
          }
        }

        // First pagination load
        when (val state = tvShowList.loadState.refresh) {
          is LoadState.Loading -> {
            item(span = { GridItemSpan(2) }) {
              TvShowLoading()
            }
          }
          is LoadState.Error -> {
            item(span = { GridItemSpan(2) }) {
              TvShowError(error = state.error, tvShowList)
            }
          }
          else -> {}
        }

        tvShowList.loadState.refresh

        // Pagination
        when (val state = tvShowList.loadState.append) {
          is LoadState.Loading -> {
            item(span = { GridItemSpan(2) }) {
              TvShowLoading()
            }
          }
          is LoadState.Error -> {
            item(span = { GridItemSpan(2) }) {
              TvShowError(error = state.error, tvShowList)
            }
          }
          else -> {}
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
fun TvShowFilterChip(
  filter: TvShowFilter,
  isSelected: Boolean,
  onTvShowFilterClicked: (TvShowFilter) -> Unit
) {
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
    onClick = { onTvShowFilterClicked(filter) },
    colors = ChipDefaults.chipColors(backgroundColor = backgroundColor),
    border = BorderStroke(1.dp, borderColor)
  ) {
    Text(text = filter.text, style = MaterialTheme.typography.subtitle2, color = textColor)
  }
}
