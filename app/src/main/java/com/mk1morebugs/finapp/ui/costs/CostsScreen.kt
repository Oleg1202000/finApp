package com.mk1morebugs.finapp.ui.costs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mk1morebugs.finapp.R
import com.mk1morebugs.finapp.ui.graphdraw.ButtonGraph
import com.mk1morebugs.finapp.ui.graphdraw.CategoryDetails
import com.mk1morebugs.finapp.ui.graphdraw.GraphPeriod
import com.mk1morebugs.finapp.ui.graphdraw.PieChart
import com.mk1morebugs.finapp.ui.graphdraw.CostTable
import com.mk1morebugs.finapp.ui.theme.Shapes

@Composable
fun CostsScreen(
    viewModel: CostsViewModel = hiltViewModel(),
    finappStatusbarTitle: MutableState<String>,
    isFactCosts: Boolean,
) {
    finappStatusbarTitle.value = "Расходы"

    viewModel.switchTypeCosts(
        isFactCosts = isFactCosts
    )

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CostsScreenContent(
        categoriesDetails = uiState.categoriesDetails,
        contentIsLoading = uiState.isLoading,
        beginDate = uiState.beginDate,
        endDate = uiState.endDate,
        summaryAmount = uiState.sumAmount,
        updateDate = viewModel::updateDate,
        updateGraphPeriod = viewModel::updateGraphPeriod,
        updateCosts = viewModel::updateCosts

    )
}

@Composable
fun CostsScreenContent(
    categoriesDetails: List<CategoryDetails>,
    contentIsLoading: Boolean,
    beginDate: Long,
    endDate: Long,
    summaryAmount: Int,
    updateDate: (Int) -> Unit,
    updateGraphPeriod: (GraphPeriod) -> Unit,
    updateCosts: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                /*.swipeable(
                    state = swipeableState,
                    orientation = Orientation.Horizontal,
                    anchors = anchors
                )*/,
                shape = Shapes.small,
                shadowElevation = 4.dp
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    if (contentIsLoading) {
                        CircularProgressIndicator()
                    } else if (categoriesDetails.isEmpty()) {
                        Text(text = stringResource(R.string.no_records_for_selected_period))
                    } else {
                        PieChart(
                            modifier = Modifier
                               // .padding(20.dp)
                                .size(280.dp),
                            pieChartItems = categoriesDetails,
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(5.dp))
        }

        item {
            ButtonGraph(
                modifier = Modifier.fillMaxWidth(0.9f),
                beginDate = beginDate,
                endDate = endDate,
                updateDate = updateDate,
                updateDataGraph = updateCosts,
                updateGraphPeriod = updateGraphPeriod
            )
        }


        item {
            Spacer(modifier = Modifier.height(20.dp))
        }

        item {
            CostTable(
                modifier = Modifier.fillMaxWidth(0.9f),
                detailData = categoriesDetails,
                sumIncome = summaryAmount
            )
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}