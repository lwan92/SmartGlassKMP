package com.smartglass.project.presentation.intro

/**
 * Intro 화면의 상태 정의
 * features_spec.md의 Intro 화면 로직을 기반으로 작성
 */
sealed class IntroState {
    object Idle : IntroState()
    object ShowingSplash : IntroState()           // 스플래시 표시 중 (2초)
    object CheckingPermissions : IntroState()     // 권한 확인 중
    object RequestingPermissions : IntroState()  // 권한 요청 중
    object CheckingLogin : IntroState()           // 로그인 상태 확인 중
    object AutoLoginInProgress : IntroState()     // 자동 로그인 진행 중
    object NavigateToLogin : IntroState()         // 로그인 화면으로 이동
    object NavigateToHome : IntroState()          // 홈 화면으로 이동
    data class Error(val message: String) : IntroState()  // 에러 발생
}
