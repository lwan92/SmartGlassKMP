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
}
