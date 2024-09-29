package com.example.baseprojectflamingo.base.ext

import com.example.baseprojectflamingo.database.entities.RecordActualCost
import com.example.baseprojectflamingo.ui.domain.RecordManage
import com.example.baseprojectflamingo.ui.domain.RecordManage.Companion.RECORD_ACTUAL_COST

fun RecordActualCost.toRecordManage() =
    RecordManage(
        id = this.id,
        recordName = this.noteTitle,
        time = this.time,
        amount = this.cost,
        typeRecord = RECORD_ACTUAL_COST,
        imageRes = this.categoryActualCost.categoryParent.imageRes
    )

fun List<RecordActualCost>.toMutableListRecordManage() =
    this.map { it.toRecordManage() }.toMutableList()