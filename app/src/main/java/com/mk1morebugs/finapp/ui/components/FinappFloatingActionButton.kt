package com.mk1morebugs.finapp.ui.components

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.mk1morebugs.finapp.R

@Composable
fun FinappFloatingActionButton(
    onClick: () -> Unit,
) {
    FloatingActionButton(
        onClick = onClick
    ) {
        Icon(painter = painterResource(id = R.drawable.ic_add_40px), contentDescription = "Add expense / plan")
    }
}