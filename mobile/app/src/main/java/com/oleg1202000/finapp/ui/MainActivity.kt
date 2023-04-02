package com.oleg1202000.finapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity()  {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //appComponent.inject(this)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            Finapp()
        }
    }
}
