package com.giussepr.mubi.presentation.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.giussepr.mubi.presentation.navigation.AppNavigation
import com.giussepr.mubi.presentation.theme.MubiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MubiTheme {
                AppNavigation(navController = rememberNavController())
            }
        }
    }
}
