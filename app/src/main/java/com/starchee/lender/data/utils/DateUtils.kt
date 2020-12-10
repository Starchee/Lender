package com.starchee.lender.data.utils

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DateUtils {

    private val df = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")

    fun formatDate(date: String) =
        ZonedDateTime.parse(date).withZoneSameInstant(Calendar.getInstance().timeZone.toZoneId())
            .format(df)

}