package com.example.baseprojectflamingo.base.ext

import com.example.baseprojectflamingo.database.entities.RecordRealIncome
import com.example.baseprojectflamingo.ui.domain.RecordManage

fun RecordRealIncome.toRecordManage() =
    RecordManage(
        id = this.id,
        recordName = this.noteTitle,
        time = this.time,
        amount = this.revenue,
        imageRes = this.service.categoryParent.imageRes
    )

fun List<RecordRealIncome>.toMutableListRecordManage() =
    this.map { it.toRecordManage() }.toMutableList()