package com.brainbyte.healthareana.ui.step_up

import com.github.mikephil.charting.formatter.ValueFormatter

class DayAxisFormatter : ValueFormatter() {


    override fun getFormattedValue(value: Float): String {
        return when(value.toInt()) {
            1 -> "Mon"
            2 -> "Tue"
            3 -> "Wed"
            4 -> "Thu"
            5 -> "Fri"
            6 -> "Sat"
            else -> "Sun"
        }
    }
}