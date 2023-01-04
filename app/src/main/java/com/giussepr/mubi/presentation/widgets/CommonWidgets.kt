/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.presentation.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.giussepr.mubi.R
import com.giussepr.mubi.domain.error.ApiException
import com.giussepr.mubi.domain.error.DomainException
import com.giussepr.mubi.domain.error.NoTvShowsResultsException
import com.giussepr.mubi.domain.model.TvShow
import com.giussepr.mubi.presentation.theme.ColorText
import com.giussepr.mubi.presentation.theme.RatingTextColor
import com.giussepr.mubi.presentation.theme.Red
import com.giussepr.mubi.presentation.theme.White
import com.giussepr.mubi.presentation.util.formatToRating
import kotlin.math.ceil

@Composable
@Preview
fun MubiTopAppBarPreview() {
  MubiTopAppBar(rememberNavController(), {}, {})
}

@Composable
@Preview
fun MubiRatingBarPreview() {
  MubiRatingBar(8.7, Modifier.padding(start = 12.dp, top = 4.dp))
}

@Composable
fun MubiTopAppBar(
  navController: NavHostController,
  onSearchClicked: () -> Unit,
  onProfileClicked: () -> Unit
) {
  TopAppBar(
    title = {
      Text(
        text = stringResource(R.string.tv_shows),
        style = MaterialTheme.typography.h6,
        color = White
      )
    },
    navigationIcon = if (navController.previousBackStackEntry != null) {
      {
        IconButton(onClick = { navController.navigateUp() }) {
          Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(R.string.back)
          )
        }
      }
    } else {
      null
    },
    actions = {
      if (navController.previousBackStackEntry == null) {
        SearchAction(onSearchClicked)
        ProfileAction(onProfileClicked)
      }
    }
  )
}

@Composable
fun SearchAction(onSearchClicked: () -> Unit) {
  IconButton(onClick = onSearchClicked) {
    Icon(
      imageVector = Icons.Filled.Search,
      contentDescription = stringResource(R.string.search),
      tint = White
    )
  }
}

@Composable
fun ProfileAction(onProfileClicked: () -> Unit) {
  IconButton(onClick = onProfileClicked) {
    Icon(
      imageVector = Icons.Filled.AccountCircle,
      contentDescription = stringResource(R.string.profile),
      tint = White
    )
  }
}

@Composable
fun MubiRatingBar(voteAverage: Double, modifier: Modifier) {
  // Calculate the voteAverage in scale of 5
  val rating = (voteAverage / 10) * 5

  val filledStars = rating.toInt()
  val partialStar = rating.rem(1f)
  val unfilledStars = 5 - filledStars - ceil(partialStar).toInt()

  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    repeat(filledStars) {
      Image(painter = painterResource(id = R.drawable.ic_star_filled), contentDescription = null)
    }

    if (partialStar > 0) {
      Box {
        Image(
          painter = painterResource(id = R.drawable.ic_star_unfilled),
          contentDescription = null,
        )
        Image(
          painter = painterResource(id = R.drawable.ic_star_filled),
          contentDescription = null,
          modifier = Modifier.drawWithContent {
            clipRect(right = size.width * partialStar.toFloat()) {
              this@drawWithContent.drawContent()
            }
          })
      }
    }

    if (unfilledStars > 0) {
      repeat(unfilledStars) {
        Image(
          painter = painterResource(id = R.drawable.ic_star_unfilled),
          contentDescription = null
        )
      }
    }

    Text(
      text = rating.formatToRating(),
      style = MaterialTheme.typography.body2,
      color = RatingTextColor,
      modifier = Modifier.padding(start = 8.dp)
    )
  }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TvShowListItem(tvShow: TvShow, onTvShowItemClicked: () -> Unit) {
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

@Composable
fun TvShowLoading() {
  Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(vertical = 16.dp)) {
    CircularProgressIndicator()
  }
}

@Composable
fun TvShowError(error: Throwable, pagingItems: LazyPagingItems<TvShow>) {
  val errorMessage = when (error) {
    is NoTvShowsResultsException -> stringResource(id = R.string.no_tv_shows_results)
    is ApiException -> error.message
    is DomainException -> error.message
    else -> "Something went wrong"
  }

  val textColor = if (error != NoTvShowsResultsException) Red else ColorText

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(vertical = 16.dp), horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(text = errorMessage, style = MaterialTheme.typography.h6, color = textColor)
    Spacer(modifier = Modifier.height(16.dp))
    if (error != NoTvShowsResultsException) {
      Button(onClick = { pagingItems.retry() }) {
        Text(text = "Retry")
      }
    }
  }
}
