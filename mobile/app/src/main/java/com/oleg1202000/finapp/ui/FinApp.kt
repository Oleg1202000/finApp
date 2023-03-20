package com.oleg1202000.finapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oleg1202000.finapp.ui.theme.FinappTheme
import com.oleg1202000.finapp.ui.theme.LightGray
import com.oleg1202000.finapp.ui.theme.Typography

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
        elevation = 60.dp
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text ="[Название экрана]", style = Typography.h1)
            Text(text ="Настройки", style = Typography.button)
        }

    }
}


@Preview(showBackground = true)
@Composable
fun NavagationBar() {
    BottomNavigation(
        elevation = 60.dp
    ) {


        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = "Главная", style = Typography.button)
            Text(text = "План", style = Typography.button)
            Text(text = "История", style = Typography.button)
        }
    }

}

