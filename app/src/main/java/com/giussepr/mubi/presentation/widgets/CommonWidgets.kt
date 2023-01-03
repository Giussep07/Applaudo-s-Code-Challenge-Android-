/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.presentation.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.giussepr.mubi.R
import com.giussepr.mubi.presentation.theme.RatingTextColor
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
