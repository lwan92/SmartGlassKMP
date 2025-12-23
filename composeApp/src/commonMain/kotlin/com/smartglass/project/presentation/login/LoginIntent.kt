package com.smartglass.project.presentation.login

/**
 * 로그인 화면의 사용자 액션 정의
 * features_spec.md 2. 로그인 화면 사용자 행동 기준
 */
sealed class LoginIntent {
    // 입력 업데이트
    data class UpdateUsername(val username: String) : LoginIntent()
    data class UpdatePassword(val password: String) : LoginIntent()
    data class UpdateAutoLogin(val autoLogin: Boolean) : LoginIntent()
    object TogglePasswordVisibility : LoginIntent()
    
    // 필드 포커스 (디바이스 미등록 시 팝업 표시)
    object OnFieldFocused : LoginIntent()
    
    // 로그인 액션
    object ClickLogin : LoginIntent()
    object ClickQrLogin : LoginIntent()
    
    // 디바이스 등록
    object ClickDeviceRegistration : LoginIntent()
    object DeviceRegistrationSuccess : LoginIntent()
    object DeviceRegistrationCancelled : LoginIntent()
    
    // 계정 찾기
    object ClickFindId : LoginIntent()
    object ClickFindPassword : LoginIntent()
    object CloseFindAccountPopup : LoginIntent()
    
    // 중복 로그인
    object ConfirmDuplicateLogin : LoginIntent()
    object CancelDuplicateLogin : LoginIntent()
    
    // 네비게이션
    object NavigateToHome : LoginIntent()
    object NavigateToPasswordReset : LoginIntent()
    object NavigateToQrScan : LoginIntent()
}
