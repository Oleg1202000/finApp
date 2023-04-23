@file:OptIn(ExperimentalMaterial3Api::class)

package com.oleg1202000.finapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.oleg1202000.finapp.ui.components.FinappFloatingActionButton
import com.oleg1202000.finapp.ui.components.FinappNavigationBar
import com.oleg1202000.finapp.ui.components.ScreenItem
import com.oleg1202000.finapp.ui.components.StatusBar
import com.oleg1202000.finapp.ui.components.navItems
import com.oleg1202000.finapp.ui.theme.FinappTheme
import com.oleg1202000.finapp.ui.theme.Typography


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Finapp() {

    FinappTheme {

        val showSystemBar: MutableState<Boolean> = remember {
            mutableStateOf(true)
        }
        val showFloatingActionButton: MutableState<Boolean> = remember {
            mutableStateOf(true)
        }

        val statusBarItem: MutableState<String> = remember {
            mutableStateOf("")
        }



        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        Scaffold(
           topBar = {if (showSystemBar.value)
                StatusBar(
                    item = statusBarItem,  // Для отображения суммы доходов и расходов
                )
                    },

            bottomBar = {if (showSystemBar.value)
                FinappNavigationBar(
                navController = navController,
                currentDestination = currentDestination,
            )
                        },

        floatingActionButton = { if (showSystemBar.value && showFloatingActionButton.value) {
            FinappFloatingActionButton(
                navController = navController,
                currentDestination = currentDestination
            )
        }
        }
        ) {
            padding ->
            Box(modifier = Modifier.padding(padding)) {
                NavGraph(
                    navController = navController,
                    showSystemBar = showSystemBar,
                    showFloatingActionButton = showFloatingActionButton,
                    statusBarItem = statusBarItem
                )
            }
        }
    }
}