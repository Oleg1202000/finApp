@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.oleg1202000.finapp.ui.categories

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.oleg1202000.finapp.ui.Screen
import com.oleg1202000.finapp.ui.home.adddata.CategoryItem
import com.oleg1202000.finapp.ui.theme.Shapes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun CategoriesScreen(
    navController: NavHostController,
    showBottomSheet: MutableState<Boolean>,
    sheetState: SheetState,
    coroutineScope: CoroutineScope,
    categories: List<CategoryItem>,
    selectedCategoryId: Long?,
    selectCategory: (Long) -> Unit
) {

        Spacer(modifier = Modifier.height(30.dp))

        CategoryItems(
            navController = navController,
            categories = categories,
            showBottomSheet = showBottomSheet,
            sheetState = sheetState,
            coroutineScope = coroutineScope,
            selectCategory = selectCategory,
            selectedCategoryId = selectedCategoryId
        )

        Spacer(Modifier.height(30.dp))
}


@Composable
fun CategoryItems(
    navController: NavHostController,
    showBottomSheet: MutableState<Boolean>,
    sheetState: SheetState,
    coroutineScope: CoroutineScope,
    categories: List<CategoryItem>,
    selectCategory: (Long) -> Unit,
    selectedCategoryId: Long?
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {

        for (i in 0..categories.size - 2 step 2) {

            item {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    for (j in 0..1) {
                        CardItem(
                            item = categories[i+j],
                            showBottomSheet = showBottomSheet,
                            sheetState = sheetState,
                            coroutineScope = coroutineScope,
                            selectCategory = selectCategory,
                            selectedCategoryId = selectedCategoryId
                        )
                    }
                }
            }
        }


        item {
            if (categories.size % 2 == 1) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    CardItem(
                        item = categories[categories.lastIndex],
                        showBottomSheet = showBottomSheet,
                        sheetState = sheetState,
                        coroutineScope = coroutineScope,
                        selectCategory = selectCategory,
                        selectedCategoryId = selectedCategoryId
                    )

                    ButtonItem(
                        navController = navController
                    )
                }
            } else {

                ButtonItem(
                    navController = navController
                )
            }
        }
    }
}


@Composable
fun CardItem(
    item: CategoryItem,
    showBottomSheet: MutableState<Boolean>,
    sheetState: SheetState,
    coroutineScope: CoroutineScope,
    selectCategory: (Long) -> Unit,
    selectedCategoryId: Long?
) {

    val colorCard = if (selectedCategoryId != null && selectedCategoryId == item.id) {

        CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSurface,
            contentColor = MaterialTheme.colorScheme.surface
        )
    } else {

        CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    }

    Card(
        modifier = Modifier
            .height(150.dp)
            .width(150.dp)
            .padding(
                start = 15.dp,
                end = 15.dp,
                bottom = 30.dp
            )
            .clickable {

                selectCategory(item.id)

                coroutineScope.launch {
                    sheetState.hide()
                    showBottomSheet.value = false
                }
            },

        colors = colorCard
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                painter = painterResource(id = item.pathToIcon),
                contentDescription = item.name,
                tint = Color(item.colorIcon.toULong())
            )

            Text(text = item.name)
        }
    }
}


@Composable
fun ButtonItem(
    navController: NavHostController
) {
    Button(
        modifier = Modifier
            .size(150.dp)
            .padding(
                start = 15.dp,
                end = 15.dp,
                top = 15.dp,
                bottom = 30.dp
            ),
        shape = Shapes.extraLarge,
        onClick = {
            navController.navigate(Screen.AddCategory.route)
        }
    ) {

        Text(text = "Добавить категорию") }
}
