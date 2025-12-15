package com.smartglass.project.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val loginId: String,
    val password: String,
    val deviceType: String,
    val platform: String = "android",
    val allowDuplicateLogin: Boolean = false,
    val appId: String? = null
)
