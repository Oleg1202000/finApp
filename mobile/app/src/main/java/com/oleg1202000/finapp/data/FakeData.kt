package com.oleg1202000.finapp.data


class FakeData {
    private val fakeCategories = listOf<Categories>(
        Categories(1, "Fake 1", false),
        Categories(2, "Fake 2", true),
        Categories(3, "Fake 3", false),
        Categories(4, "Fake 4", true),
        Categories(5, "Fake 5", true),
    )

    fun getCategories() = fakeCategories
}
