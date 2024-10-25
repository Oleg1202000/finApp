package com.mk1morebugs.finapp.ui.costs

import java.util.Calendar
import java.util.TimeZone

fun calculateDate(
    delta: Int,
    graphPeriod: GraphPeriod
): Array<Long> {

    val beginDate: Long
    val endDate: Long
    val currentDate : Calendar = Calendar.getInstance(TimeZone.getDefault())

    when (graphPeriod) {

        GraphPeriod.DAY -> {
            currentDate.add(Calendar.DAY_OF_YEAR, delta)

            currentDate.set(Calendar.HOUR_OF_DAY, 0)
            currentDate.set(Calendar.MINUTE, 0)
            currentDate.set(Calendar.SECOND, 0)
            currentDate.set(Calendar.MILLISECOND, 0)
            beginDate = currentDate.timeInMillis


            currentDate.set(Calendar.HOUR_OF_DAY, 23)
            currentDate.set(Calendar.MINUTE, 59)
            currentDate.set(Calendar.SECOND, 59)
            currentDate.set(Calendar.MILLISECOND, 999)
            endDate = currentDate.timeInMillis
        }

        GraphPeriod.WEEK -> {
            currentDate.add(Calendar.WEEK_OF_YEAR, delta)

            currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            currentDate.set(Calendar.HOUR_OF_DAY, 0)
            currentDate.set(Calendar.MINUTE, 0)
            currentDate.set(Calendar.SECOND, 0)
            currentDate.set(Calendar.MILLISECOND, 0)
            beginDate = currentDate.timeInMillis

            currentDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
            currentDate.set(Calendar.HOUR_OF_DAY, 23)
            currentDate.set(Calendar.MINUTE, 59)
            currentDate.set(Calendar.SECOND, 59)
            currentDate.set(Calendar.MILLISECOND, 999)
            endDate = currentDate.timeInMillis
        }

        GraphPeriod.MONTH -> {
            currentDate.add(Calendar.MONTH, delta)

            currentDate.set(Calendar.DAY_OF_MONTH, 1)
            currentDate.set(Calendar.HOUR_OF_DAY, 0)
            currentDate.set(Calendar.MINUTE, 0)
            currentDate.set(Calendar.SECOND, 0)
            currentDate.set(Calendar.MILLISECOND, 0)
            beginDate = currentDate.timeInMillis


            currentDate.set(Calendar.DAY_OF_MONTH, currentDate.getActualMaximum(Calendar.DAY_OF_MONTH))
            currentDate.set(Calendar.HOUR_OF_DAY, 23)
            currentDate.set(Calendar.MINUTE, 59)
            currentDate.set(Calendar.SECOND, 59)
            currentDate.set(Calendar.MILLISECOND, 999)
            endDate = currentDate.timeInMillis
        }
    }
    return arrayOf(beginDate, endDate)
}