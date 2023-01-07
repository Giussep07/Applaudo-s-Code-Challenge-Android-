/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.presentation.screens.seasondetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.giussepr.mubi.domain.model.Episode
import com.giussepr.mubi.presentation.theme.*
import com.giussepr.mubi.presentation.widgets.MubiImage
import com.giussepr.mubi.presentation.widgets.MubiTopAppBar

@Composable
@Preview(showSystemUi = true)
fun SeasonDetailScreenPreview() {
  SeasonDetailScreen(rememberNavController())
}

@Composable
@Preview(showSystemUi = true)
fun EpisodeItemPreview() {
  val episode = Episode(
    airDate = "2017-07-08",
    episodeNumber = 6,
    id = 1599928,
    name = "Rick and Morty The Non-Canonical Adventures: 2001: A Space Odyssey",
    overview = "A claymation recreation of a classic scene from ‘2001: A Space Odyssey’.",
    productionCode = "",
    runtime = 1,
    seasonNumber = 0,
    showId = 60625,
    stillPath = "https://image.tmdb.org/t/p/w780/4TxoNFzCmt8wfCJ1BfbrkDO4Nc7.jpg",
    voteAverage = 0.0,
    voteCount = 0,
  )
  EpisodeItem(episode)
}

@Composable
fun SeasonDetailScreen(
  navController: NavHostController,
  viewModel: SeasonDetailViewModel = hiltViewModel()
) {
  var seasonName by remember { mutableStateOf("") }
  Scaffold(
    modifier = Modifier
      .fillMaxSize()
      .background(Background),
    topBar = {
      MubiTopAppBar(
        navController = navController,
        title = seasonName,
        onSearchClicked = {},
        onProfileClicked = {})
    }
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
      when (val state = viewModel.uiState.collectAsState().value) {
        is SeasonDetailViewModel.UiState.Loading -> {
          Box(
            modifier = Modifier
              .fillMaxSize(),
            contentAlignment = Alignment.Center
          ) {
            CircularProgressIndicator()
          }
        }
        is SeasonDetailViewModel.UiState.Success -> {
          seasonName = state.seasonDetail.seasonName
          LazyColumn(
            modifier = Modifier
              .fillMaxSize()
              .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
          ) {
            items(state.seasonDetail.episodes) { episode ->
              EpisodeItem(episode)
            }
          }
        }
        is SeasonDetailViewModel.UiState.Error -> {
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

@Composable
fun EpisodeItem(episode: Episode) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(start = 16.dp, end = 16.dp)
      .height(180.dp),
    elevation = 4.dp,
    shape = MaterialTheme.shapes.large,
  ) {
    Row(modifier = Modifier.fillMaxWidth()) {
      // Season poster
      MubiImage(
        modifier = Modifier
          .weight(0.3f),
        imageUrl = episode.imageUrl,
        contentDescription = episode.name,
      )
      // Season details
      Column(
        modifier = Modifier
          .weight(0.7f)
          .padding(16.dp)
      ) {
        // Episode title
        Text(
          modifier = Modifier,
          text = episode.name,
          style = MaterialTheme.typography.h6.copy(color = MaterialTheme.colors.onSurface),
          maxLines = 2,
          overflow = TextOverflow.Ellipsis,
        )
        // Season overview
        Text(
          modifier = Modifier.padding(vertical = 8.dp),
          text = episode.overview,
          style = MaterialTheme.typography.body2.copy(
            color = MaterialTheme.colors.onSurface,
            fontWeight = FontWeight.Normal,
            letterSpacing = 0.25.sp
          ),
          maxLines = 4,
          overflow = TextOverflow.Ellipsis,
        )
        // Season Episodes
        Text(
          text = episode.airDate ?: "",
          style = MaterialTheme.typography.caption.copy(color = Purple),
        )
      }
    }
  }
}
