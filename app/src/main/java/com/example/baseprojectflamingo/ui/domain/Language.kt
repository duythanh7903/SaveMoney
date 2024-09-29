package com.example.baseprojectflamingo.ui.domain

import com.example.baseprojectflamingo.R

data class Language(
    var id: Long = 0L,
    var countryNameRes: Int = R.string.vietnamese,
    var countriesFlag: Int = R.drawable.vietnam,
    var code: String = "vi"
) {
}