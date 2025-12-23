package com.smartglass.project.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * 아이디 찾기 응답
 * api_spec.md 2.5 참조
 */
@Serializable
data class FindIdResponse(
    val success: Boolean,
    val code: String,
    val data: FindIdData? = null
)

@Serializable
data class FindIdData(
    val loginId: String? = null,
    val userName: String? = null,
    val createDate: String? = null
)

