package com.oleg1202000.finapp.ui.components
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinappStatusBar(
    title: String
){
    TopAppBar(

        modifier = Modifier.height(80.dp),

        title = {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        },

        /*actions = {
                Icon(
                    modifier = Modifier.padding(10.dp),
                    painter = painterResource(id = R.drawable.ic_settings_40px),
                    contentDescription = "Settings"
                )
        },*/

        windowInsets = WindowInsets.statusBars
    )
}