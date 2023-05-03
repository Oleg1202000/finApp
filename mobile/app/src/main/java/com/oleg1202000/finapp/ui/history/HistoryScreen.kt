package com.oleg1202000.finapp.ui.history

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController


@Composable
fun HistoryScreen(
    navController: NavHostController,
    currentDestination: NavDestination?
) {

        Column {


            Text(text = "History")

        }

}