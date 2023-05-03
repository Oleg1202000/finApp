package com.oleg1202000.finapp.ui.categories.addcategory

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.oleg1202000.finapp.R
import com.oleg1202000.finapp.ui.theme.colorCategories


@Composable
fun AddCategoryScreen(
    viewModel: AddCategoryViewModel = viewModel(),
    navController: NavHostController,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item { Spacer(modifier = Modifier.height(200.dp)) }

        item {
            OutlinedTextField(
                value = uiState.categoryName,
                onValueChange = { viewModel.setCategoryName(it) },
                label = { Text("Название категории") }
            )
        }

        item { Spacer(modifier = Modifier.height(20.dp)) }


        // "Демонстрация" карточки категории
        if (uiState.selectedCategoryIcon != null && uiState.selectedCategoryColor != null) {
            item {
                Card (
                    modifier = Modifier
                        .height(150.dp)
                        .width(150.dp)
                        .padding(
                            start = 15.dp,
                            end = 15.dp,
                            top = 15.dp,
                            bottom = 15.dp

                        ),
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Icon(
                            painter = painterResource(id = uiState.selectedCategoryIcon!!),
                            contentDescription = "CategoryItem",
                            tint = uiState.selectedCategoryColor!!
                        )

                        Spacer(
                            modifier = Modifier.height(15.dp)
                        )

                        Text(text = uiState.categoryName)
                    }
                }
            }

        }


        // "Сетка категорий"
        item {
            LazyRow {

                val iconItemsSize = iconCategoryItems.size
                for (i in 0 until iconItemsSize - iconItemsSize % 3 step 3) {
                    item {
                        Column {
                            for (j in i..i + 2) {

                                val colorCard = if (uiState.selectedCategoryIcon != null && uiState.selectedCategoryIcon == iconCategoryItems[j]) {

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

                                DrawItem(
                                    clickedItem = { viewModel.setCategoryIcon(iconCategoryItems[j]) },
                                    colorCard = colorCard
                                ) {

                                    Icon(
                                        painter = painterResource(id = iconCategoryItems[j]),
                                        contentDescription = "IconCategoryItem"
                                    )
                                }
                            }
                        }
                    }
                }

                item {
                    Column {
                        for (j in iconItemsSize - iconItemsSize % 3 until iconItemsSize) {

                            val colorCard = if (uiState.selectedCategoryIcon != null && uiState.selectedCategoryIcon == iconCategoryItems[j]) {
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

                            DrawItem(
                                clickedItem = { viewModel.setCategoryIcon(iconCategoryItems[j]) },
                                colorCard = colorCard
                            ) {
                                Icon(
                                    painter = painterResource(id = iconCategoryItems[j]),
                                    contentDescription = "IconCategoryItem"
                                )
                            }
                            // TODO: Убрать лишний (повторяющийся) код
                        }
                    }
                }
            }
        }


        item { Spacer(modifier = Modifier.height(20.dp)) }


        item {
            LazyRow {

                val colorItemsSize = colorCategories.size
                for (i in 0 until colorItemsSize - colorItemsSize % 3 step 3) {
                    item {
                        Column {
                            for (j in i..i + 2) {

                                val colorCard = if (uiState.selectedCategoryColor != null && uiState.selectedCategoryColor == colorCategories[j]) {
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

                                DrawItem(
                                    clickedItem = { viewModel.setCategoryColor(colorCategories[j]) },
                                    colorCard = colorCard
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(50.dp)
                                            .background(colorCategories[j])
                                    )
                                }
                            }
                        }
                    }
                }

                item {
                    Column {
                        for (j in colorItemsSize - colorItemsSize % 3 until colorItemsSize) {

                            val colorCard = if (uiState.selectedCategoryColor != null && uiState.selectedCategoryColor == colorCategories[j]) {
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

                            DrawItem(
                                clickedItem = { viewModel.setCategoryColor(colorCategories[j]) },
                                colorCard = colorCard
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .background(colorCategories[j])
                                )
                            }
                        }
                    }
                }
            }
        }


        item { Spacer(modifier = Modifier.height(30.dp)) }


        item {
            Button(
                onClick = {
                    viewModel.addCategory()
                    navController.popBackStack()
                }
            ) {
                Text(text = "Добавить категорию")
            }
        }


        item { Spacer(modifier = Modifier.height(30.dp)) }
    }
}


@Composable
fun DrawItem(
    clickedItem: () -> Unit,
    colorCard: CardColors,
    item: @Composable () -> Unit
) {
    Card (
        modifier = Modifier
            .size(100.dp)
            .padding(
                end = 15.dp,
                bottom = 15.dp
            )
            .clickable {
                clickedItem()
            },

        colors = colorCard
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item()

        }
    }
}


/*
@Preview
@Composable
fun AddCategoryPreview() {
    AddCategoryScreen()
}*/


val iconCategoryItems: List<Int> = listOf(
    R.drawable.ic_category_cottage_40px,

    R.drawable.ic_category_directions_bus_40px,
    R.drawable.ic_category_tram_40px,

    R.drawable.ic_category_shopping_cart_40px,
    R.drawable.ic_category_checkroom_40px,
    R.drawable.ic_category_table_lamp_40px,
    R.drawable.ic_category_backpack_40px,

    R.drawable.ic_category_coffee_40px,
    R.drawable.ic_category_restaurant_40px,
    R.drawable.ic_category_wine_bar_40px,
    R.drawable.ic_category_fastfood_40px,
    R.drawable.ic_category_local_pizza_40px,

    R.drawable.ic_category_cut_40px,

    R.drawable.ic_category_language_40px,
    R.drawable.ic_category_lan_40px,
    R.drawable.ic_category_cell_tower_40px,
    R.drawable.ic_category_call_40px,

    R.drawable.ic_category_local_hospital_40px,

    R.drawable.ic_category_confirmation_number_40px,
    R.drawable.ic_category_map_40px,
    R.drawable.ic_category_train_40px,
    R.drawable.ic_category_flight_takeoff_40px,
    R.drawable.ic_category_apartment_40px,

    R.drawable.ic_category_devices_40px,
    R.drawable.ic_category_tv_40px,

    R.drawable.ic_category_fertile_40px,
    R.drawable.ic_category_local_florist_40px,

    R.drawable.ic_category_theater_comedy_40px,
    R.drawable.ic_category_stadium_40px,
    R.drawable.ic_category_park_40px,

    R.drawable.ic_category_stadia_controller_40px,
    R.drawable.ic_category_play_shapes_40px,

    R.drawable.ic_category_science_40px,
    R.drawable.ic_category_school_40px,

    R.drawable.ic_category_piano_40px,
    R.drawable.ic_category_photo_camera_40px,
    R.drawable.ic_category_payments_40px,

    R.drawable.ic_category_local_taxi_40px,
    R.drawable.ic_category_local_shipping_40px,

    R.drawable.ic_category_local_car_wash_40px,
    R.drawable.ic_category_local_gas_station_40px,
)