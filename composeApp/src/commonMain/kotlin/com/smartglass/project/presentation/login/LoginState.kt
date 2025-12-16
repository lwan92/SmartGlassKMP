package com.smartglass.project.presentation.login

/**
 * 로그인 화면의 상태 정의
 * features_spec.md 2. 로그인 화면 상태 기준
 */
sealed class LoginState {
    data class Idle(
        val username: String = "",
        val password: String = "",
        val autoLogin: Boolean = false,
        val showPassword: Boolean = false,
        val isDeviceRegistered: Boolean = false
    ) : LoginState() {
        val isLoginEnabled: Boolean
            get() = username.isNotBlank() && password.isNotBlank() && isDeviceRegistered
    }
    
    object DeviceRegistrationRequired : LoginState()
    object DeviceRegistrationInProgress : LoginState()
    object LoginInProgress : LoginState()
    object QrLoginInProgress : LoginState()
    
    data class LoginSuccess(val requiresPasswordReset: Boolean) : LoginState()
    data class LoginFailure(val errorCode: String?, val message: String?) : LoginState()
    
    object DuplicateLogin : LoginState()
    object ShowFindAccountPopup : LoginState()
    object NavigateToHome : LoginState()
    object NavigateToPasswordReset : LoginState()
    object NavigateToQrScan : LoginState()
}
