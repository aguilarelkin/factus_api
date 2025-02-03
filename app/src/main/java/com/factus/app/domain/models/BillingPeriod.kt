package com.factus.app.domain.models

import com.factus.app.data.response.BillingPeriodResponse

data class BillingPeriod(
    val start_date: String? = null,
    val start_time: String? = null,
    val end_date: String? = null,
    val end_time: String? = null
) {
    fun toDomain() = BillingPeriodResponse(
        start_date, start_time, end_date, end_time
    )
}
