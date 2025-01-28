package com.factus.app.domain.repository

import com.factus.app.domain.models.Location
import com.factus.app.domain.models.Measurement
import com.factus.app.domain.models.Numbering
import com.factus.app.domain.models.Tribute
import com.factus.app.domain.state.LoginResult

interface FactureRepository {
    suspend fun getNumberingRanges(): LoginResult<List<Numbering>>
    suspend fun getUnitsMeasurement(): LoginResult<List<Measurement>>
    suspend fun getLocations(): LoginResult<List<Location>>
    suspend fun getTributes(): LoginResult<List<Tribute>>
}