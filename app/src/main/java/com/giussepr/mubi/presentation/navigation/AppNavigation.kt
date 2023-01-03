/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.giussepr.mubi.presentation.screens.home.HomeScreen
import com.giussepr.mubi.presentation.screens.splash.SplashScreen

@Composable
fun AppNavigation(navController: NavHostController) {
  NavHost(navController = navController, startDestination = AppScreens.SplashScreen.route) {
    composable(AppScreens.SplashScreen.route) {
      SplashScreen(navController)
    }
    composable(AppScreens.Home.route) {
      HomeScreen(navController)
    }
  }
}
