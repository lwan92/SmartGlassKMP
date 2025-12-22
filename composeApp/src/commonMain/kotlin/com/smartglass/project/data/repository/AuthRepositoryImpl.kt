package com.smartglass.project.data.repository

import com.smartglass.project.data.remote.api.AuthApi
import com.smartglass.project.data.remote.dto.*
import com.smartglass.project.domain.model.LoginResult
import com.smartglass.project.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authApi: AuthApi
) : AuthRepository {
    
    override suspend fun login(
        loginId: String,
        password: String,
        deviceType: String,
        platform: String,
        allowDuplicateLogin: Boolean,
        appId: String?
    ): Result<LoginResult> {
        return try {
            val request = LoginRequest(
                loginId = loginId,
                password = password,
                deviceType = deviceType,
                platform = platform,
                allowDuplicateLogin = allowDuplicateLogin,
                appId = appId
            )
            
            val response = authApi.login(request)
            
            if (response.success && response.data != null) {
                Result.success(response.data.toDomain())
            } else {
                val errorMessage = response.message 
                    ?: "로그인에 실패했습니다. (코드: ${response.code})"
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun registerDevice(
        uuid: String,
        deviceId: String,
        deviceType: String
    ): Result<String> {
        return try {
            val request = RegisterDeviceRequest(
                uuid = uuid,
                deviceId = deviceId,
                deviceType = deviceType,
                activeStatus = true
            )
            
            val response = authApi.registerDevice(request)
            
            if (response.success && response.data?.device?.deviceId != null) {
                Result.success(response.data.device.deviceId)
            } else {
                val errorMessage = "디바이스 등록에 실패했습니다. (코드: ${response.code})"
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun registerApp(
        deviceId: String,
        appId: String,
        appType: String,
        appVersion: String
    ): Result<String> {
        return try {
            val request = RegisterAppRequest(
                deviceId = deviceId,
                appId = appId,
                appType = appType,
                appVersion = appVersion
            )
            
            val response = authApi.registerApp(request)
            
            if (response.success && response.data?.appInfo?.appId != null) {
                Result.success(response.data.appInfo.appId)
            } else {
                val errorMessage = "앱 등록에 실패했습니다. (코드: ${response.code})"
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
