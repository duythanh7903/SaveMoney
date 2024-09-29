package com.example.baseprojectflamingo.commons

import com.example.baseprojectflamingo.R
import com.example.baseprojectflamingo.database.entities.ParentCategory
import com.example.baseprojectflamingo.database.entities.ParentCategory.Companion.TYPE_EXPENSE
import com.example.baseprojectflamingo.database.entities.ParentCategory.Companion.TYPE_INCOME
import com.example.baseprojectflamingo.ui.domain.Language

object Commons {
    fun getAllParentCategory() = mutableListOf(
        ParentCategory(0, "Birthday", R.drawable.icon_cate_bag, TYPE_EXPENSE),
        ParentCategory(0, "Children day", R.drawable.icon_cate_nurses, TYPE_EXPENSE),
        ParentCategory(0, "Grandparents", R.drawable.icon_cate_house, TYPE_EXPENSE),
        ParentCategory(0, "Investment", R.drawable.icon_cate_water, TYPE_EXPENSE),
        ParentCategory(0, "New year", R.drawable.icon_cate_beauty, TYPE_EXPENSE),
        ParentCategory(0, "Other", R.drawable.icon_cate_water, TYPE_EXPENSE),
        ParentCategory(0, "Parents", R.drawable.icon_cate_hawaiian_shirt, TYPE_EXPENSE),
        ParentCategory(0, "Salary", R.drawable.icon_cate_diet, TYPE_EXPENSE),
        ParentCategory(0, "Saving", R.drawable.icon_cate_water, TYPE_EXPENSE),
        ParentCategory(0, "Study bonus", R.drawable.icon_cate_studying, TYPE_EXPENSE),

        ParentCategory(0, "Activity", R.drawable.father, TYPE_INCOME),
        ParentCategory(0, "Assessories", R.drawable.dollar, TYPE_INCOME),
        ParentCategory(0, "Birthday", R.drawable.handsome, TYPE_INCOME),
        ParentCategory(0, "Clothes", R.drawable.team, TYPE_INCOME),
        ParentCategory(0, "Foods", R.drawable.lotus, TYPE_INCOME),
        ParentCategory(0, "Other", R.drawable.salary, TYPE_INCOME),
        ParentCategory(0, "Phone expenses", R.drawable.coding, TYPE_INCOME),
        ParentCategory(0, "Study fees", R.drawable.mother, TYPE_INCOME),
        ParentCategory(0, "Taxi", R.drawable.salary, TYPE_INCOME)
    )

    fun getAllLanguages() = mutableListOf(
        Language(0, R.string.vietnamese, R.drawable.vietnam, "vi"),
        Language(1, R.string.english, R.drawable.english, "en"),
        Language(2, R.string.hindi, R.drawable.hindi, "hi"),
        Language(3, R.string.portugal, R.drawable.portugal, "pt"),
    )
}