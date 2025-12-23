package com.smartglass.project.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartglass.project.data.local.PreferencesManager
import com.smartglass.project.domain.model.AuthException
import com.smartglass.project.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * 로그인 화면 ViewModel
 * features_spec.md: 2. 로그인 화면 사용자 행동 및 앱 반응 참고
 */
class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val preferencesManager: PreferencesManager
) : ViewModel() {
    private val _state = MutableStateFlow<LoginState>(
        LoginState.Idle(isDeviceRegistered = preferencesManager.isDeviceRegistered())
    )
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun handleIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.UpdateUsername -> updateUsername(intent.username)
            is LoginIntent.UpdatePassword -> updatePassword(intent.password)
            is LoginIntent.UpdateAutoLogin -> updateAutoLogin(intent.autoLogin)
            is LoginIntent.TogglePasswordVisibility -> togglePasswordVisibility()
            
            is LoginIntent.OnFieldFocused -> onFieldFocused()
            
            is LoginIntent.ClickLogin -> login()
            is LoginIntent.ClickQrLogin -> qrLogin()
            
            is LoginIntent.ClickDeviceRegistration -> navigateToQrScan()
            is LoginIntent.DeviceRegistrationSuccess -> onDeviceRegistrationSuccess()
            is LoginIntent.DeviceRegistrationCancelled -> onDeviceRegistrationCancelled()
            
            is LoginIntent.ClickFindId -> showFindAccountPopup()
            is LoginIntent.ClickFindPassword -> showFindAccountPopup()
            is LoginIntent.CloseFindAccountPopup -> closeFindAccountPopup()
            
            is LoginIntent.ConfirmDuplicateLogin -> loginWithDuplicateAllowed()
            is LoginIntent.CancelDuplicateLogin -> cancelDuplicateLogin()
            
            is LoginIntent.NavigateToHome -> {}
            is LoginIntent.NavigateToPasswordReset -> {}
            is LoginIntent.NavigateToQrScan -> {}
        }
    }
    
    // ========== 입력 업데이트 ==========
    
    private fun updateUsername(username: String) {
        val currentState = _state.value
        if (currentState is LoginState.Idle) {
            _state.value = currentState.copy(username = username)
        }
    }
    
    private fun updatePassword(password: String) {
        val currentState = _state.value
        if (currentState is LoginState.Idle) {
            _state.value = currentState.copy(password = password)
        }
    }
    
    private fun updateAutoLogin(autoLogin: Boolean) {
        val currentState = _state.value
        if (currentState is LoginState.Idle) {
            _state.value = currentState.copy(autoLogin = autoLogin)
        }
    }
    
    private fun togglePasswordVisibility() {
        val currentState = _state.value
        if (currentState is LoginState.Idle) {
            _state.value = currentState.copy(showPassword = !currentState.showPassword)
        }
    }
    
    // ========== 디바이스 등록 체크 ==========
    
    private fun onFieldFocused() {
        val currentState = _state.value
        if (currentState is LoginState.Idle && !currentState.isDeviceRegistered) {
            _state.value = LoginState.DeviceRegistrationRequired
        }
    }
    
    private fun onDeviceRegistrationSuccess() {
        val currentState = _state.value
        if (currentState is LoginState.Idle) {
            _state.value = currentState.copy(isDeviceRegistered = true)
        } else {
            _state.value = LoginState.Idle(isDeviceRegistered = true)
        }
    }
    
    private fun onDeviceRegistrationCancelled() {
        _state.value = LoginState.Idle(isDeviceRegistered = false)
    }
    
    // ========== 네비게이션 ==========
    
    private fun navigateToQrScan() {
        _state.value = LoginState.NavigateToQrScan
    }
    
    // ========== 계정 찾기 팝업 ==========
    
    private fun showFindAccountPopup() {
        _state.value = LoginState.ShowFindAccountPopup
    }
    
    private fun closeFindAccountPopup() {
        val currentState = _state.value
        if (currentState is LoginState.Idle) {
            _state.value = currentState
        } else {
            _state.value = LoginState.Idle(
                isDeviceRegistered = preferencesManager.isDeviceRegistered()
            )
        }
    }
    
    // ========== 로그인 ==========
    
    private fun login() {
        val currentState = _state.value
        if (currentState is LoginState.Idle) {
            _state.value = LoginState.LoginInProgress
            
            viewModelScope.launch {
                // appId는 PreferencesManager에서 가져오기 (디바이스 등록 후 발급받은 값)
                val appId = preferencesManager.getAppId()
                val result = loginUseCase(
                    loginId = currentState.username,
                    password = currentState.password,
                    autoLogin = currentState.autoLogin,
                    appId = appId,  // null일 수 있음 (api_spec.md에 따르면 nullable)
                    allowDuplicateLogin = false
                )
                
                result.fold(
                    onSuccess = { loginResult ->
                        // 비밀번호 재설정 필요 여부 확인
                        if (loginResult.isPasswordReset == true) {
                            _state.value = LoginState.NavigateToPasswordReset
                        } else {
                            _state.value = LoginState.NavigateToHome
                        }
                    },
                    onFailure = { error ->
                        // 중복 로그인 에러 처리
                        when (error) {
                            is AuthException.DuplicateLogin -> {
                                _state.value = LoginState.DuplicateLogin
                            }
                            is AuthException.Other -> {
                                _state.value = LoginState.LoginFailure(
                                    errorCode = error.errorCode,
                                    message = error.message
                                )
                                
                                viewModelScope.launch {
                                    kotlinx.coroutines.delay(3000)
                                    _state.value = LoginState.Idle(
                                        username = currentState.username,
                                        password = currentState.password,
                                        autoLogin = currentState.autoLogin,
                                        isDeviceRegistered = currentState.isDeviceRegistered
                                    )
                                }
                            }
                            else -> {
                                _state.value = LoginState.LoginFailure(
                                    errorCode = null,
                                    message = error.message
                                )
                                
                                viewModelScope.launch {
                                    kotlinx.coroutines.delay(3000)
                                    _state.value = LoginState.Idle(
                                        username = currentState.username,
                                        password = currentState.password,
                                        autoLogin = currentState.autoLogin,
                                        isDeviceRegistered = currentState.isDeviceRegistered
                                    )
                                }
                            }
                        }
                    }
                )
            }
        }
    }
    
    private fun qrLogin() {
        _state.value = LoginState.NavigateToQrScan
    }
    
    // ========== 중복 로그인 처리 ==========
    
    private fun loginWithDuplicateAllowed() {
        val currentState = _state.value
        if (currentState is LoginState.Idle) {
            _state.value = LoginState.LoginInProgress
            
            viewModelScope.launch {
                // appId는 PreferencesManager에서 가져오기 (디바이스 등록 후 발급받은 값)
                val appId = preferencesManager.getAppId()
                val result = loginUseCase(
                    loginId = currentState.username,
                    password = currentState.password,
                    autoLogin = currentState.autoLogin,
                    appId = appId,  // null일 수 있음 (api_spec.md에 따르면 nullable)
                    allowDuplicateLogin = true
                )
                
                result.fold(
                    onSuccess = { loginResult ->
                        _state.value = LoginState.NavigateToHome
                    },
                    onFailure = { error ->
                        _state.value = LoginState.LoginFailure(
                            errorCode = null,
                            message = error.message
                        )
                        
                        viewModelScope.launch {
                            kotlinx.coroutines.delay(3000)
                            _state.value = LoginState.Idle(
                                username = currentState.username,
                                password = currentState.password,
                                autoLogin = currentState.autoLogin,
                                isDeviceRegistered = currentState.isDeviceRegistered
                            )
                        }
                    }
                )
            }
        }
    }
    
    private fun cancelDuplicateLogin() {
        val currentState = _state.value
        if (currentState is LoginState.Idle) {
            _state.value = currentState
        } else {
            _state.value = LoginState.Idle(
                isDeviceRegistered = preferencesManager.isDeviceRegistered()
            )
        }
    }
}
