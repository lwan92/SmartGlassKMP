package com.smartglass.project.presentation.passwordreset

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * 비밀번호 재설정 화면 ViewModel
 * features_spec.md 및 api_spec.md를 기반으로 구현
 */
class PasswordResetViewModel() : ViewModel() {
    private val _state = MutableStateFlow<PasswordResetState>(PasswordResetState.Idle())
    val state: StateFlow<PasswordResetState> = _state.asStateFlow()

    fun handleIntent(intent: PasswordResetIntent) {
        when (intent) {
            is PasswordResetIntent.UpdateCurrentPassword -> updateCurrentPassword(intent.password)
            is PasswordResetIntent.UpdateNewPassword -> updateNewPassword(intent.password)
            is PasswordResetIntent.UpdateConfirmPassword -> updateConfirmPassword(intent.password)
            is PasswordResetIntent.ToggleCurrentPasswordVisibility -> toggleCurrentPasswordVisibility()
            is PasswordResetIntent.ToggleNewPasswordVisibility -> toggleNewPasswordVisibility()
            is PasswordResetIntent.ToggleConfirmPasswordVisibility -> toggleConfirmPasswordVisibility()
            is PasswordResetIntent.ResetPassword -> resetPassword()
            is PasswordResetIntent.Close -> close()
        }
    }

    private fun updateCurrentPassword(password: String) {
        _state.update { state ->
            if (state is PasswordResetState.Idle) {
                state.copy(currentPassword = password, validationError = null)
            } else state
        }
    }

    private fun updateNewPassword(password: String) {
        _state.update { state ->
            if (state is PasswordResetState.Idle) {
                state.copy(newPassword = password, validationError = null)
            } else state
        }
    }

    private fun updateConfirmPassword(password: String) {
        _state.update { state ->
            if (state is PasswordResetState.Idle) {
                val error = if (password != state.newPassword) {
                    "비밀번호가 일치하지 않습니다"
                } else null
                state.copy(confirmPassword = password, validationError = error)
            } else state
        }
    }

    private fun toggleCurrentPasswordVisibility() {
        _state.update { state ->
            if (state is PasswordResetState.Idle) {
                state.copy(showCurrentPassword = !state.showCurrentPassword)
            } else state
        }
    }

    private fun toggleNewPasswordVisibility() {
        _state.update { state ->
            if (state is PasswordResetState.Idle) {
                state.copy(showNewPassword = !state.showNewPassword)
            } else state
        }
    }

    private fun toggleConfirmPasswordVisibility() {
        _state.update { state ->
            if (state is PasswordResetState.Idle) {
                state.copy(showConfirmPassword = !state.showConfirmPassword)
            } else state
        }
    }

    /**
     * 비밀번호 재설정
     * api_spec.md의 1.7 비밀번호 변경 API 호출
     */
    private fun resetPassword() {
        viewModelScope.launch {
            val currentState = _state.value
            if (currentState is PasswordResetState.Idle) {
                // 유효성 검사
                if (!currentState.isResetEnabled) {
                    _state.update { 
                        currentState.copy(
                            validationError = "비밀번호를 올바르게 입력해주세요"
                        )
                    }
                    return@launch
                }
                
                _state.value = PasswordResetState.Resetting
                
                // TODO: 비밀번호 변경 API 호출
                // api_spec.md의 1.7 비밀번호 변경 API 사용
                // PUT /api/users/password
                // ChangePasswordData:
                //   - currentPassword: 현재 비밀번호
                //   - newPassword: 새 비밀번호
                
                try {
                    // 임시로 성공 처리
                    // 실제 구현 시 Repository를 통해 API 호출
                    _state.value = PasswordResetState.ResetSuccess
                } catch (e: Exception) {
                    _state.value = PasswordResetState.Error("비밀번호 변경에 실패했습니다: ${e.message}")
                }
            }
        }
    }

    private fun close() {
        _state.value = PasswordResetState.Idle()
    }
}
