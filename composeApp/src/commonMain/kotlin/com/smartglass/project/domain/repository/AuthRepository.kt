package com.smartglass.project.domain.repository

import com.smartglass.project.domain.model.LoginResult

interface AuthRepository {
    
    suspend fun login(
        loginId: String,
        password: String,
        deviceType: String,
        platform: String,
        allowDuplicateLogin: Boolean,
        appId: String?
    ): Result<LoginResult>
    
    /**
     * 디바이스 등록
     * @param uuid QR 코드에서 받은 UUID
     * @param deviceId 디바이스 ID (초기 등록 시 null, 재등록 시 기존 ID)
     * @param deviceType 디바이스 타입 (GLASS, MOBILE)
     * @return Result<String> 성공 시 등록된 deviceId 반환
     */
    suspend fun registerDevice(
        uuid: String,
        deviceId: String? = null,  // ✅ nullable, 초기 등록 시 null
        deviceType: String
    ): Result<String>
    
    /**
     * 앱 등록
     * @param deviceId 디바이스 ID
     * @param appId 앱 ID
     * @param appType 앱 타입 (GLASS, MOBILE)
     * @param appVersion 앱 버전
     * @return Result<String> 성공 시 등록된 appId 반환
     */
    suspend fun registerApp(
        deviceId: String,
        appId: String,
        appType: String,
        appVersion: String
    ): Result<String>
}
