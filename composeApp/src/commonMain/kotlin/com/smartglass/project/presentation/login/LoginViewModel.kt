package com.smartglass.project.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartglass.project.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun handleIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.UpdateUsername -> updateUsername(intent.username)
            is LoginIntent.UpdatePassword -> updatePassword(intent.password)
            is LoginIntent.UpdateAutoLogin -> updateAutoLogin(intent.autoLogin)
            is LoginIntent.ClickLogin -> login()
            is LoginIntent.ClickFindId -> findId()
            is LoginIntent.ClickFindPassword -> findPassword()
        }
    }

    private fun updateUsername(username: String) {
        _state.update { it.copy(username = username, error = null) }
    }

    private fun updatePassword(password: String) {
        _state.update { it.copy(password = password, error = null) }
    }

    private fun updateAutoLogin(autoLogin: Boolean) {
        _state.update { it.copy(autoLogin = autoLogin) }
    }

    private fun login() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            
            val result = loginUseCase(
                loginId = _state.value.username,
                password = _state.value.password,
                autoLogin = _state.value.autoLogin
            )
            
            result.fold(
                onSuccess = { loginResult ->
                    _state.update { it.copy(isLoading = false) }
                    // TODO: 로그인 성공 처리
                    // - 토큰 저장
                    // - 사용자 정보 저장
                    // - 비밀번호 재설정 여부 확인 (loginResult.isPasswordReset)
                    // - 홈 화면으로 네비게이션
                    println("로그인 성공: ${loginResult.user.userName}")
                    println("AccessToken: ${loginResult.token.accessToken}")
                },
                onFailure = { error ->
                    _state.update { 
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "로그인에 실패했습니다"
                        )
                    }
                }
            )
        }
    }

    private fun findId() {
        // TODO: 아이디 찾기 로직 구현
    }

    private fun findPassword() {
        // TODO: 비밀번호 찾기 로직 구현
    }
}
