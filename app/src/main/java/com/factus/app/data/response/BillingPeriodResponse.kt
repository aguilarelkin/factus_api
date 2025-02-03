package com.factus.app.data.response

import com.factus.app.domain.models.BillingPeriod
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class BillingPeriodResponse(
    @SerializedName("start_date") val start_date: String? = null,
    @SerializedName("start_time") val start_time: String? = null,
    @SerializedName("end_date") val end_date: String? = null,
    @SerializedName("end_time") val end_time: String? = null
) {
    fun toDomain() = BillingPeriod(start_date, start_time, end_date, end_time)
}
