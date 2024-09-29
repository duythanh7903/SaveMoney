package com.example.baseprojectflamingo.ui.screen.frg.chart

import com.anychart.chart.common.dataentry.ValueDataEntry

class CustomDataEntry(x: String?, value: Number?, value2: Number?, value3: Number?, value4: Number? = 12) :
    ValueDataEntry(x, value) {
    init {
        setValue("value2", value2)
        setValue("value3", value3)
        setValue("value4", value4)
    }
}