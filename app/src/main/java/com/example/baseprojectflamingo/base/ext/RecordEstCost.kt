package com.example.baseprojectflamingo.base.ext

import com.example.baseprojectflamingo.database.entities.RecordEstimateCost
import com.example.baseprojectflamingo.ui.domain.RecordManage
import com.example.baseprojectflamingo.ui.domain.RecordManage.Companion.RECORD_EST_COST

fun RecordEstimateCost.toRecordManage() =
    RecordManage(
        id = this.id,
        recordName = this.noteTitle,
        time = this.time,
        amount = this.cost,
        typeRecord = RECORD_EST_COST,
        imageRes = this.categoryEstimateCost.categoryParent.imageRes
    )

fun List<RecordEstimateCost>.toMutableListRecordManage() =
    this.map { it.toRecordManage() }.toMutableList()