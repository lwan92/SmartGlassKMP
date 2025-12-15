package com.smartglass.project.presentation.passwordreset

/**
 * 비밀번호 재설정 화면의 상태 정의
 * features_spec.md 및 Figma 디자인을 기반으로 작성
 */
sealed class PasswordResetState {
    data class Idle(
        val currentPassword: String = "",
        val newPassword: String = "",
        val confirmPassword: String = "",
        val showCurrentPassword: Boolean = false,
        val showNewPassword: Boolean = false,
        val showConfirmPassword: Boolean = false,
        val validationError: String? = null
    ) : PasswordResetState() {
        val isResetEnabled: Boolean
            get() = currentPassword.isNotBlank() 
                && newPassword.isNotBlank() 
                && confirmPassword.isNotBlank()
                && newPassword == confirmPassword
                && isValidPassword(newPassword)
    }
    
    object Resetting : PasswordResetState()              // 비밀번호 재설정 진행 중
    object ResetSuccess : PasswordResetState()          // 비밀번호 재설정 성공
    data class Error(val message: String) : PasswordResetState()  // 에러 발생
    
    /**
     * 비밀번호 유효성 검사
     * 8자 이상~20자 이내로 영문, 숫자, 특수 문자를 포함해야 함
     */
    fun isValidPassword(password: String): Boolean {
        if (password.length < 8 || password.length > 20) return false
        val hasLetter = password.any { it.isLetter() }
        val hasDigit = password.any { it.isDigit() }
        val hasSpecial = password.any { !it.isLetterOrDigit() }
        return hasLetter && hasDigit && hasSpecial
    }
}
