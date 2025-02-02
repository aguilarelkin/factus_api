package com.factus.app.domain.models

import java.time.LocalDate

data class BillingPeriod(
    val start_date: LocalDate? = null,
    val start_time: String? = null,
    val end_date: LocalDate? = null,
    val end_time: String? = null
)
