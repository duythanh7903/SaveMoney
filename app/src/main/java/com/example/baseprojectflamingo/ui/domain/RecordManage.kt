package com.example.baseprojectflamingo.ui.domain

import com.example.baseprojectflamingo.R

data class RecordManage(
    var id: Long = 0L,
    var recordName: String = "",
    var time: Long = System.currentTimeMillis(),
    var amount: Long = 0L,
    var imageRes: Int = R.drawable.icon_cate_water,
    var typeRecord: Int = RECORD_REAL_INCOME
) {

    companion object {
        internal const val RECORD_REAL_INCOME = 0
        internal const val RECORD_ACTUAL_COST = 1
        internal const val RECORD_EXPECTED_INCOME = 2
        internal const val RECORD_EST_COST = 3
    }

}