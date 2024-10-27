package com.mk1morebugs.finapp.ui.categories.addcategory

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mk1morebugs.finapp.R
import com.mk1morebugs.finapp.ui.components.FinappScaffold
import com.mk1morebugs.finapp.ui.theme.colorCategories
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.ceil

@Composable
fun AddCategoryScreen(
    viewModel: AddCategoryViewModel = hiltViewModel(),
    coroutineScope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    backToPreviousDestination: () -> Unit,
) {
    FinappScaffold(
        statusbarTitle = stringResource(R.string.create_category),
        snackbarHostState = snackBarHostState,
    ) { paddingValues ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        uiState.snackbarMessageId?.let {
            val snackbarText = stringResource(it)
            LaunchedEffect(uiState.snackbarMessageId) {
                coroutineScope.launch {
                    snackBarHostState.showSnackbar(
                        message = snackbarText
                    )
                }
            }
        }

        if (uiState.isBackToPreviousScreen) {
            backToPreviousDestination()
        }

        AddCategoryScreenContent(
            modifier = Modifier.padding(paddingValues),
            categoryNameValue = uiState.categoryName,
            onCategoryNameValueValueChange = viewModel::setCategoryName,
            selectedCategoryIcon = uiState.selectedCategoryIcon,
            setCategoryIcon = viewModel::setCategoryIcon,
            selectedCategoryColor = uiState.selectedCategoryColor,
            setCategoryColor = viewModel::setCategoryColor,
            uiStateIsLoading = uiState.isLoading,
            onAddCategoryClick = viewModel::addCategory,
        )
    }
}

@Composable
fun AddCategoryScreenContent(
    modifier: Modifier = Modifier,
    categoryNameValue: String,
    onCategoryNameValueValueChange: (String) -> Unit,
    selectedCategoryIcon: Int?,
    setCategoryIcon: (Int) -> Unit,
    selectedCategoryColor: Color?,
    setCategoryColor: (Color) -> Unit,
    uiStateIsLoading: Boolean,
    onAddCategoryClick: () -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { Spacer(modifier = Modifier.height(200.dp)) }

        item {
            OutlinedTextField(
                value = categoryNameValue,
                onValueChange = onCategoryNameValueValueChange,
                label = { Text("Название категории") }
            )
        }

        item {
            val columnCount = 3
            GridIcons(
                rowCount = ceil(iconCategoryItems.size.toDouble() / columnCount).toInt(),
                columnCount = columnCount,
                categoryIconIdList = iconCategoryItems,
                selectedCategoryIconId = selectedCategoryIcon,
                onIconClick = setCategoryIcon,
            )
        }

        item { Spacer(modifier = Modifier.height(20.dp)) }

        item {
            val columnCount = 3
            GridColors(
                rowCount = ceil(iconCategoryItems.size.toDouble() / columnCount).toInt(),
                columnCount = columnCount,
                colorCategoriesList = colorCategories,
                selectedCategoryColor = selectedCategoryColor,
                onClick = setCategoryColor
            )
        }

        if (selectedCategoryIcon != null && selectedCategoryColor != null) {
            item {
                Card(
                    modifier = Modifier
                        .size(150.dp)
                        .padding(all = 15.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        if (!uiStateIsLoading) {
                            Icon(
                                painter = painterResource(id = selectedCategoryIcon),
                                contentDescription = "CategoryItem",
                                tint = selectedCategoryColor
                            )
                            Spacer(modifier = Modifier.height(15.dp))

                            Text(text = categoryNameValue)
                        } else {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(30.dp)) }

        item {
            Button(
                onClick = {
                    onAddCategoryClick()
                }
            ) {
                Text(text = "Добавить категорию")
            }
        }

        item { Spacer(modifier = Modifier.height(40.dp)) }
    }
}

@Composable
private fun GridIcons(
    rowCount: Int,
    columnCount: Int,
    categoryIconIdList: List<Int>,
    selectedCategoryIconId: Int?,
    onIconClick: (Int) -> Unit,
) {
    LazyRow {
        for (rowIndex in 0..<rowCount) {
            item {
                Column {
                    for (columnIndex in 0..<columnCount) {
                        val currentPositionOnList = rowIndex * columnCount + columnIndex
                        if (currentPositionOnList < categoryIconIdList.size) {
                            val colorCard =
                                if (selectedCategoryIconId != null
                                    && selectedCategoryIconId == categoryIconIdList[currentPositionOnList]
                                ) {
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

                            CardItem(
                                onClick = { onIconClick(categoryIconIdList[currentPositionOnList]) },
                                colorCard = colorCard
                            ) {
                                Icon(
                                    painter = painterResource(id = categoryIconIdList[currentPositionOnList]),
                                    contentDescription = "IconCategoryItem"
                                )
                            }
                        } else {
                            break
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GridColors(
    rowCount: Int,
    columnCount: Int,
    colorCategoriesList: List<Color>,
    selectedCategoryColor: Color?,
    onClick: (Color) -> Unit,
) {
    LazyRow {
        for (rowIndex in 0..<rowCount) {
            item {
                Column {
                    for (columnIndex in 0..<columnCount) {
                        val currentPositionOnList = rowIndex * columnCount + columnIndex
                        if (currentPositionOnList < colorCategoriesList.size) {
                            val colorCard =
                                if (selectedCategoryColor != null
                                    && selectedCategoryColor == colorCategoriesList[currentPositionOnList]
                                ) {
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

                            CardItem(
                                onClick = { onClick(colorCategoriesList[currentPositionOnList]) },
                                colorCard = colorCard
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .background(colorCategoriesList[currentPositionOnList])
                                )
                            }
                        } else {
                            break
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CardItem(
    onClick: () -> Unit,
    colorCard: CardColors,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = Modifier
            .size(100.dp)
            .padding(
                end = 15.dp,
                bottom = 15.dp
            )
            .clickable(onClick = onClick),
        colors = colorCard
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            content()
        }
    }
}


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