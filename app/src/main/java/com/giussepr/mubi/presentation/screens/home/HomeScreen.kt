/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.giussepr.mubi.presentation.theme.Background

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun HomeScreenPreview() {
  HomeScreen(rememberNavController())
}

@Composable
fun HomeScreen(navController: NavHostController) {
  Column(modifier = Modifier
    .fillMaxSize()
    .background(Background)) {
    Text(text = "Home Screen")
  }
}
