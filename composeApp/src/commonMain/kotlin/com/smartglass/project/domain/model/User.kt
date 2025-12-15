package com.smartglass.project.domain.model

data class User(
    val userId: Long,
    val loginId: String,
    val deptName: String,
    val ognzName: String,
    val positionName: String,
    val userName: String,
    val role: Role
)
