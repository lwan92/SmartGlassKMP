package com.smartglass.project.domain.usecase

import com.smartglass.project.domain.model.LoginResult
import com.smartglass.project.domain.repository.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        loginId: String,
        password: String,
        autoLogin: Boolean
    ): Result<LoginResult> {
        // 입력값 검증
        if (loginId.isBlank()) {
            return Result.failure(Exception("아이디를 입력해주세요"))
        }
        
        if (password.isBlank()) {
            return Result.failure(Exception("비밀번호를 입력해주세요"))
        }
        
        // 로그인 요청
        return authRepository.login(
            loginId = loginId,
            password = password,
            deviceType = "MOBILE", // 또는 플랫폼에 따라 "GLASS", "MOBILE_NEO" 등
            platform = getPlatform(),
            allowDuplicateLogin = autoLogin,
            appId = null // 필요 시 기기 등록 후 사용
        )
    }
    
    private fun getPlatform(): String {
        // TODO: 실제 플랫폼 확인 로직
        return "android"
    }
}
