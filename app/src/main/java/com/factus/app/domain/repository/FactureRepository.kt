package com.factus.app.domain.repository

import com.factus.app.data.response.FactureResponse
import com.factus.app.domain.models.Facture
import com.factus.app.domain.models.Location
import com.factus.app.domain.models.Measurement
import com.factus.app.domain.models.Numbering
import com.factus.app.domain.models.Tribute
import com.factus.app.domain.models.invoice.FactureItem
import com.factus.app.domain.models.pdf.Pdf
import com.factus.app.domain.state.LoginResult

interface FactureRepository {
    suspend fun getNumberingRanges(): LoginResult<List<Numbering>>
    suspend fun getUnitsMeasurement(): LoginResult<List<Measurement>>
    suspend fun getLocations(): LoginResult<List<Location>>
    suspend fun getTributes(): LoginResult<List<Tribute>>
    suspend fun createFacture(facture: FactureResponse): LoginResult<Facture>
    suspend fun getInvoice(identification: String): LoginResult<List<FactureItem>>
    suspend fun downloadInvoice(number: String): LoginResult<Pdf>
}