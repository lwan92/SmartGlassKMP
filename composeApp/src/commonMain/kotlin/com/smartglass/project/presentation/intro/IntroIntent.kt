package com.smartglass.project.presentation.intro

/**
 * Intro 화면의 사용자 액션 정의
 * features_spec.md의 Intro 화면 로직을 기반으로 작성
 */
sealed class IntroIntent {
    object StartApp : IntroIntent()                    // 앱 시작
    object PermissionsGranted : IntroIntent()          // 권한 승인됨
    object PermissionsDenied : IntroIntent()           // 권한 거부됨
    object AutoLoginSuccess : IntroIntent()            // 자동 로그인 성공
    object AutoLoginFailed : IntroIntent()            // 자동 로그인 실패
    object NavigateToLogin : IntroIntent()            // 로그인 화면으로 이동
    object NavigateToHome : IntroIntent()             // 홈 화면으로 이동
}
