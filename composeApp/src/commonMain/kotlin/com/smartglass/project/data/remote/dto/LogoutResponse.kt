package com.smartglass.project.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * 로그아웃 응답
 * api_spec.md 2.3 참조
 */
@Serializable
data class LogoutResponse(
    val success: Boolean,
    val code: String
)

