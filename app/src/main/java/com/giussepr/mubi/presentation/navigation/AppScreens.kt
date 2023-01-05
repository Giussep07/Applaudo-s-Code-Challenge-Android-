/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.presentation.navigation

sealed class AppScreens(val route: String) {
  object SplashScreen : AppScreens("splash_screen")
  object Home : AppScreens("home")
  object Search : AppScreens("search")
  object TvShowDetail : AppScreens("tv_show_detail")
  object SeasonDetail : AppScreens("season_detail")

  fun withArg(arg: String): String {
    return "$route?$arg"
  }

  fun withArgs(vararg args: String): String {
    return "$route?${args.joinToString("?")}"
  }
}
