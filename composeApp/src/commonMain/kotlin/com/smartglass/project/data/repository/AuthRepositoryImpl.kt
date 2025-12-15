package com.smartglass.project.data.repository

import com.smartglass.project.data.remote.api.AuthApi
import com.smartglass.project.data.remote.dto.LoginRequest
import com.smartglass.project.data.remote.dto.toDomain
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
}
