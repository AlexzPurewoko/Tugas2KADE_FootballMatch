package com.apwdevs.footballMatch.utility

import java.text.SimpleDateFormat
import java.util.*

object MyDate {
    fun getDate(strDate: String?, pattern: String): CharSequence? {
        if (strDate == null) return null
        val calendar = Calendar.getInstance()
        calendar.time = SimpleDateFormat(pattern, Locale.getDefault()).parse(strDate)
        val day = when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> "Sun"
            Calendar.MONDAY -> "Mon"
            Calendar.THURSDAY -> "Thu"
            Calendar.WEDNESDAY -> "Wed"
            Calendar.SATURDAY -> "Sat"
            Calendar.TUESDAY -> "Tue"
            Calendar.FRIDAY -> "Fri"
            else -> "nan"
        }
        val month = when (calendar.get(Calendar.MONTH)) {
            Calendar.JANUARY -> "Jan"
            Calendar.FEBRUARY -> "Feb"
            Calendar.MARCH -> "March"
            Calendar.APRIL -> "April"
            Calendar.MAY -> "May"
            Calendar.JUNE -> "June"
            Calendar.JULY -> "July"
            Calendar.AUGUST -> "August"
            Calendar.SEPTEMBER -> "Sep"
            Calendar.OCTOBER -> "Oct"
            Calendar.NOVEMBER -> "Nov"
            Calendar.DECEMBER -> "Dec"
            else -> "nan"
        }
        val date = calendar.get(Calendar.DAY_OF_MONTH)
        val year = calendar.get(Calendar.YEAR)

        return "$day, $date $month $year"
    }
}