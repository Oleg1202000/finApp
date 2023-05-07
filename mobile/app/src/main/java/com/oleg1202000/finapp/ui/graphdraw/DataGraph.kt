package com.oleg1202000.finapp.ui.graphdraw

import androidx.compose.ui.graphics.Color

data class DataGraph(
    val categoryName: String,
    val iconCategory: Int,
    val colorIcon: Long,
    val amount: Int,
    val sumAmount: Int = 0,
    val coefficientAmount: Float,
    val colorItem: Color
)
