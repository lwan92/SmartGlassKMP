package com.smartglass.project.presentation.intro

/**
 * Intro 화면의 상태 정의
 * features_spec.md: 1. Intro 화면 상태 기준
 */
sealed class IntroState {
    object Idle : IntroState()
    object CheckingPermissions : IntroState()
    object RequestingPermissions : IntroState()
    object CheckingLogin : IntroState()
    object AutoLoginInProgress : IntroState()
    object NavigateToLogin : IntroState()
    object NavigateToHome : IntroState()
}
