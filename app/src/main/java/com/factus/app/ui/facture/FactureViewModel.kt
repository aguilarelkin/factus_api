package com.factus.app.ui.facture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.factus.app.domain.models.Facture
import com.factus.app.domain.models.Location
import com.factus.app.domain.models.Measurement
import com.factus.app.domain.models.Numbering
import com.factus.app.domain.models.Tribute
import com.factus.app.domain.repository.DataStoreRepository
import com.factus.app.domain.repository.FactureRepository
import com.factus.app.domain.state.LoginResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FactureViewModel @Inject constructor(
    private val factureRepository: FactureRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    private val _numberingRangesState =
        MutableStateFlow<LoginResult<List<Numbering>>>(LoginResult.Loading(false))
    private val _unitsMeasurementState =
        MutableStateFlow<LoginResult<List<Measurement>>>(LoginResult.Loading(false))
    private val _locationsState =
        MutableStateFlow<LoginResult<List<Location>>>(LoginResult.Loading(false))
    private val _tributesState =
        MutableStateFlow<LoginResult<List<Tribute>>>(LoginResult.Loading(false))
    private val _factureState = MutableStateFlow<LoginResult<Facture>>(LoginResult.Loading(false))

    val numberingRangesState: StateFlow<LoginResult<List<Numbering>>> = _numberingRangesState
    val unitsMeasurementState: StateFlow<LoginResult<List<Measurement>>> = _unitsMeasurementState
    val locationsState: StateFlow<LoginResult<List<Location>>> = _locationsState
    val tributesState: StateFlow<LoginResult<List<Tribute>>> = _tributesState
    val factureState: StateFlow<LoginResult<Facture>> = _factureState


    init {
        fetchNumberingRanges()
        fetchUnitsMeasurement()
        fetchLocations()
        fetchTributes()
    }

    private fun <T> handleRequest(
        request: suspend () -> LoginResult<T>, stateFlow: MutableStateFlow<LoginResult<T>>
    ) = viewModelScope.launch {
        stateFlow.value = LoginResult.Loading(true)
        try {
            val result = withContext(Dispatchers.IO) { request() }
            stateFlow.value = result
        } catch (e: Exception) {
            stateFlow.value = LoginResult.Error(message = e.message ?: "Error")
        }/* finally {
            stateFlow.value = LoginResult.Loading(false)
        }*/
    }

    private fun fetchNumberingRanges() {
        handleRequest(
            request = { factureRepository.getNumberingRanges() }, stateFlow = _numberingRangesState
        )
    }

    private fun fetchUnitsMeasurement() {
        handleRequest(
            request = { factureRepository.getUnitsMeasurement() },
            stateFlow = _unitsMeasurementState
        )
    }

    private fun fetchLocations() {
        handleRequest(
            request = { factureRepository.getLocations() }, stateFlow = _locationsState
        )
    }

    private fun fetchTributes() {
        handleRequest(
            request = { factureRepository.getTributes() }, stateFlow = _tributesState
        )
    }

    fun createdFacture(facture: Facture) {
        handleRequest(
            request = { factureRepository.createFacture(facture.toDomain()) },
            stateFlow = _factureState
        )
    }
}