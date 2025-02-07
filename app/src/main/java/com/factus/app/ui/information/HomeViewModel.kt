package com.factus.app.ui.information

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.factus.app.domain.models.invoice.FactureItem
import com.factus.app.domain.repository.FactureRepository
import com.factus.app.domain.state.LoginResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.Base64
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val factureRepository: FactureRepository) :
    ViewModel() {

    private val _factureState =
        MutableStateFlow<LoginResult<List<FactureItem>>>(LoginResult.Loading(false))
    val factureState: StateFlow<LoginResult<List<FactureItem>>> = _factureState

    private val _downloadState = MutableStateFlow<LoginResult<File>>(LoginResult.Loading(false))
    val downloadState: StateFlow<LoginResult<File>> get() = _downloadState

    private val _pdfDownloadedEvent = MutableSharedFlow<Pair<String, File>>()
    val pdfDownloadedEvent = _pdfDownloadedEvent.asSharedFlow()

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()


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

    fun downloadInvoice(number: String, context: Context) {
        viewModelScope.launch {
            _downloadState.value = LoginResult.Loading(true)
            try {
                val result =
                    withContext(Dispatchers.IO) { factureRepository.downloadInvoice(number) }

                val responseBody = result.data
                if (responseBody?.status == "OK" && responseBody.data?.pdf_base_64_encoded != null) {
                    val pdfBytes = Base64.getDecoder().decode(responseBody.data.pdf_base_64_encoded)
                    val file = File(context.cacheDir, "${responseBody.data.file_name}.pdf")
                    file.writeBytes(pdfBytes)

                    val index = _factureState.value.data?.indexOfFirst { it.number == number }
                    if (index != null && index != -1) {
                        val updatedList = _factureState.value.data?.toMutableList()
                        updatedList?.get(index)?.fileUrl = file
                        _factureState.value = LoginResult.Success(updatedList ?: emptyList())
                    }
                    _downloadState.value = LoginResult.Success(file)
                    _pdfDownloadedEvent.emit(number to file)

                } else {
                    _downloadState.value = LoginResult.Error("La respuesta no contiene el PDF")
                }

            } catch (e: Exception) {
                _downloadState.value = LoginResult.Error(message = e.message ?: "Error")
            }
        }
    }

    fun openPdf(context: Context, file: File) {
        val uri = FileProvider.getUriForFile(
            context, "${context.packageName}.provider", file
        )

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/pdf")
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NO_HISTORY
        }

        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            viewModelScope.launch {
                _toastMessage.emit("No hay visor de PDF instalado")

            }
        }
    }

    fun sharePdf(context: Context, file: File) {
        try {
            val uri = FileProvider.getUriForFile(
                context, "${context.packageName}.provider", file
            )

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "application/pdf"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // Permiso de lectura
            }

            context.startActivity(Intent.createChooser(shareIntent, "Compartir PDF"))

        } catch (e: Exception) {
            Log.e("sharePdf", e.message.toString())
        }
    }
}