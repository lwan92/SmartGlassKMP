package com.smartglass.project.presentation.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartglass.project.data.local.PreferencesManager
import com.smartglass.project.platform.permissions.PermissionManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Intro 화면 ViewModel
 * features_spec.md: 1. Intro 화면 사용자 행동 및 앱 반응 참고
 */
class IntroViewModel(
    private val preferencesManager: PreferencesManager,
    private val permissionManager: PermissionManager
) : ViewModel() {
    private val _state = MutableStateFlow<IntroState>(IntroState.Idle)
    val state: StateFlow<IntroState> = _state.asStateFlow()
    
    init {
        startIntroFlow()
    }
    
    private fun startIntroFlow() {
        viewModelScope.launch {
            // features_spec.md: Input: 앱 시작
            // Output: 스플래시 이미지 표시 (브랜딩 이미지 또는 기본 이미지)
            _state.value = IntroState.ShowingSplash
            
            // features_spec.md: Input: 2초 대기 후
            // Output: 권한 확인 시작
            delay(2000)
            checkPermissions()
        }
    }
    
    fun handleIntent(intent: IntroIntent) {
        when (intent) {
            is IntroIntent.CheckPermissions -> checkPermissions()
            is IntroIntent.PermissionsGranted -> onPermissionsGranted()
            is IntroIntent.PermissionsDenied -> onPermissionsDenied()
            is IntroIntent.CheckAutoLogin -> checkAutoLogin()
            is IntroIntent.AutoLoginSuccess -> onAutoLoginSuccess()
            is IntroIntent.AutoLoginFailed -> onAutoLoginFailed()
        }
    }
    
    private fun checkPermissions() {
        _state.value = IntroState.CheckingPermissions
        
        viewModelScope.launch {
            try {
                val hasPermissions = permissionManager.checkPermissions()
                
                if (hasPermissions) {
                    onPermissionsGranted()
                } else {
                    // 권한 요청 시도
                    _state.value = IntroState.RequestingPermissions
                    val granted = permissionManager.requestPermissions()
                    
                    if (granted) {
                        onPermissionsGranted()
                    } else {
                        onPermissionsDenied()
                    }
                }
            } catch (e: Exception) {
                _state.value = IntroState.Error(e.message ?: "권한 확인 실패")
                delay(2000)
                onPermissionsDenied()
            }
        }
    }
    
    private fun onPermissionsGranted() {
        // features_spec.md: Input: 필수 권한 확인 완료
        // Output: 로그인 상태 확인
        checkAutoLogin()
    }
    
    private fun onPermissionsDenied() {
        // features_spec.md: Input: 권한 거부
        // Output: 권한 안내 토스트 표시, 로그인 화면으로 이동
        viewModelScope.launch {
            delay(1000)
            _state.value = IntroState.NavigateToLogin
        }
    }
    
    private fun checkAutoLogin() {
        _state.value = IntroState.CheckingLogin
        
        viewModelScope.launch {
            val hasRefreshToken = preferencesManager.getRefreshToken() != null
            val isDeviceRegistered = preferencesManager.isDeviceRegistered()
            val isAutoLoginEnabled = preferencesManager.isAutoLoginEnabled()
            
            if (hasRefreshToken && isDeviceRegistered && isAutoLoginEnabled) {
                // features_spec.md: Input: 자동 로그인 가능 (토큰 존재 + 디바이스 등록됨)
                // Output: 자동 로그인 시도
                tryAutoLogin()
            } else {
                // features_spec.md: Input: 자동 로그인 실패 또는 자동 로그인 불가
                // Output: 로그인 화면으로 이동
                onAutoLoginFailed()
            }
        }
    }
    
    private fun tryAutoLogin() {
        _state.value = IntroState.AutoLoginInProgress
        
        viewModelScope.launch {
            // TODO: 실제 자동 로그인 로직 (refreshToken 사용)
            delay(1000)
            
            val success = false // TODO: 실제 결과
            
            if (success) {
                onAutoLoginSuccess()
            } else {
                onAutoLoginFailed()
            }
        }
    }
    
    private fun onAutoLoginSuccess() {
        // features_spec.md: Input: 자동 로그인 성공
        // Output: 홈 화면으로 이동
        _state.value = IntroState.NavigateToHome
    }
    
    private fun onAutoLoginFailed() {
        // features_spec.md: Input: 자동 로그인 실패 또는 자동 로그인 불가
        // Output: 로그인 화면으로 이동
        _state.value = IntroState.NavigateToLogin
    }
}
