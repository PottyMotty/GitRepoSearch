package com.pottymotty.gitreposearch.util

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

enum class DateTimeFormatPattern(val pattern: String){
    DATE_WITH_MONTH_TEXT("yyyy. MMM dd.")
}

fun Instant.toFormattedString(dateTimePattern: DateTimeFormatPattern): String {
    val formatter = DateTimeFormatter.ofPattern(dateTimePattern.pattern, Locale.getDefault())
    return formatter.format(LocalDateTime.ofInstant(this, ZoneId.of("UTC")))
}

fun createInstant(year: Int, month: Int, dayOfMonth: Int): Instant {
    val localDate = LocalDate.of(year, month, dayOfMonth)
    return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()
}