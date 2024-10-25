package com.mk1morebugs.finapp.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinappStatusBar(
    title: String,
) {
    TopAppBar(
        modifier = Modifier.height(80.dp),
        title = {
            Text(
                modifier = Modifier.padding(horizontal = 10.dp),
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        },
    )
}