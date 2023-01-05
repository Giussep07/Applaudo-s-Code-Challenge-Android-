/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.giussepr.mubi.presentation.screens.home.HomeScreen
import com.giussepr.mubi.presentation.screens.search.SearchScreen
import com.giussepr.mubi.presentation.screens.seasondetail.SeasonDetailScreen
import com.giussepr.mubi.presentation.screens.splash.SplashScreen
import com.giussepr.mubi.presentation.screens.tvshowdetail.TvShowDetailScreen

@Composable
fun AppNavigation(navController: NavHostController) {
  NavHost(navController = navController, startDestination = AppScreens.SplashScreen.route) {
    composable(AppScreens.SplashScreen.route) {
      SplashScreen(navController)
    }
    composable(AppScreens.Home.route) {
      HomeScreen(navController)
    }
    composable(AppScreens.Search.route) {
      SearchScreen(navController)
    }
    composable(
      route = AppScreens.TvShowDetail.route
        .plus("?{tvShowDetailJson}"),
      arguments = listOf(
        navArgument("tvShowDetailJson") { type = NavType.StringType },
      )
    ) {
      TvShowDetailScreen(navController)
    }
    composable(
      route = AppScreens.SeasonDetail.route
        .plus("?{tvShowId}")
        .plus("?{seasonNumber}"),
      arguments = listOf(
        navArgument("tvShowId") { type = NavType.IntType },
        navArgument("seasonNumber") { type = NavType.IntType },
      )
    ) {
      SeasonDetailScreen(navController)
    }
  }
}
