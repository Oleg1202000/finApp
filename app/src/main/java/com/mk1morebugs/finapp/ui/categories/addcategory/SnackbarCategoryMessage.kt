package com.mk1morebugs.finapp.ui.categories.addcategory

import androidx.annotation.StringRes
import com.mk1morebugs.finapp.R

enum class SnackbarCategoryMessage(@StringRes val stringResource: Int) {
    NameNotUnique(stringResource = R.string.non_unique_name),
    IconNotSelected(stringResource = R.string.icon_not_selected),
    ColorNotSelected(stringResource = R.string.color_not_selected),
    OK(stringResource = R.string.add_category_is_success)
}