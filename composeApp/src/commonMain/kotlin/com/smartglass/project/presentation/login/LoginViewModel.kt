package com.smartglass.project.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<LoginState>(
        LoginState.Idle(isDeviceRegistered = false) // TODO: 실제 디바이스 등록 여부 확인
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
            // features_spec.md: Input: 디바이스 미등록 상태에서 입력 필드 포커스
            // Output: 디바이스 등록 팝업 표시
            _state.value = LoginState.DeviceRegistrationRequired
        }
    }
    
    private fun onDeviceRegistrationSuccess() {
        // 디바이스 등록 성공 후 Idle 상태로 복귀
        val currentState = _state.value
        if (currentState is LoginState.Idle) {
            _state.value = currentState.copy(isDeviceRegistered = true)
        } else {
            _state.value = LoginState.Idle(isDeviceRegistered = true)
        }
    }
    
    private fun onDeviceRegistrationCancelled() {
        // 디바이스 등록 취소 시 Idle 상태로 복귀
        _state.value = LoginState.Idle(isDeviceRegistered = false)
    }
    
    private fun navigateToQrScan() {
        _state.value = LoginState.NavigateToQrScan
    }
    
    // ========== 로그인 ==========
    
    private fun login() {
        val currentState = _state.value
        if (currentState !is LoginState.Idle) return
        
        viewModelScope.launch {
            _state.value = LoginState.LoginInProgress
            
            // TODO: 실제 appId 가져오기 (로컬 스토리지에서)
            val appId = "test-app-id"
            
            val result = loginUseCase(
                loginId = currentState.username,
                password = currentState.password,
                autoLogin = currentState.autoLogin,
                appId = appId,
                allowDuplicateLogin = false
            )
            
            result.fold(
                onSuccess = { loginResult ->
                    // features_spec.md: 비밀번호 재설정 필요 시 비밀번호 변경 화면으로 이동
                    if (loginResult.isPasswordReset) {
                        _state.value = LoginState.NavigateToPasswordReset
                    } else {
                        _state.value = LoginState.NavigateToHome
                    }
                    
                    // TODO: 토큰 저장, 사용자 정보 저장 (로컬 스토리지)
                },
                onFailure = { error ->
                    val errorMessage = error.message ?: "로그인에 실패했습니다"
                    
                    // features_spec.md: 중복 로그인 (코드 1018)
                    if (errorMessage.contains("1018")) {
                        _state.value = LoginState.DuplicateLogin
                    } else {
                        _state.value = LoginState.LoginFailure(
                            errorCode = null,
                            message = errorMessage
                        )
                        
                        // 3초 후 Idle 상태로 복귀
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
    
    private fun loginWithDuplicateAllowed() {
        val currentState = _state.value
        if (currentState is LoginState.Idle) {
            // 중복 로그인 허용하고 재시도
            viewModelScope.launch {
                _state.value = LoginState.LoginInProgress
                
                val appId = "test-app-id"
                
                val result = loginUseCase(
                    loginId = currentState.username,
                    password = currentState.password,
                    autoLogin = currentState.autoLogin,
                    appId = appId,
                    allowDuplicateLogin = true
                )
                
                result.fold(
                    onSuccess = { loginResult ->
                        if (loginResult.isPasswordReset) {
                            _state.value = LoginState.NavigateToPasswordReset
                        } else {
                            _state.value = LoginState.NavigateToHome
                        }
                    },
                    onFailure = { error ->
                        _state.value = LoginState.LoginFailure(
                            errorCode = null,
                            message = error.message ?: "로그인에 실패했습니다"
                        )
                    }
                )
            }
        }
    }
    
    private fun cancelDuplicateLogin() {
        // 중복 로그인 취소 시 Idle 상태로 복귀
        val currentState = _state.value
        if (currentState is LoginState.Idle) {
            _state.value = currentState
        } else {
            _state.value = LoginState.Idle()
        }
    }
    
    // ========== QR 로그인 ==========
    
    private fun qrLogin() {
        // QR 코드 스캔 화면으로 이동
        _state.value = LoginState.NavigateToQrScan
    }
    
    // ========== 계정 찾기 ==========
    
    private fun showFindAccountPopup() {
        _state.value = LoginState.ShowFindAccountPopup
    }
    
    private fun closeFindAccountPopup() {
        // 계정 찾기 팝업 닫기 후 Idle 상태로 복귀
        _state.value = LoginState.Idle()
    }
}
