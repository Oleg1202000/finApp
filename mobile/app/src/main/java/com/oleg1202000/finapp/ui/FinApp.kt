@file:OptIn(ExperimentalMaterial3Api::class)

package com.oleg1202000.finapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.oleg1202000.finapp.ui.components.FinappNavigationBar
import com.oleg1202000.finapp.ui.theme.FinappTheme
import com.oleg1202000.finapp.ui.theme.Typography


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Finapp() {

    FinappTheme {

        val navController = rememberNavController()

        Scaffold(
            topBar = { StatusBar()},
            bottomBar = { FinappNavigationBar(navController = navController) },
        ) {
            padding ->
            Box(modifier = Modifier.padding(padding)) {
                NavGraph(navController = navController)
            }
        }
    }
}


@Composable
fun StatusBar(){

    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text ="[Название экрана]", style = Typography.titleLarge)
                Text(text ="Настройки", style = Typography.labelMedium)
            }
        },
        windowInsets = WindowInsets.statusBars,
        modifier = Modifier.height(60.dp)
    )
}




