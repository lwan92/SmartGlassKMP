package com.smartglass.project.domain.repository

import com.smartglass.project.domain.model.LoginResult

interface AuthRepository {
    suspend fun login(
        loginId: String,
        password: String,
        deviceType: String,
        platform: String = "android",
        allowDuplicateLogin: Boolean = false,
        appId: String? = null
    ): Result<LoginResult>
    
    /**
     * 디바이스 등록
     * features_spec.md: 2.2 디바이스 등록 프로세스 - 1단계
     */
    suspend fun registerDevice(
        uuid: String,
        deviceId: String,
        deviceType: String
    ): Result<String> // deviceId 반환
    
    /**
     * 앱 등록
     * features_spec.md: 2.2 디바이스 등록 프로세스 - 2단계
     */
    suspend fun registerApp(
        deviceId: String,
        appId: String,
        appType: String,
        appVersion: String
    ): Result<String> // appId 반환
}
