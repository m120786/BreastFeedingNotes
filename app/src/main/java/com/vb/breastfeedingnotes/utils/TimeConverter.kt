package com.vb.breastfeedingnotes.utils

import java.text.SimpleDateFormat
import java.time.Duration
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*

class TimeConverter {

    fun convertTimeFromLongToString(time: Long?): String {
        val simpleDateFormat = SimpleDateFormat("HH:mm")
        val date = time?.let { Date(it) }
        val newTime = simpleDateFormat.format(date)
        return newTime
    }

    fun getDisplayValue(ms: Long?): String {
        val duration = ms?.let { Duration.ofMillis(it) }
        val hours =  duration?.toHours()
        val minutes = hours?.let { duration?.toMinutes() }
        val seconds = minutes?.let { duration?.minusMinutes(it)?.seconds }

        return "${minutes} min"
    }

}