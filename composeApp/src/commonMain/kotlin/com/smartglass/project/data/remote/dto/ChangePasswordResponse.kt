package com.smartglass.project.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * 비밀번호 변경 응답
 * api_spec.md 2.7 참조
 */
@Serializable
data class ChangePasswordResponse(
    val success: Boolean,
    val code: String
)

