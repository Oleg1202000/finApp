package com.mk1morebugs.finapp.ui.components

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import com.mk1morebugs.finapp.R
import com.mk1morebugs.finapp.ui.Screen


@Composable
fun FinappFloatingActionButton(
    navController: NavHostController,
    currentDestination : NavDestination?
) {

    FloatingActionButton(
        onClick = {

            if (currentDestination?.route == "home") {
                navController.navigate(Screen.AddData.route)
            }
            else if (currentDestination?.route == "home/plan") {
                navController.navigate(Screen.AddPlan.route)
            }
        }
    ) {
        Icon(painter = painterResource(id = R.drawable.ic_add_40px), contentDescription = "Add expense / plan")
    }


}