package com.factus.app.domain.models

import java.time.LocalDate

data class BillingPeriod(
    val start_date: LocalDate = LocalDate.now(),
    val start_time: String = "00:00:00",
    val end_date: LocalDate = LocalDate.now(),
    val end_time: String = "23:59:59"
)
