package com.mk1morebugs.finapp.ui.categories


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.mk1morebugs.finapp.ui.Screen
import com.mk1morebugs.finapp.ui.theme.Shapes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    showBottomSheet: MutableState<Boolean>,
    sheetState: SheetState,
    coroutineScope: CoroutineScope,
    categories: List<CategoryItem>,
    selectedCategoryId: Long?,
    selectCategory: (Long) -> Unit,
    deleteCategoryById: (Long) -> Unit,
    navigateTo: (Screen) -> Unit
) {

        Spacer(modifier = Modifier.height(30.dp))

        CategoryItems(
            categories = categories,
            showBottomSheet = showBottomSheet,
            sheetState = sheetState,
            coroutineScope = coroutineScope,
            selectCategory = selectCategory,
            selectedCategoryId = selectedCategoryId,
            deleteCategoryById = deleteCategoryById,
            navigateTo = navigateTo,
        )

        Spacer(Modifier.height(30.dp))
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryItems(
    showBottomSheet: MutableState<Boolean>,
    sheetState: SheetState,
    coroutineScope: CoroutineScope,
    categories: List<CategoryItem>,
    selectedCategoryId: Long?,
    selectCategory: (Long) -> Unit,
    deleteCategoryById: (Long) -> Unit,
    navigateTo: (Screen) -> Unit

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
                            selectedCategoryId = selectedCategoryId,
                            deleteCategoryById = deleteCategoryById
                        )
                    }
                }
            }
        }


        item {

                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    if (categories.size % 2 == 1) {
                        CardItem(
                            item = categories[categories.lastIndex],
                            showBottomSheet = showBottomSheet,
                            sheetState = sheetState,
                            coroutineScope = coroutineScope,
                            selectCategory = selectCategory,
                            selectedCategoryId = selectedCategoryId,
                            deleteCategoryById = deleteCategoryById
                        )

                        ButtonItem(
                            navigateTo = navigateTo,
                        )
                    } else {

                        ButtonItem(
                            navigateTo = navigateTo,
                        )
                        Spacer(
                            modifier = Modifier
                                .size(150.dp)
                                .padding(
                                    start = 15.dp,
                                    end = 15.dp,
                                    top = 15.dp,
                                    bottom = 30.dp
                                )
                        )
                    }
                }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardItem(
    item: CategoryItem,
    showBottomSheet: MutableState<Boolean>,
    sheetState: SheetState,
    coroutineScope: CoroutineScope,
    selectedCategoryId: Long?,
    selectCategory: (Long) -> Unit,
    deleteCategoryById: (Long) -> Unit

) {
    var showDropdownMenu by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    val colorCard = if (selectedCategoryId != null && selectedCategoryId == item.id) {

        CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
        )
    } else {

        CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

    Card (
        modifier = Modifier
            .height(150.dp)
            .width(150.dp)
            .padding(
                start = 15.dp,
                end = 15.dp,
                bottom = 30.dp
            ),

        colors = colorCard,
        onClick = {
            showDropdownMenu = true
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                painter = painterResource(id = item.iconId),
                contentDescription = item.name,
                tint = Color(item.colorIcon.toULong())
            )

            Text(text = item.name)

            if (showDropdownMenu) {
                Box {
                    DropdownMenu(
                        expanded = true,
                        offset = DpOffset(x = (-66).dp, y = (-10).dp),
                        onDismissRequest = { showDropdownMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = "Выбрать") },
                            onClick = {
                                selectCategory(item.id)
                                showDropdownMenu = false
                                coroutineScope.launch {
                                    sheetState.hide()
                                    showBottomSheet.value = false
                                }
                            },
                            contentPadding = PaddingValues(vertical = 10.dp)
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Удалить") },
                            onClick = { showDialog = true },
                            contentPadding = PaddingValues(vertical = 10.dp)
                        )

                    }
                }
            }
        }
    }

    

    
    if (showDialog) {
        showDropdownMenu = false
        BasicAlertDialog(onDismissRequest = { showDialog = false }
        ) {
            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Column(
                    modifier = Modifier

                        .padding(bottom = 20.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "Вы действительно хотите удалить эту категорию?",
                        minLines = 2,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineMedium

                    )


                    Spacer(modifier = Modifier.height(30.dp))


                    Text(
                        text = "Удалятся все данные категории",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.surfaceTint
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            onClick = { showDialog = false }
                        ) {
                            Text(
                                text = "Нет",
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                        OutlinedButton(
                            onClick = {
                                deleteCategoryById(item.id)
                                showDialog = false
                            }
                        ) {
                            Text(
                                text = "Да",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.error
                            )

                        }
                    }

                }
            }

        }
    }
}


@Composable
fun ButtonItem(
    navigateTo: (Screen) -> Unit,
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
            navigateTo(Screen.AddCategory)
        }
    ) {

        Text(text = "Добавить категорию") }
}
