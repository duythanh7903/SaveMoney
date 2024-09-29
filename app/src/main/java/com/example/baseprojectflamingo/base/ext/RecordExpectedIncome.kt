package com.example.baseprojectflamingo.base.ext

import com.example.baseprojectflamingo.database.entities.RecordExpectedIncome
import com.example.baseprojectflamingo.ui.domain.RecordManage
import com.example.baseprojectflamingo.ui.domain.RecordManage.Companion.RECORD_EXPECTED_INCOME

fun RecordExpectedIncome.toRecordManage() =
    RecordManage(
        id = this.id,
        recordName = this.noteTitle,
        time = this.time,
        amount = this.revenue,
        typeRecord = RECORD_EXPECTED_INCOME,
        imageRes = this.service.categoryParent.imageRes
    )

fun List<RecordExpectedIncome>.toMutableListRecordManage() =
    this.map { it.toRecordManage() }.toMutableList()