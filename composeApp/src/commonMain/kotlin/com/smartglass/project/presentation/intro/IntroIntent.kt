package com.smartglass.project.presentation.intro

/**
 * Intro 화면의 사용자 액션 정의
 * features_spec.md: 1. Intro 화면 사용자 행동 기준
 */
sealed class IntroIntent {
    object CheckPermissions : IntroIntent()
    object PermissionsGranted : IntroIntent()
    object PermissionsDenied : IntroIntent()
    object CheckAutoLogin : IntroIntent()
    object AutoLoginSuccess : IntroIntent()
    object AutoLoginFailed : IntroIntent()
}
