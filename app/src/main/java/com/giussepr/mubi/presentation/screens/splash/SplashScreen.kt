/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.presentation.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.giussepr.mubi.R
import com.giussepr.mubi.presentation.navigation.AppScreens
import com.giussepr.mubi.presentation.theme.LightBlue
import com.giussepr.mubi.presentation.theme.Purple
import kotlinx.coroutines.delay

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun SplashScreenPreview() {
  SplashScreen(rememberNavController())
}

@Composable
fun SplashScreen(navController: NavHostController) {

  LaunchedEffect(key1 = true) {
    delay(2000L)
    navController.popBackStack()
    navController.navigate(AppScreens.Home.route)
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(
        brush = Brush.verticalGradient(
          colors = listOf(LightBlue, Purple, Purple)
        )
      ),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.horizontal_logo_lottie))
    LottieAnimation(composition, iterations = 1, modifier = Modifier.fillMaxSize(0.7f))
  }
}

