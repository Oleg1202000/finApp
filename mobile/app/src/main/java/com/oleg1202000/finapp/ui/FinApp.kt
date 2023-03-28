@file:OptIn(ExperimentalMaterial3Api::class)

package com.oleg1202000.finapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*


import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oleg1202000.finapp.ui.theme.FinappTheme
import com.oleg1202000.finapp.ui.theme.LightGray
import com.oleg1202000.finapp.ui.theme.Typography


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Finapp() {
    FinappTheme {
        Scaffold(
            modifier = Modifier
                .background(color = LightGray)
                .windowInsetsPadding(
                    WindowInsets.systemBars
                        .only(WindowInsetsSides.Vertical)
                ),
            topBar = { StatusBar()},
            bottomBar = { NavagationBar() },
            content = {padding -> Box(modifier = Modifier.padding(padding))
                }
        )
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
        modifier = Modifier.height(60.dp)
    )
}


@Preview(showBackground = true)
@Composable
fun NavagationBar() {
    BottomAppBar(
        tonalElevation = 60.dp,


    ) {


        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = "Главная", style = Typography.labelMedium)
            Text(text = "План", style = Typography.labelMedium)
            Text(text = "История", style = Typography.labelMedium)
        }
    }

}

