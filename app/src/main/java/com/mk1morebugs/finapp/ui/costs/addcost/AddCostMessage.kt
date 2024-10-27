package com.mk1morebugs.finapp.ui.costs.addcost

import androidx.annotation.StringRes
import com.mk1morebugs.finapp.R

enum class AddCostMessage(@StringRes val stringResource: Int? = null) {
    CATEGORY_NOT_SELECTED(stringResource = R.string.category_not_selected),
    AMOUNT_IS_EMPTY(stringResource = R.string.amount_is_empty),
    AMOUNT_NOT_INT(stringResource = R.string.amount_no_int),
    AMOUNT_OVER_LIMIT(stringResource = R.string.amount_over_limit),
    OK(stringResource = R.string.add_cost_success)
}