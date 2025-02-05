package com.factus.app.ui.information

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.factus.app.domain.models.invoice.FactureItem
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
class HomeViewModel @Inject constructor(private val factureRepository: FactureRepository) :
    ViewModel() {

    private val _factureState =
        MutableStateFlow<LoginResult<List<FactureItem>>>(LoginResult.Loading(false))
    val factureState: StateFlow<LoginResult<List<FactureItem>>> = _factureState

    private fun <T> handleRequest(
        request: suspend () -> LoginResult<T>, stateFlow: MutableStateFlow<LoginResult<T>>
    ) = viewModelScope.launch {
        stateFlow.value = LoginResult.Loading(true)
        try {
            val result = withContext(Dispatchers.IO) { request() }
            stateFlow.value = result
        } catch (e: Exception) {
            stateFlow.value = LoginResult.Error(message = e.message ?: "Error")
        }
    }

    fun getInvoice(identification: String) {
        handleRequest(
            request = { factureRepository.getInvoice(identification) }, stateFlow = _factureState
        )
    }
}