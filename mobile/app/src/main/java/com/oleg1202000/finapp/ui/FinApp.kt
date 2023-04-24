@file:OptIn(ExperimentalMaterial3Api::class)

package com.oleg1202000.finapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.oleg1202000.finapp.ui.theme.FinappTheme


@Composable
fun Finapp() {

    FinappTheme {

        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        NavGraph(
            navController = navController,
            currentDestination = currentDestination
        )

    }
}