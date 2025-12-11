package com.smartglass.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform