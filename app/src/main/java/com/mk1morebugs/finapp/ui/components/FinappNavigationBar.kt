package com.mk1morebugs.finapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import com.mk1morebugs.finapp.R
import com.mk1morebugs.finapp.ui.Screen

@Composable
fun FinappNavigationBar(
    currentDestination: Screen,
    fromNavBarNavigateTo: (Screen) -> Unit,
) {
    NavigationBar(
        windowInsets = WindowInsets.navigationBars
    ) {
        navItems.forEach { screen ->
            NavigationBarItem(
                label = {
                    Text(text = screen.label,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                selected = currentDestination == screen.destination,
                onClick = {
                    fromNavBarNavigateTo(screen.destination)
                },
                icon = {
                    Icon(painter = painterResource(screen.icon),
                    contentDescription = screen.label)
                },
            )
        }
    }
}

data class ScreenItem(val destination: Screen, val label: String, val icon: Int)

val navItems = listOf(
    ScreenItem(Screen.Costs(isFactCosts = true), "Главная", R.drawable.ic_home),
    ScreenItem(Screen.Costs(isFactCosts = false), "План", R.drawable.ic_plan),
    ScreenItem(Screen.History, "История", R.drawable.ic_history)
)
