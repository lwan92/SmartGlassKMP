package com.smartglass.project.domain.usecase

import com.smartglass.project.data.local.PreferencesManager
import com.smartglass.project.domain.model.LoginResult
import com.smartglass.project.domain.repository.AuthRepository

/**
 * 로그인 UseCase
 * features_spec.md 3.1: Login 참고
 * 
 * - 로그인 성공 시 토큰 저장
 * - 자동 로그인 설정 저장
 */
class LoginUseCase(
    private val authRepository: AuthRepository,
    private val preferencesManager: PreferencesManager
) {
    suspend operator fun invoke(
        loginId: String,
        password: String,
        autoLogin: Boolean,
        appId: String? = null,
        allowDuplicateLogin: Boolean = false
    ): Result<LoginResult> {
        // 입력값 검증
        if (loginId.isBlank()) {
            return Result.failure(Exception("아이디를 입력해주세요"))
        }
        
        if (password.isBlank()) {
            return Result.failure(Exception("비밀번호를 입력해주세요"))
        }
        
        // 로그인 요청
        val result = authRepository.login(
            loginId = loginId,
            password = password,
            deviceType = "MOBILE",
            platform = getPlatform(),
            allowDuplicateLogin = allowDuplicateLogin,
            appId = appId
        )
        
        // 로그인 성공 시 토큰 저장
        result.onSuccess { loginResult ->
            // 토큰 저장
            loginResult.token?.let { token ->
                token.accessToken?.let { preferencesManager.saveAccessToken(it) }
                token.refreshToken?.let { preferencesManager.saveRefreshToken(it) }
            }
            
            // 사용자 정보 저장
            loginResult.user?.let { user ->
                user.userId?.let { preferencesManager.saveUserId(it) }
                user.userName?.let { preferencesManager.saveUsername(it) }
            }
            
            // 자동 로그인 설정 저장
            preferencesManager.setAutoLogin(autoLogin)
            
            // AppId 저장 (있는 경우)
            appId?.let { preferencesManager.saveAppId(it) }
        }
        
        return result
    }
    
    private fun getPlatform(): String {
        // TODO: 실제 플랫폼 확인 로직
        return "android"
    }
}
