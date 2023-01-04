/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.presentation.screens.tvshowdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.giussepr.mubi.R
import com.giussepr.mubi.presentation.theme.*
import com.giussepr.mubi.presentation.widgets.MubiRatingBar

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun TvShowDetailPreview() {
  TvShowDetailScreen(
    rememberNavController()
  )
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
        .background(Background)
    ) {
      // Header
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(320.dp)
      ) {
        // Tv Show Images
        AsyncImage(
          modifier = Modifier
            .fillMaxSize()
            .background(HintTextColor),
          contentScale = ContentScale.Crop,
          model = uiTvShowDetail.imageUrl,
          contentDescription = uiTvShowDetail.name,
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

      // Tv Show Summary
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(start = 16.dp, end = 16.dp, top = 24.dp)
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
            .padding(top = 8.dp),
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
  }
}
