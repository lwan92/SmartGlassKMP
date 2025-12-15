package com.smartglass.project.presentation.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartglass.project.domain.usecase.LoginUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Intro 화면 ViewModel
 * features_spec.md의 Intro 화면 로직을 기반으로 구현
 */
class IntroViewModel(
    private val loginUseCase: LoginUseCase // 자동 로그인용 (나중에 사용)
) : ViewModel() {
    private val _state = MutableStateFlow<IntroState>(IntroState.Idle)
    val state: StateFlow<IntroState> = _state.asStateFlow()

    fun handleIntent(intent: IntroIntent) {
        when (intent) {
            is IntroIntent.StartApp -> startApp()
            is IntroIntent.PermissionsGranted -> checkLogin()
            is IntroIntent.PermissionsDenied -> navigateToLogin()
            is IntroIntent.AutoLoginSuccess -> navigateToHome()
            is IntroIntent.AutoLoginFailed -> navigateToLogin()
            is IntroIntent.NavigateToLogin -> navigateToLogin()
            is IntroIntent.NavigateToHome -> navigateToHome()
        }
    }

    /**
     * 앱 시작 처리
     * 1. 스플래시 화면 표시 (2초)
     * 2. 권한 확인 시작
     */
    private fun startApp() {
        viewModelScope.launch {
            _state.value = IntroState.ShowingSplash
            
            // 스플래시 2초 표시
            delay(2000)
            
            // 권한 확인 시작
            _state.value = IntroState.CheckingPermissions
            // TODO: 실제 권한 확인 로직 구현
            // 현재는 바로 로그인 확인으로 진행
            checkLogin()
        }
    }

    /**
     * 로그인 상태 확인
     * 자동 로그인 가능 여부 확인 후 처리
     */
    private fun checkLogin() {
        viewModelScope.launch {
            _state.value = IntroState.CheckingLogin
            
            // TODO: 저장된 토큰 확인 및 디바이스 등록 여부 확인
            // 현재는 자동 로그인 불가로 가정하고 로그인 화면으로 이동
            delay(500) // 확인 시간 시뮬레이션
            
            // 자동 로그인 가능 여부 확인
            val hasToken = false // TODO: 실제 토큰 확인 로직
            val isDeviceRegistered = false // TODO: 실제 디바이스 등록 확인 로직
            
            if (hasToken && isDeviceRegistered) {
                // 자동 로그인 시도
                _state.value = IntroState.AutoLoginInProgress
                // TODO: 자동 로그인 API 호출
                // 현재는 실패로 가정
                navigateToLogin()
            } else {
                // 자동 로그인 불가 -> 로그인 화면으로 이동
                navigateToLogin()
            }
        }
    }

    /**
     * 로그인 화면으로 이동
     */
    private fun navigateToLogin() {
        _state.value = IntroState.NavigateToLogin
    }

    /**
     * 홈 화면으로 이동
     */
    private fun navigateToHome() {
        _state.value = IntroState.NavigateToHome
    }
}
