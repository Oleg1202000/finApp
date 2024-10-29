package com.mk1morebugs.finapp.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.rememberNavController
import com.mk1morebugs.finapp.ui.theme.FinappTheme

@Composable
fun Finapp() {
    FinappTheme {
        val navController = rememberNavController()
        val coroutineScope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }
        NavGraph(
            navController = navController,
            snackbarHostState = snackbarHostState,
            coroutineScope = coroutineScope,
        )
    }
}