package com.oleg1202000.finapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oleg1202000.finapp.ui.theme.FinappTheme

@Composable
fun Finapp() {
    FinappTheme {
                
        """
        NavigationRail(
            modifier = Modifier.fillMaxSize(),
            backgroundColor = MaterialTheme.colors.background
        ) {
            Row() {
                Text(text = "1")
                Text(text = "2")
                Text(text = "3")
            }

        }
        """
        
        Scaffold(
            bottomBar = { NavagationBar() },
            content = {padding -> Box(modifier = Modifier.padding(padding))
                }
        )
    }




}

@Composable
fun NavagationBar() {
    BottomNavigation(
        modifier = Modifier.height(60.dp)
    ) {


        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = "1")
            Text(text = "2")
            Text(text = "3")
        }
    }

}

