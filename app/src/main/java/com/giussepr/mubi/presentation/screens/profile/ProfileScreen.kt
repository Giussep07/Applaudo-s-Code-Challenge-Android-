/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.presentation.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.giussepr.mubi.R
import com.giussepr.mubi.presentation.theme.Background
import com.giussepr.mubi.presentation.theme.ColorText
import com.giussepr.mubi.presentation.theme.Purple
import com.giussepr.mubi.presentation.theme.SubtleText
import com.giussepr.mubi.presentation.widgets.MubiTopAppBar

@Composable
@Preview
fun ProfileScreenPreview() {
  ProfileScreen(rememberNavController())
}

@Composable
fun ProfileScreen(navController: NavHostController) {
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
        // TODO: Load favorite tv shows
      }
    }
  }
}
