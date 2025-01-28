package com.factus.app.domain.models

import java.time.LocalDate
import java.time.LocalTime

data class BillingPeriod(
    val start_date: LocalDate,
    val start_time: LocalTime,
    val end_date: LocalDate,
    val end_time: LocalTime
)
