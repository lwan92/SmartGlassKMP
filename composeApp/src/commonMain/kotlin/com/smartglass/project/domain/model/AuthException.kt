package com.smartglass.project.domain.model

/**
 * 인증 관련 예외
 * features_spec.md: 중복 로그인 (코드 1018) 처리
 */
sealed class AuthException(
    message: String,
    val errorCode: String? = null
) : Exception(message) {
    /**
     * 중복 로그인 에러 (코드: 1018)
     */
    class DuplicateLogin(message: String) : AuthException(message, "1018")
    
    /**
     * 기타 인증 에러
     */
    class Other(message: String, errorCode: String?) : AuthException(message, errorCode)
}

