package com.factus.app.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.factus.app.domain.models.Token
import com.factus.app.domain.repository.DataStoreRepository
import com.factus.app.domain.repository.LoginRepository
import com.factus.app.domain.state.LoginResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    //val tokenData: LiveData<Token?> = repository.tokenDataFlow().asLiveData()
    private val _loginState = MutableStateFlow<LoginResult<Token>>(
        LoginResult.Loading(false)
    )
    val loginState: StateFlow<LoginResult<Token>> = _loginState

    private val _loginUser = MutableStateFlow<Boolean>(false)
    val loginUser: StateFlow<Boolean> = _loginUser

    init {
        userLogin()
    }

    fun loginFactusApi(
        grantType: String,
        clientId: String,
        clientSecret: String,
        username: String,
        password: String
    ) {
        viewModelScope.launch {
            try {
                _loginState.value = LoginResult.Loading(true)
                val result: LoginResult<Token> = withContext(Dispatchers.IO) {
                    loginRepository.singInFactus(
                        grantType, clientId, clientSecret, username, password
                    )
                }
                _loginState.value = result
            } catch (e: Exception) {
                _loginState.value = LoginResult.Error(e.message ?: "Error")
            }

        }
    }

    fun userLogin() {
        viewModelScope.launch {
            val data: Token? = withContext(Dispatchers.IO) {
                dataStoreRepository.tokenDataFlow().firstOrNull()
            }/*dataStoreRepository.tokenDataFlow().firstOrNull()?.let { savedToken ->
                println("Token guardado y obtenido: $savedToken")
            } ?: println("No se encontró ningún token guardado.")*/
            if (data != null) {
                _loginUser.value = data.access_token.isNotBlank()
            }
        }
    }

    fun refreshTokenApi(
        grantType: String, clientId: String, clientSecret: String
    ) {
        viewModelScope.launch {
            try {
                _loginState.value = LoginResult.Loading(true)
                val refreshTokenData: Token? = withContext(Dispatchers.IO) {
                    dataStoreRepository.tokenDataFlow().firstOrNull()
                }
                val result: LoginResult<Token> = withContext(Dispatchers.IO) {
                    loginRepository.refreshToken(
                        grantType, clientId, clientSecret, refreshTokenData!!.refresh_token
                    )
                }
                _loginState.value = result
            } catch (e: Exception) {
                _loginState.value = LoginResult.Error(e.message ?: "Error")
            }

        }
    }

}