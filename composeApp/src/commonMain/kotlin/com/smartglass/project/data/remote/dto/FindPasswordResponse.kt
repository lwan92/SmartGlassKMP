package com.smartglass.project.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * 비밀번호 찾기 응답
 * api_spec.md 2.6 참조
 */
@Serializable
data class FindPasswordResponse(
    val success: Boolean,
    val code: String,
    val data: FindPasswordData? = null
)

@Serializable
data class FindPasswordData(
    val message: String? = null
)

