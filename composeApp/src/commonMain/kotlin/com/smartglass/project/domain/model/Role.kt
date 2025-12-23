package com.smartglass.project.domain.model

data class Role(
    val roleGroupId: Long, // Int를 Long으로 변환하여 저장 (호환성 유지)
    val roleGroupName: String
)
