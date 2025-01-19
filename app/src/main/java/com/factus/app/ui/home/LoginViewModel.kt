package com.factus.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.factus.app.domain.models.Token
import com.factus.app.domain.repository.LoginRepository
import com.factus.app.domain.state.LoginResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) :
    ViewModel() {
    private val _loginState = MutableStateFlow<LoginResult<Token>>(LoginResult.Loading())
    val loginState: StateFlow<LoginResult<Token>> = _loginState

    fun loginFactusApi(
        grantType: String,
        clientId: String,
        clientSecret: String,
        username: String,
        password: String
    ) {
        viewModelScope.launch {
            _loginState.value = LoginResult.Loading()
            val result: LoginResult<Token> = withContext(Dispatchers.IO) {
                loginRepository.singInFactus(grantType, clientId, clientSecret, username, password)
            }
            _loginState.value = result
        }
    }

}