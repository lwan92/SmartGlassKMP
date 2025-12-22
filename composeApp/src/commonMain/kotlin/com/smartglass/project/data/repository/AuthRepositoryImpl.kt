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
            println("🌐 AuthRepository: registerDevice API 호출 준비")
            println("🌐 Request - uuid: $uuid, deviceId: $deviceId, deviceType: $deviceType")
            
            val request = RegisterDeviceRequest(
                uuid = uuid,
                deviceId = deviceId,
                deviceType = deviceType,
                activeStatus = true
            )
            
            println("🌐 API 호출 중...")
            val response = authApi.registerDevice(request)
            
            println("🌐 Response 수신:")
            println("  - success: ${response.success}")
            println("  - code: ${response.code}")
            // message 필드 없음
            println("  - data: ${response.data}")
            println("  - data.device: ${response.data?.device}")
            println("  - data.device.deviceId: ${response.data?.device?.deviceId}")
            
            if (response.success && response.data?.device?.deviceId != null) {
                val deviceId = response.data.device.deviceId
                println("✅ 디바이스 등록 성공 - deviceId: $deviceId")
                Result.success(deviceId)
            } else {
                val errorMessage = "디바이스 등록에 실패했습니다. (코드: ${response.code})"
                println("❌ 디바이스 등록 실패 - $errorMessage")
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            println("❌ AuthRepository: registerDevice 예외 발생 - ${e.message}")
            e.printStackTrace()
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
            println("🌐 AuthRepository: registerApp API 호출 준비")
            println("🌐 Request - deviceId: $deviceId, appId: $appId, appType: $appType, appVersion: $appVersion")
            
            val request = RegisterAppRequest(
                deviceId = deviceId,
                appId = appId,
                appType = appType,
                appVersion = appVersion
            )
            
            println("🌐 API 호출 중...")
            val response = authApi.registerApp(request)
            
            println("🌐 Response 수신:")
            println("  - success: ${response.success}")
            println("  - code: ${response.code}")
            // message 필드 없음
            println("  - data: ${response.data}")
            println("  - data.appInfo: ${response.data?.appInfo}")
            println("  - data.appInfo.appId: ${response.data?.appInfo?.appId}")
            
            if (response.success && response.data?.appInfo?.appId != null) {
                val appId = response.data.appInfo.appId
                println("✅ 앱 등록 성공 - appId: $appId")
                Result.success(appId)
            } else {
                val errorMessage = "앱 등록에 실패했습니다. (코드: ${response.code})"
                println("❌ 앱 등록 실패 - $errorMessage")
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            println("❌ AuthRepository: registerApp 예외 발생 - ${e.message}")
            e.printStackTrace()
            Result.failure(e)
        }
    }
}
