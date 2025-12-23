package com.smartglass.project.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * 공통 API 응답 구조
 * api_spec.md 1.3 참조
 * 
 * 모든 API 응답은 이 구조를 따릅니다:
 * {
 *   "success": boolean,
 *   "code": string,
 *   "message": string?,
 *   "status": number?,
 *   "data": object?
 * }
 */
@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val code: String,
    val message: String? = null,
    val status: Int? = null,
    val data: T? = null
)

