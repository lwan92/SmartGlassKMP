package com.smartglass.project.presentation.passwordreset

/**
 * 비밀번호 재설정 화면의 사용자 액션 정의
 * features_spec.md 및 Figma 디자인을 기반으로 작성
 */
sealed class PasswordResetIntent {
    data class UpdateCurrentPassword(val password: String) : PasswordResetIntent()
    data class UpdateNewPassword(val password: String) : PasswordResetIntent()
    data class UpdateConfirmPassword(val password: String) : PasswordResetIntent()
    object ToggleCurrentPasswordVisibility : PasswordResetIntent()
    object ToggleNewPasswordVisibility : PasswordResetIntent()
    object ToggleConfirmPasswordVisibility : PasswordResetIntent()
    object ResetPassword : PasswordResetIntent()         // 비밀번호 재설정
    object Close : PasswordResetIntent()                // 화면 닫기
}
