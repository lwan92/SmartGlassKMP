package com.smartglass.project.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * 토큰 갱신 요청
 * api_spec.md 2.4 참조
 */
@Serializable
data class TokenRefreshRequest(
    val refreshToken: String,
    val bypassRefreshExpiry: Boolean = true
)

