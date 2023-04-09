package com.oleg1202000.finapp.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.oleg1202000.finapp.ui.Navigation
import com.oleg1202000.finapp.ui.rememberNavigation

/*
* 3 цвета для диаграммы:
* зеленый - если с лимитом все ок
* красный - если не ок
* серый - если ничего не известно про лимит для выбранного периода
*/


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToHome: () -> Unit,
    navigation: Navigation = rememberNavigation()

) {
    Column() {
        Text(text = "Home")
    }
}