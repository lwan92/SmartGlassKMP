package com.smartglass.project.data.local

/**
 * 로컬 스토리지 관리 인터페이스
 * multiplatform-settings 기반
 */
interface PreferencesManager {
    // Token 관리
    fun saveAccessToken(token: String)
    fun getAccessToken(): String?
    fun saveRefreshToken(token: String)
    fun getRefreshToken(): String?
    fun clearTokens()
    
    // 사용자 정보
    fun saveUserId(userId: String)
    fun getUserId(): String?
    fun saveUsername(username: String)
    fun getUsername(): String?
    
    // 자동 로그인
    fun setAutoLogin(enabled: Boolean)
    fun isAutoLoginEnabled(): Boolean
    
    // 디바이스 등록
    fun saveAppId(appId: String)
    fun getAppId(): String?
    fun setDeviceRegistered(registered: Boolean)
    fun isDeviceRegistered(): Boolean
    
    // Base URL 관리
    fun saveBaseUrl(url: String)
    fun getBaseUrl(): String?
    
    // 전체 삭제
    fun clearAll()
}
